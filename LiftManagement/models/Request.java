package LiftManagement.models;

public class Request {
    private Floor fromFloor;
    private Floor toFloor;
    
    public Request(Floor fromFloor, Floor toFloor) {
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
    }
    public Floor getFromFloor() {
        return fromFloor;
    }
    public void setFromFloor(Floor fromFloor) {
        this.fromFloor = fromFloor;
    }
    public Floor getToFloor() {
        return toFloor;
    }
    public void setToFloor(Floor toFloor) {
        this.toFloor = toFloor;
    } 
}
