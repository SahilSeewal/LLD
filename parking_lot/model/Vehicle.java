package parking_lot.model;

import parking_lot.constant.VehicleType;

public class Vehicle {
    private String name;
    private VehicleType vehicleType;
    public Vehicle(String name, VehicleType vehicleType) {
        this.name = name;
        this.vehicleType = vehicleType;
    }
    public String getName() {
        return name;
    }
    public VehicleType getVehicleType() {
        return vehicleType;
    } 
}
