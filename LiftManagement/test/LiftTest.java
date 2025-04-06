package LiftManagement.test;
/*
 * User can hit UP/DOWN button to request.
 * - UP -> User request for lift going upward.
 * - Down -> User request for lift going downward.
 * Inside lift user can select:
 * - 1,2,3,4,5,OPEN,CLOSE
 * Lift going downward and if user select floor
 *  which is above current floor then lift will 
 * first go down to 1 and then up, vice versa is also true  
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import LiftManagement.constant.Direction;
import LiftManagement.models.Floor;
import LiftManagement.models.Lift;
import LiftManagement.models.Request;
import LiftManagement.service.LiftService;

public class LiftTest {
    
    public static void main(String[] args) {
        Lift lift = new Lift("lift-1", new Floor(new AtomicInteger(1)), 
        Direction.STOP, new PriorityBlockingQueue<Request>(10, 
        new CustomUpComaparator()),
        new PriorityBlockingQueue<Request>(10,
        new CustomDownComaparator()));

        LiftService liftService = new LiftService(lift);
        List<Direction> directions = Arrays.asList(Direction.UP, Direction.DOWN,
                 Direction.UP, Direction.DOWN, Direction.UP, Direction.DOWN,
                 Direction.UP);

        List<Floor> floors = Arrays.asList(new Floor(new AtomicInteger(1)), 
        new Floor(new AtomicInteger(1)), new Floor(new AtomicInteger(4)), 
        new Floor(new AtomicInteger(3)), new Floor(new AtomicInteger(2)),
        new Floor(new AtomicInteger(4)), new Floor(new AtomicInteger(5)));         
        
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for(int i=0;i<floors.size();i++) {
            final int x = i;
            futures.add(CompletableFuture.runAsync(()->{
                liftService.requestLift(directions.get(x), floors.get(x));
            }));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    public static class CustomUpComaparator implements Comparator<Request> {
        @Override
        public int compare(Request req1, Request req2) {
            return req1.getToFloor().getVal().get() - 
                req2.getToFloor().getVal().get();
        } 
    }
    
    public static class CustomDownComaparator implements Comparator<Request> {
        @Override
        public int compare(Request req1, Request req2) {
            return req2.getToFloor().getVal().get() - 
                req1.getToFloor().getVal().get();
        } 
    }
    
}
