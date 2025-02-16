package parking_lot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingLot {
    private List<Floor> floorList;
    private int floorCount = 4;
    private int totalSlotCount = 100;
    private volatile AtomicInteger availableSlotCount;

    
    public ParkingLot() {
        this.floorList = new CopyOnWriteArrayList<>();
        for(int i=0; i < this.floorCount; i++) {
            floorList.add(new Floor(i+1));
        }
        this.availableSlotCount = new AtomicInteger(this.totalSlotCount);
    }
    
    public List<Floor> getFloorList() {
        return floorList;
    }
    public int getFloorCount() {
        return floorCount;
    }

    public int getTotalSlotCount() {
        return totalSlotCount;
    }

    public void setAvailableSlotCount(AtomicInteger availableSlotCount) {
        this.availableSlotCount = availableSlotCount;
    }

    public AtomicInteger getAvailableSlotCount() {
        return availableSlotCount;
    } 
       
}
