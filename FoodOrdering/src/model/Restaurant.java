package model;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Restaurant {
	private String restaurantId;
	private String restaurantName;
	private AtomicInteger totalRating;
	private AtomicInteger ratedUserCount;
	private List<String> foodIds;

	public Restaurant(String restaurantId, String restaurantName, List<String> foodIds) {
		super();
		this.restaurantId = restaurantId;
		this.restaurantName = restaurantName;
		this.foodIds = foodIds;
		this.totalRating = new AtomicInteger(0);
		this.ratedUserCount = new AtomicInteger(0);
	}

	public AtomicInteger getRatedUserCount() {
		return ratedUserCount;
	}

	public void setRatedUserCount(AtomicInteger ratedUserCount) {
		this.ratedUserCount = ratedUserCount;
	}
	
	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public void setTotalRating(AtomicInteger totalRating) {
		this.totalRating = totalRating;
	}
	public void setFoodIds(List<String> foodIds) {
		this.foodIds = foodIds;
	}

	public String getRestaurantId() {
		return restaurantId;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public AtomicInteger getTotalRating() {
		return totalRating;
	}
	public List<String> getFoodIds() {
		return foodIds;
	}
}
