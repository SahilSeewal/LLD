package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.Rating;
import service.RestaurantService;

public class FoodOrderingTest {

	public static void main(String []args) {
		RestaurantService restaurantService = new RestaurantService();

		List<String> restaurantNames = Arrays.asList("r1", "r2");
		List<String> restaurantIds = new CopyOnWriteArrayList();
		List<List<String>> foodList = Arrays.asList(
				Arrays.asList("f1", "f2"), 
				Arrays.asList("f3", "f4"));

		//List<CompletableFuture<Void>> restrauntResList = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		CompletableFuture<Void>[] restrauntResList = new CompletableFuture[3];

		for(int i=0; i<restaurantNames.size(); i++) {
			final int idx = i;
			//CompletableFuture<Void> restrauntRes = 
			restrauntResList[idx] = CompletableFuture.supplyAsync(() -> {
				return restaurantService.addNewRestaurant(restaurantNames.get(idx), foodList.get(idx)).join();
			}, executor).thenAccept(restrauntId -> {restaurantIds.add(restrauntId);});
//			restrauntResList.add(restrauntRes);
		}
		
//		CompletableFuture.allOf(restrauntResList.toArray(new CompletableFuture[0])).join();
		CompletableFuture.allOf(restrauntResList[0], restrauntResList[1]).join();
		executor.shutdown();
		System.out.println(restaurantIds.size() + " -> " + restrauntResList[0] + " -> "+restrauntResList[1]);
		//	System.out.println("Restaurant list len: " + restrauntResList.size());
		System.out.println("Restaurant list len: " + restrauntResList.length);
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
			
		CompletableFuture<Void> restrauntRateRes = 
					
		CompletableFuture.runAsync(() -> {
			 	restaurantService.rateRestaurant("u1", restaurantIds.get(0), Rating.FOUR).join();
				restaurantService.rateRestaurant("u1", restaurantIds.get(1), Rating.ONE).join();
			}).thenAccept(__->{
				restaurantService.getTopKRestaurantIdsByRating(1).join();}); 
			
//			restrauntResList.add(restrauntRateRes);
		restrauntResList[2]= restrauntRateRes;
		
//		CompletableFuture.allOf(restrauntResList.toArray(new CompletableFuture[0])).join();
		CompletableFuture.allOf(restrauntResList[2]).join();
	}
}
