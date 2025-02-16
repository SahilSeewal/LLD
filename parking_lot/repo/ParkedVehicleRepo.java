package parking_lot.repo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import parking_lot.model.Token;
import parking_lot.model.User;

public class ParkedVehicleRepo {
    public static ConcurrentHashMap<User, Token> parkedVehicleMap = new ConcurrentHashMap<>();
}
