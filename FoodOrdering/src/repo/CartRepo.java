package repo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

public class CartRepo {
	//{userId -> restauranrId, [foodId]}
	public static ConcurrentHashMap<String, ConcurrentHashMap<String, List<String>>>
		cart = new ConcurrentHashMap<>();	
}
