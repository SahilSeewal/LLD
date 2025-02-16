package parking_lot.model;

import java.util.concurrent.atomic.AtomicInteger;

import parking_lot.constant.VehicleType;

public class Slot {
    private AtomicInteger slotNumber;
    private VehicleType vehicleType;

    public Slot(AtomicInteger slotNumber, VehicleType vehicleType) {
        this.slotNumber = slotNumber;
        this.vehicleType = vehicleType;
    }

    public AtomicInteger getSlotNumber() {
        return slotNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }
}
