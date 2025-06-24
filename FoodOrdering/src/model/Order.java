package model;

import java.util.List;

public class Order {
	private String orderId;
	private String restaurantId;
	private List<String> foodIds;
	private OrderStatus status = OrderStatus.IN_PROCESS;
	private Rating rating;

	public Order(String orderId, String restaurantId, List<String> foodIds) {
		super();
		this.orderId = orderId;
		this.restaurantId = restaurantId;
		this.foodIds = foodIds;

	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public void setFoodIds(List<String> foodIds) {
		this.foodIds = foodIds;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public List<String> getOrderIds() {
		return foodIds;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
