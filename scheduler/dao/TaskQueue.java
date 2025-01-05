package dao;

import service.ScheduledTask;

public interface TaskQueue<T extends ScheduledTask>  {
    void add(T task);
    T take();
    boolean isEmpty();
}
