package dao.impl;

import java.util.concurrent.PriorityBlockingQueue;

import dao.TaskQueue;
import service.ScheduledTask;

public class PriorityTaskQueue implements TaskQueue<ScheduledTask> {
    private PriorityBlockingQueue<ScheduledTask> priorityBlockingQueue;
    
    public PriorityTaskQueue(PriorityBlockingQueue<ScheduledTask> priorityBlockingQueue) {
        this.priorityBlockingQueue = priorityBlockingQueue;
    }
    @Override
    public void add(ScheduledTask task) {
        priorityBlockingQueue.offer(task);
    }

    @Override
    public ScheduledTask take() {
        try {
            return priorityBlockingQueue.take();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
    }

    @Override
    public boolean isEmpty() {
        return priorityBlockingQueue.isEmpty();
    }
}
