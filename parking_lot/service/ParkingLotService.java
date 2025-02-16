package parking_lot.service;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import parking_lot.constant.VehicleType;
import parking_lot.model.Floor;
import parking_lot.model.ParkingLot;
import parking_lot.model.Slot;
import parking_lot.model.Token;
import parking_lot.model.User;
import parking_lot.model.Vehicle;
import parking_lot.model.VehicleService;
import parking_lot.repo.ParkedVehicleRepo;
import parking_lot.repo.ParkingSlotAvailabilityRepo;

public class ParkingLotService {
    private ParkingLot parkingLot;
    private Semaphore parkingSlotSemaphore;
    private Semaphore unparkingSlotSemaphore;
    private Semaphore parkingFloorSemaphore;
    private Semaphore unparkingFloorSemaphore;

    public ParkingLotService() {
        this.parkingLot = new ParkingLot();
        parkingSlotSemaphore = new Semaphore(4);
        unparkingSlotSemaphore = new Semaphore(4);
        parkingFloorSemaphore = new Semaphore(2);
        // unparkingFloorSemaphore = new Semaphore(2);
    }

    public CompletionStage<Token> parkVehicle(VehicleService vehicleService) {
        CompletionStage<Token> token = null;
        try {
            parkingSlotSemaphore.acquire(1);
            if (ParkedVehicleRepo.parkedVehicleMap.containsKey(vehicleService.getUser())) {
                System.out.println(vehicleService.getVehicle().getName() + " Vehicle for user " +
                        vehicleService.getUser().getName() + " already exist");
                throw new RuntimeException("User Vehicle is already parked");
            }

            token = allocateParkingSlot(vehicleService.getUser(), vehicleService.getVehicle());

        } catch (Exception e) {
            System.out.println("Error occurred while entering in lot " + e);
        } finally {
            parkingSlotSemaphore.release(1);
            return token;
        }
    }

    private CompletionStage<Token> allocateParkingSlot(User user, Vehicle vehicle) {
        if (this.parkingLot.getAvailableSlotCount().get() == 0) {
            throw new RuntimeException("Parking slot is not available");
        }

        Token token = new Token();
        CompletableFuture<Token> futureToken = null;
        try {
            this.parkingFloorSemaphore.acquire(1);

            List<Floor> floorList = this.parkingLot.getFloorList();
            AtomicBoolean isSlotAvailble = new AtomicBoolean(false);

            Map<AtomicInteger, ConcurrentHashMap<VehicleType, CompletionStage<List<Slot>>>> unAvaialbleParkingMap = ParkingSlotAvailabilityRepo.floorSlotUnavailabilityMap;

            while (!isSlotAvailble.get()) {
                AtomicInteger floorNumber = new AtomicInteger(ThreadLocalRandom.current().nextInt(4));
                Floor floor = floorList.get(floorNumber.get());

                CompletableFuture<List<Slot>> futureSlots = ParkingSlotAvailabilityRepo.floorSlotAvailabilityMap
                        .get(floor.getFloorNumber()).get(vehicle.getVehicleType()).toCompletableFuture();

                futureToken = futureSlots.thenApply(slots -> {

                    Slot slot = slots.get(0);
                    slots.remove(0);

                    if (!unAvaialbleParkingMap.containsKey(floor.getFloorNumber())) {
                        unAvaialbleParkingMap.put(floor.getFloorNumber(),
                                new ConcurrentHashMap<VehicleType, CompletionStage<List<Slot>>>());
                    }

                    if (!unAvaialbleParkingMap.get(floor.getFloorNumber())
                            .containsKey(vehicle.getVehicleType())) {
                        unAvaialbleParkingMap.get(floor.getFloorNumber())
                                .put(vehicle.getVehicleType(),
                                        CompletableFuture.completedStage(new CopyOnWriteArrayList()));
                    }

                    unAvaialbleParkingMap.get(floor.getFloorNumber())
                            .get(vehicle.getVehicleType()).thenAccept(slotList -> {
                                slotList.add(slot);
                            });
                    token.setId(String.valueOf(floor.getFloorNumber()) + "-" + String.valueOf(slot.getSlotNumber()));
                    token.setFloor(floor);
                    token.setSlot(slot);
                    token.setUser(user);
                    token.setVehicle(vehicle);
                    ParkedVehicleRepo.parkedVehicleMap.put(user, token);
                    isSlotAvailble.compareAndSet(false, true);
                    this.parkingLot.getAvailableSlotCount().compareAndSet(
                            this.parkingLot.getAvailableSlotCount().get(),
                            this.parkingLot.getAvailableSlotCount().getAndDecrement());
                    return token;
                });
            }

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            this.parkingFloorSemaphore.release(1);
            return futureToken;
        }

    }
}
