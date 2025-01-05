package cache.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

import cache.model.Record;

import cache.model.Event;
import cache.contants.Type;
import cache.model.AccessDetails;
import cache.model.Cache;
import cache.service.CacheService;
import cache.service.DataSourceAccessService;

public class CacheServiceImpl<KEY, VALUE> implements CacheService<KEY, VALUE>{

    @Override
    public CompletionStage<VALUE> get(Cache<KEY, VALUE> cache, KEY key) {
        
        List<Event<Record<KEY, VALUE>, KEY, VALUE>> eventQueue = cache.getEventQueue();
        Map<KEY, Record<KEY, VALUE>> cacheStorage = cache.getStorage();
        Map<AccessDetails, List<KEY>> priorityQueue = cache.getPriorityQueue();
        
        CompletableFuture<Record<KEY, VALUE>> futureRecord = 
        CompletableFuture.supplyAsync(()-> {
            System.out.println(Thread.currentThread().getName() + " thread to get data in cache");
            if(!cacheStorage.containsKey(key) || !priorityQueue.containsKey(key)) {
                CompletableFuture<VALUE> futureData = loadFromSource(cache.getDataSourceAccessService(), key).toCompletableFuture();

            // return new Record<KEY,VALUE>(key, null, null);

                try {
                    Record<KEY, VALUE> record = futureData.thenApply(data -> {
                    // return new CompletableFuture.toCompletableFuture(new Record<>());
                        return addToCache(cache, createRecord(key, data, createAccessDetails(System.currentTimeMillis(), 0)));
                    }).get();
                    return record;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            } 
            Record<KEY, VALUE> record = cacheStorage.get(key);
            
            System.out.println(Thread.currentThread().getName() + " " + record.getKey() + " " + record.getValue());
            System.out.println(Thread.currentThread().getName() + " " + priorityQueue);
            AccessDetails accessDetails = record.getAccessDetails();
            priorityQueue.get(accessDetails).remove(key);
            return addToCache(cache, record);
        });
        return futureRecord.thenApply(recordEntry -> {
            return recordEntry.getValue();
        });
    }

    @Override
    public CompletionStage<Void> set(Cache<KEY, VALUE> cache, KEY key, VALUE value) {
        List<Event<Record<KEY, VALUE>, KEY, VALUE>> eventQueue = cache.getEventQueue();
        Map<KEY, Record<KEY, VALUE>> cacheStorage = cache.getStorage();
        ConcurrentSkipListMap<AccessDetails, List<KEY>> priorityQueue = cache.getPriorityQueue();

        return CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " thread to set data in cache");
            int accessCount = 0;
            if(cacheStorage.containsKey(key)) {
                Record<KEY, VALUE> record = cacheStorage.get(key);
                AccessDetails accessDetails = record.getAccessDetails();
                accessCount = accessDetails.getTimesAccessed();
                priorityQueue.get(accessDetails).remove(key);
                    if(priorityQueue.get(accessDetails).isEmpty()) {
                        priorityQueue.remove(accessDetails);
                    }

                cacheStorage.remove(key);
                eventQueue.add(createEvent(record, Type.REPLACED));
            }
            AccessDetails newAccessDetails = createAccessDetails(System.currentTimeMillis(), accessCount + 1);
            Record<KEY, VALUE> newRecord = createRecord(key, value, newAccessDetails);
            cacheStorage.put(key, newRecord);
            cache.getDataSourceAccessService().persist(key, value);
            priorityQueue.putIfAbsent(newAccessDetails, new CopyOnWriteArrayList<>());
            priorityQueue.get(newAccessDetails).add(key);
            eventQueue.add(createEvent(newRecord, Type.LOAD));
        });
    }
    
    private Record<KEY, VALUE> createRecord(KEY key, VALUE value, AccessDetails accessDetails) {
        return new Record<KEY, VALUE>(key, value, accessDetails); 
    }

    private AccessDetails createAccessDetails(long time, int count) {
        return new AccessDetails(time, count);
    }
    
    private CompletionStage<VALUE> loadFromSource(DataSourceAccessService<KEY, VALUE> dataSource, KEY key) {
          return dataSource.load(key).whenComplete((data, throwable) ->{
            if(throwable == null) {
                // return data;
            } else {
                System.out.println("load error==>  " + throwable.getStackTrace());
                CompletableFuture.failedStage(throwable);
            }
        });
    }


    private Record<KEY, VALUE> addToCache(Cache<KEY, VALUE> cache, Record<KEY, VALUE> record) {
        ConcurrentSkipListMap<AccessDetails, List<KEY>> priorityQueue = cache.getPriorityQueue();  
        List<Event<Record<KEY, VALUE>, KEY, VALUE>> eventQueue = cache.getEventQueue();
        manageCache(cache);
        
        KEY recordKey = record.getKey();
        AccessDetails accessDetails = new AccessDetails(System.currentTimeMillis(), record.getAccessDetails().getTimesAccessed() + 1);
        record.setAccessDetails(accessDetails);
        cache.getStorage().put(recordKey, record);
        priorityQueue.putIfAbsent(accessDetails, new CopyOnWriteArrayList<>());
        priorityQueue.get(accessDetails).add(recordKey);
        eventQueue.add(createEvent(record, Type.LOAD));
        return record;
    }

    private void manageCache(Cache<KEY, VALUE> cache) {
        ConcurrentSkipListMap<AccessDetails, List<KEY>> priorityQueue = cache.getPriorityQueue();  
        List<Event<Record<KEY, VALUE>, KEY, VALUE>> eventQueue = cache.getEventQueue();
        if(cache.getCacheSize() == cache.getStorage().size()) {
            priorityQueue = cache.getPriorityQueue();
            while(!priorityQueue.isEmpty() && hasExpired(cache, priorityQueue.firstKey().getAccessTime())) {
                List<KEY> keyList = priorityQueue.pollFirstEntry().getValue();
                for(KEY key: keyList) {
                    Record<KEY, VALUE> record = cache.getStorage().remove(key);                    
                    eventQueue.add(createEvent(createRecord(key, record.getValue(), record.getAccessDetails()), Type.EXPIRE)); 
                }
            }
        
            if(cache.getCacheSize() == cache.getStorage().size()) {
                AccessDetails accessDetails = priorityQueue.pollFirstEntry().getKey();
                Record<KEY, VALUE> record = cache.getStorage().remove(accessDetails);
                eventQueue.add(createEvent(record, Type.REPLACED));
            }    
        }
    }

    private Event<Record<KEY, VALUE>, KEY, VALUE> createEvent(Record<KEY, VALUE> record, Type type) {
        return new Event<>(record, type);
    }

    private boolean hasExpired(Cache<KEY, VALUE> cache, long accessTime) {
        return (System.currentTimeMillis() - accessTime) > cache.getCacheEntryExpirationTime();
    }
}
