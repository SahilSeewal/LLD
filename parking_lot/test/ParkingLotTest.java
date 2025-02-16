package parking_lot.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import parking_lot.constant.VehicleType;
import parking_lot.model.Token;
import parking_lot.model.User;
import parking_lot.model.Vehicle;
import parking_lot.model.VehicleService;
import parking_lot.service.ParkingLotService;

public class ParkingLotTest {
    public static void main(String[] args) {
        ParkingLotService parkingLotService = new ParkingLotService();

        List<VehicleService> vehicleServiceList = new ArrayList<>();
        List<Vehicle> vehicleList = new ArrayList<>(
            Arrays.asList(
                new Vehicle("V1", VehicleType.TWO_WHEELER),
                new Vehicle("V2", VehicleType.FOUR_WHEELER),
                new Vehicle("V3", VehicleType.TWO_WHEELER),
                new Vehicle("V4", VehicleType.FOUR_WHEELER),
                new Vehicle("V5", VehicleType.TWO_WHEELER)
            )
        );
        
        for(int i=0;i<vehicleList.size(); i++) {
            
            VehicleService vehicleService = 
            new VehicleService(
                new User("user" + String.valueOf(i + 1)), 
                vehicleList.get(i));
            vehicleServiceList.add(vehicleService);
        }

        List<CompletionStage<Token>> futureTokenList = new CopyOnWriteArrayList<>();
        List<CompletableFuture<Void>> futureTaskList = new CopyOnWriteArrayList<>();
        for(int i=0; i< vehicleServiceList.size(); i++) {  
            final int idx = i;   
            CompletableFuture<Void> futureTask = CompletableFuture.runAsync(()->{
                CompletionStage<Token> token = parkingLotService.parkVehicle(vehicleServiceList.get(idx));
                futureTokenList.add(token);
            });

        futureTaskList.add(futureTask);    
        }
        
        CompletableFuture.allOf(futureTaskList.toArray(new CompletableFuture[0])).join();
        //System.out.println(futureList.size());
        for(CompletionStage<Token> stageToken: futureTokenList) {
            CompletableFuture<Token> token = stageToken.toCompletableFuture();
            Token currentToken = token.join();
            System.out.println(currentToken.getUser().getName() + " -> " 
            + currentToken.getId() + " -> " + currentToken.getVehicle().getName());
        }
    }
}
