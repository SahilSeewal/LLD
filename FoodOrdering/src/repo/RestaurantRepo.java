package repo;

import java.util.concurrent.ConcurrentHashMap;

import model.Rating;
import model.Restaurant;

public class RestaurantRepo {
	public static ConcurrentHashMap<String, Restaurant> restaurantMap = new ConcurrentHashMap<>();
	public static ConcurrentHashMap<String, ConcurrentHashMap<String, Rating>> userRestaurantRatingMap = new ConcurrentHashMap<>();

}
