package cache.service.impl;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;

import cache.model.DataSource;
import cache.service.DataSourceAccessService;

public class WriteThroughServiceImpl<KEY, VALUE> implements DataSourceAccessService<KEY, VALUE>{
    private DataSource<KEY, VALUE> dataSource;

    public WriteThroughServiceImpl() {
        this.dataSource = new DataSource<KEY, VALUE>();
    }

    @Override
    public CompletionStage<Void> persist(KEY key, VALUE value) {
        this.dataSource.getDataMap().put(key, value);
        return CompletableFuture.completedStage(null);
    }
    @Override
    public CompletionStage<VALUE> load(KEY key) {
        if(this.dataSource.getDataMap().containsKey(key)) {
            return CompletableFuture
            .completedFuture(this.dataSource.getDataMap().get(key));
        }
        return CompletableFuture.failedStage(new NullPointerException());
    }    
}
