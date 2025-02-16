package parking_lot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import parking_lot.constant.VehicleType;
import parking_lot.repo.ParkingSlotAvailabilityRepo;

public class Floor {

    private AtomicInteger floorNumber;
    private int slotCount = 25;
    private List<Slot> slotList;
    
    public AtomicInteger getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(AtomicInteger floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getSlotCount() {
        return slotCount;
    }

    public void setSlotCount(int slotCount) {
        this.slotCount = slotCount;
    }

    public List<Slot> getSlotList() {
        return slotList;
    }

    public void setSlotList(List<Slot> slotList) {
        this.slotList = slotList;
    }

    public Floor(int floorNumber) {
        this.floorNumber = new AtomicInteger(floorNumber);
        this.slotList = new CopyOnWriteArrayList<>();
        Random random = new Random();
        for (int i = 0; i < slotCount; i++) {
            int randomOrdinal = random.nextInt(2);
            VehicleType vehicleType = VehicleType.values()[randomOrdinal];
            Slot slot = new Slot(new AtomicInteger(i + 1), vehicleType);
            this.slotList.add(slot);

            ParkingSlotAvailabilityRepo.floorSlotAvailabilityMap
                    .putIfAbsent(this.floorNumber, new ConcurrentHashMap<>());

            ParkingSlotAvailabilityRepo.floorSlotAvailabilityMap.get(this.floorNumber)
                    .putIfAbsent(vehicleType,
                            CompletableFuture.completedFuture(new CopyOnWriteArrayList<>()));

            CompletionStage<List<Slot>> updatedSlotList = ParkingSlotAvailabilityRepo.floorSlotAvailabilityMap
                    .get(this.floorNumber)
                    .get(vehicleType)
                    .toCompletableFuture().thenCompose((slotList) -> {
                        slotList.add(slot);
                        return CompletableFuture.completedStage(slotList);
                    });

            ParkingSlotAvailabilityRepo.floorSlotAvailabilityMap.get(this.floorNumber)
                    .put(vehicleType, updatedSlotList);
        }
    }
}
