package repo;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

// user id-> [order id]
public class UserOrderRepo {
	public static ConcurrentHashMap<String, List<String>> userOrderMap =  new ConcurrentHashMap<>();
}
