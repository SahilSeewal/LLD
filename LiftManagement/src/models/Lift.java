package models;

import java.util.concurrent.PriorityBlockingQueue;
import constant.Direction;

public class Lift {
    //private InternalPanal internalPanal;
    private String liftId;
   // private ExternalPanal externalPanal;
    private Floor floor;
    // private State state; //open or close
    private Direction direction;
    private PriorityBlockingQueue<Request> topToDown;
    private PriorityBlockingQueue<Request> downToTop;

    
    public Lift(String liftId, Floor floor, Direction direction, PriorityBlockingQueue<Request> topToDown,
            PriorityBlockingQueue<Request> downToTop) {
        this.liftId = liftId;
        this.floor = floor;
        this.direction = direction;
        this.topToDown = topToDown;
        this.downToTop = downToTop;
    }
    // public InternalPanal getInternalPanal() {
    //     return internalPanal;
    // }
    // public void setInternalPanal(InternalPanal internalPanal) {
    //     this.internalPanal = internalPanal;
    // }
    public String getLiftId() {
        return liftId;
    }
    public void setLiftId(String liftId) {
        this.liftId = liftId;
    }
    // public ExternalPanal getExternalPanal() {
    //     return externalPanal;
    // }
    // public void setExternalPanal(ExternalPanal externalPanal) {
    //     this.externalPanal = externalPanal;
    // }
    public Floor getFloor() {
        return floor;
    }
    public void setFloor(Floor floor) {
        this.floor = floor;
    }
    // public State getState() {
    //     return state;
    // }
    // public void setState(State state) {
    //     this.state = state;
    // }
    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public PriorityBlockingQueue<Request> getTopToDown() {
        return topToDown;
    }
    public void setTopToDown(PriorityBlockingQueue<Request> topToDown) {
        this.topToDown = topToDown;
    }
    public PriorityBlockingQueue<Request> getDownToTop() {
        return downToTop;
    }
    public void setDownToTop(PriorityBlockingQueue<Request> downToTop) {
        this.downToTop = downToTop;
    } 
}
