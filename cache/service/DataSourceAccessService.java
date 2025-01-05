package cache.service;

import java.util.concurrent.CompletionStage;

public interface DataSourceAccessService<KEY, VALUE> {
    public CompletionStage<Void> persist(KEY key, VALUE value);
    public CompletionStage<VALUE> load(KEY key);
}
