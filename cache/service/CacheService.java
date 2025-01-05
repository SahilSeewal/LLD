package cache.service;

import java.util.concurrent.CompletionStage;

import cache.model.Cache;

public interface CacheService<KEY, VALUE> {
    public CompletionStage<VALUE> get(Cache<KEY, VALUE> cache, KEY key);
    public CompletionStage<Void> set(Cache<KEY, VALUE> cache, KEY key, VALUE value);
}
