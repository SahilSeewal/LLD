package service;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import constant.Direction;
import models.Floor;
import models.Lift;
import models.Request;

public class LiftService {
    private Lift lift;
    private Semaphore requestLiftSemaphore;
    private Semaphore requestFloorSemaphore;
    private AtomicInteger totalFloors;
    
    public LiftService(Lift lift) {
        this.lift = lift;
        this.totalFloors = new AtomicInteger(5);
        this.requestLiftSemaphore = new Semaphore(this.totalFloors.get());
        this.requestFloorSemaphore = new Semaphore(this.totalFloors.get()-1);
    }
    
    public void requestLift(Direction direction, Floor floor) {
 
        if(lift.getFloor().getVal().get() == floor.getVal().get()) {
            System.out.println("Lift is already on floor : " + floor.getVal().get());
            return;
        }

        if(requestLiftSemaphore.availablePermits() == 0) {
            System.out.println("Lift is already requested for current floor: " + floor.getVal().get());
            return;
        }
        try {
            requestLiftSemaphore.acquire(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(direction == Direction.UP)
            lift.getDownToTop().offer(new Request(floor, floor));

        else 
            lift.getTopToDown().offer(new Request(floor, floor));
            
        move();
        requestLiftSemaphore.release(1);
    }                                                                              
    
    private void move() {
        PriorityBlockingQueue<Request> topToDown = lift.getTopToDown();
        PriorityBlockingQueue<Request> downToTop = lift.getDownToTop();
        Direction currDirection = lift.getDirection();

        Direction direction = ((currDirection == Direction.STOP || currDirection == Direction.DOWN) 
                                && !downToTop.isEmpty())? Direction.UP : Direction.DOWN; 
        lift.setDirection(direction);

        while(!downToTop.isEmpty() && direction == Direction.UP) {
            Floor currFloor = lift.getFloor();
            System.out.println("Moved " + direction + " from " + currFloor.getVal().get() 
            + " to " + downToTop.peek().getToFloor().getVal().get() +" floor");
            try {
                lift.setFloor(downToTop.take().getToFloor());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        while(!topToDown.isEmpty() && direction == Direction.DOWN) {
            Floor currFloor = lift.getFloor();
            System.out.println("Moved " + direction + " from " + currFloor.getVal().get() + " to " 
                    + topToDown.peek().getToFloor().getVal().get() +" floor");
            try {
                lift.setFloor(topToDown.take().getToFloor());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(!lift.getTopToDown().isEmpty() || !lift.getDownToTop().isEmpty()) {
            Direction newDirection = direction == Direction.UP ? Direction.DOWN:Direction.UP;
            lift.setDirection(newDirection);
            move();
        }

        if(lift.getTopToDown().isEmpty() && lift.getDownToTop().isEmpty())
            lift.setDirection(Direction.STOP);
    }
    
    private void allotFloorRequest(Floor fromFloor, Floor toFloor) {
        if(fromFloor.getVal() == toFloor.getVal()) {
            System.out.println("Already on floor " + fromFloor.getVal());
            return;
        }

        if(this.requestFloorSemaphore.availablePermits() == 0) {
            System.out.println("Already selected floor " + toFloor.getVal());
            return;
        }

        try {
            this.requestFloorSemaphore.acquire(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(fromFloor.getVal().get() > toFloor.getVal().get()) {
            lift.getTopToDown().offer(new Request(fromFloor, toFloor));

        } else {
            lift.getDownToTop().offer(new Request(fromFloor, toFloor));
        }
        this.requestFloorSemaphore.release(1);
    } 

    public void goToFloor(Floor fromFloor, Floor toFloor) {
        allotFloorRequest(fromFloor, toFloor);
        move();        
    } 

}