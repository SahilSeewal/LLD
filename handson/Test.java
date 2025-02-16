package handson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Test {
    public static void main(String[] args) {
        LeadershipBoard lb = new LeadershipBoard();
        List<Integer> scores = new ArrayList<>(Arrays.asList(10, 50, 200, 100));
        List<CompletableFuture<Void>> arr = new LinkedList<>(); 
        // for(Integer score: scores) {
        //     arr.add(CompletableFuture.runAsync(() -> {
        //         lb.addUser(score);
        //     }));
        // } 
           
        // CompletableFuture.allOf(arr.toArray(new CompletableFuture[0])).join();

        // for(int score: lb.getTopFiveUser()) {
        //     System.out.println(score);
        // }

        //c1 -> [10, 50], c2->[11, 30]
        lb.addCar("c1", 10, 50);
        lb.addCar("c2", 11, 30);
        lb.addCar("c1", 9, 44);
        lb.addCar("c1", 100, 144);
        lb.addCar("c1", 140, 244);

        lb.searchCar("c2", 11, 30);
    }
}
