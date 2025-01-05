package cache.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

import cache.contants.Algorithm;
import cache.contants.Eviction;
import cache.service.DataSourceAccessService;

public class Cache<KEY, VALUE> {
    private Eviction evictionType;
    private long cacheEntryExpirationTime;
    private Algorithm algorithmType;
    private int cacheSize;
    private DataSourceAccessService<KEY, VALUE> dataSourceAccessService;
    private ConcurrentSkipListMap<AccessDetails, List<KEY>> priorityQueue;
    private List<Event<Record<KEY, VALUE>, KEY, VALUE>> eventQueue;
    private Map<KEY, Record<KEY, VALUE>> storageMap;

    public List<Event<Record<KEY, VALUE>, KEY, VALUE>> getEventQueue() {
        return eventQueue;
    }

    public Cache(
            Eviction evictionType, long cacheEntryExpirationTime,
            Algorithm algorithmType, int cacheSize, 
            DataSourceAccessService<KEY, VALUE> dataSourceAccessService) {
        this.algorithmType = algorithmType;
        this.cacheEntryExpirationTime = cacheEntryExpirationTime;
        this.cacheSize = cacheSize;
        this.evictionType = evictionType;
        this.priorityQueue = new ConcurrentSkipListMap<>((first, second)->{
            return (int) (first.getAccessTime() - second.getAccessTime());
        });
        
        this.dataSourceAccessService = dataSourceAccessService;
        this.storageMap = new ConcurrentHashMap<>();
        this.eventQueue = new CopyOnWriteArrayList<>();
    }

    public Map<KEY, Record<KEY, VALUE>> getStorage() {
        return this.storageMap;
    }

    public int getCacheSize() {
        return this.cacheSize;
    }

    public ConcurrentSkipListMap<AccessDetails, List<KEY>> getPriorityQueue() {
        return this.priorityQueue;
    }

    public DataSourceAccessService<KEY, VALUE> getDataSourceAccessService() {
        return this.dataSourceAccessService;
    }

    public long getCacheEntryExpirationTime() {
        return cacheEntryExpirationTime;
    }
}
