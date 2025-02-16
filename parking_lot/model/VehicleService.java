package parking_lot.model;

public class VehicleService {
    private User user;
    private Vehicle vehicle;
    public VehicleService(User user, Vehicle vehicle) {
        this.user = user;
        this.vehicle = vehicle;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    } 
}
