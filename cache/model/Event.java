package cache.model;

import cache.contants.Type;
import cache.model.Record;

public class Event<T extends Record<KEY, VALUE>, KEY, VALUE> {
    private T record;
    private Type type; 
    public Event(T record, Type type) {
        this.record = record;
        this.type = type;
    }    
}
