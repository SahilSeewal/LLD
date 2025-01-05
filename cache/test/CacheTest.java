package cache.test;

import java.util.concurrent.CompletableFuture;

import cache.contants.Algorithm;
import cache.contants.Eviction;
import cache.model.Cache;
import cache.service.DataSourceAccessService;
import cache.service.impl.CacheServiceImpl;
import cache.service.impl.WriteThroughServiceImpl;

public class CacheTest {
    private static Cache<String, String> cache;
    private static long cacheEntryExpirationTime = 5000;
    private static int cacheSize = 5;
    private static DataSourceAccessService<String, String> dataSourceAccessService;
    private static CacheServiceImpl<String, String> cacheService;
    public static void main(String[] args) {
        dataSourceAccessService = new WriteThroughServiceImpl<String, String>();
        cacheService = new CacheServiceImpl<String, String>();
         cache = new Cache<String, String>(
             Eviction.LRU,
             cacheEntryExpirationTime,
             Algorithm.WRITE_THROUGH,
             cacheSize,
             dataSourceAccessService);
        CompletableFuture<Void> future1 = cacheService.set(cache, "Ram", "123").toCompletableFuture();
        CompletableFuture<String> future2 =  cacheService.get(cache, "Ram").toCompletableFuture();
        CompletableFuture.allOf(
        future1, future2
        ).thenAccept(__-> {
            future1.thenAccept(result1 -> System.out.println("Value of future1: " + result1));
            future2.thenAccept(result2 -> System.out.println("Value of future2: " + result2));

        }).join();
        
        //CompletableFuture<String> val = cacheService.get(cache, "Ram").toCompletableFuture();
        // System.out.println(val.join());
    } 
}