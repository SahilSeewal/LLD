package service;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import model.Rating;
import model.Restaurant;
import repo.RestaurantRepo;

public class RestaurantService {
	
	private ConcurrentSkipListSet<String> restaurantsSortedByRating;
	
	public RestaurantService() {
		this.restaurantsSortedByRating = new ConcurrentSkipListSet<>(new RestaurantRatingComparator());
	}
	
	private class RestaurantRatingComparator implements Comparator<String> {
		@Override
		public int compare(String restaurantId1, String restaurantId2) {
			AtomicInteger userCount1 = RestaurantRepo.restaurantMap.get(restaurantId1).getRatedUserCount();
			AtomicInteger totalRating1 = RestaurantRepo.restaurantMap.get(restaurantId1).getTotalRating();
			
			AtomicInteger userCount2 = RestaurantRepo.restaurantMap.get(restaurantId2).getRatedUserCount();
			AtomicInteger totalRating2 = RestaurantRepo.restaurantMap.get(restaurantId2).getTotalRating();
			
			if(userCount1.get() == 0 || userCount2.get() == 0 ) return 0;
			
			return totalRating1.get()/userCount1.get() - totalRating2.get()/userCount2.get();
		}
	}
	
	public CompletableFuture<String> addNewRestaurant(String restaurantName, List<String> foodIds) {
		String restaurantId = Integer.toString(new Random().nextInt()); 
		Restaurant newRestaurant = new Restaurant(restaurantId, restaurantName, foodIds);
		RestaurantRepo.restaurantMap.put(restaurantId, newRestaurant);
		this.restaurantsSortedByRating.add(restaurantId);
		System.out.println("restrauntName: " + restaurantName + " restrauntId: " + restaurantId);
		return CompletableFuture.completedFuture(restaurantId);
	}
	
	public CompletableFuture<List<String>> getTopKRestaurantIdsByRating(int count) {
		List<String> restaurantIds = new CopyOnWriteArrayList<>();
		AtomicInteger currCount = new AtomicInteger(0);
		System.out.println("In top k method");
		for(String restaurantId: this.restaurantsSortedByRating) {
			restaurantIds.add(restaurantId);
			if(currCount.getAndIncrement() == count) break;
			
			System.out.println("restaurant id: " + restaurantId);
		}
		return CompletableFuture.completedFuture(restaurantIds);
	}
	
	public CompletableFuture<Void>  rateRestaurant(String userId, String restaurantId, Rating rating) {
		System.out.println("Inside rate restaurant method");
		RestaurantRepo.userRestaurantRatingMap.computeIfAbsent(userId, k-> new ConcurrentHashMap<>());

		RestaurantRepo.userRestaurantRatingMap.get(userId).put(restaurantId, rating);

		AtomicInteger userCount = RestaurantRepo.restaurantMap.get(restaurantId).getRatedUserCount();
		AtomicInteger totalRating = RestaurantRepo.restaurantMap.get(restaurantId).getTotalRating();

		RestaurantRepo.restaurantMap.get(restaurantId).setRatedUserCount(new AtomicInteger(userCount.getAndIncrement()));
		totalRating.addAndGet(rating.getValue().get());
		System.out.println(rating.getValue().get());
		return CompletableFuture.completedFuture(null);
	}
}
