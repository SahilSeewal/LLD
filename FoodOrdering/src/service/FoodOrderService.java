package service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import model.Order;
import model.OrderStatus;
import model.Rating;
import repo.CartRepo;
import repo.OrderRepo;
import repo.UserOrderRepo;

public class FoodOrderService {
	public void addToCart(String userId, String foodId, String restaurantId) {

		CartRepo.cart.computeIfAbsent(userId, k->new ConcurrentHashMap<String, List<String>>());
		CartRepo.cart.get(userId).computeIfAbsent(restaurantId, k-> new CopyOnWriteArrayList<>());
		CartRepo.cart.get(userId).get(restaurantId).add(foodId);
		System.out.println("FoodId " + foodId + " of restaurantId "+ restaurantId +"added to cart for user " + userId);
	}

	public void orderFood(String userId, String restaurantId, List<String> foodIds) {
		if(!CartRepo.cart.containsKey(userId) || !CartRepo.cart.get(userId).containsKey(restaurantId)) {
			System.out.println("Food is not present in cart!");
			return;
		}

		Order newOrder = new Order(Integer.toString(new Random().nextInt()), restaurantId, foodIds);
		String newOrderId = newOrder.getOrderId();

		OrderRepo.orderMap.put(newOrderId, newOrder);
		UserOrderRepo.userOrderMap.computeIfAbsent(userId, k-> new CopyOnWriteArrayList());
		UserOrderRepo.userOrderMap.get(userId).add(newOrderId);

		System.out.println("Order created for userid " + userId + " with orderId " + newOrder.getOrderId());
	}

	public void orderDelivered(String userId, String orderId) {
		if(!OrderRepo.orderMap.containsKey(orderId) || !UserOrderRepo.userOrderMap.containsKey(userId)) {
			System.out.println("Order is not present!");
			return;
		}

		OrderRepo.orderMap.get(orderId).setStatus(OrderStatus.DELIVERED);
		//UserOrderRepo.userOrderMap.get(userId).remove(orderId);

		System.out.println("Order with Orderid : " + orderId + " delivered to userid " + userId + " successfully!");
	}

	public void rateOrder(String orderId, Rating rating) {
		if(!OrderRepo.orderMap.containsKey(orderId)) {
			System.out.println("Order is not present!");
			return;
		}
		OrderRepo.orderMap.get(orderId).setRating(rating);
		System.out.println("Order with Orderid : " + orderId + " rated as " + rating);
	}

}
