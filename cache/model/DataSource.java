package cache.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataSource<KEY, VALUE> {

    private Map<KEY, VALUE> dataMap;

    public DataSource() {
        this.dataMap = new ConcurrentHashMap<>();
    }

    public Map<KEY, VALUE> getDataMap() {
        return this.dataMap;
    }
}
