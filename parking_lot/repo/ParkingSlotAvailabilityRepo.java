package parking_lot.repo;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import parking_lot.constant.VehicleType;
import parking_lot.model.Slot;

public class ParkingSlotAvailabilityRepo {
    public static ConcurrentHashMap<AtomicInteger, ConcurrentHashMap<VehicleType, CompletionStage<List<Slot>>>> 
    floorSlotAvailabilityMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<AtomicInteger, ConcurrentHashMap<VehicleType, CompletionStage<List<Slot>>>> 
    floorSlotUnavailabilityMap = new ConcurrentHashMap<>();
}
