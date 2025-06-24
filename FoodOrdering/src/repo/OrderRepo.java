package repo;

import java.util.concurrent.ConcurrentHashMap;

import model.Order;

public class OrderRepo {
	public static ConcurrentHashMap<String, Order> orderMap =  new ConcurrentHashMap<>();
}
