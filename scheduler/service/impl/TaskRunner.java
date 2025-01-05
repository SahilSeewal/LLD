package service.impl;
import java.time.Instant;

import dao.TaskQueue;
import service.ScheduledTask;

public class TaskRunner implements Runnable {
    private TaskQueue<ScheduledTask> taskQueue;
    public TaskRunner(TaskQueue<ScheduledTask> taskQueue) {
        this.taskQueue = taskQueue;
    }
    @Override
    public void run() {
        while(!taskQueue.isEmpty()) {
        ScheduledTask scheduledTask = taskQueue.take();
        long delay = scheduledTask.getExecTime() - Instant.now().toEpochMilli();
    
            if(delay > 0) {
                synchronized(this) {
                    try {
                    System.out.println(Thread.currentThread().getName() + "Thread waiting for delay: " + delay);    
                    taskQueue.add(scheduledTask);    
                    wait(delay);
                    // System.out.println(Thread.currentThread().getName() + "Thread "+ delay +" delay ended");
                    } catch (Exception e) {
                       System.out.println(e);
                    }    
            }
        }
        else {
            System.out.println(Thread.currentThread().getName() + "Thread is executing task");    
            String res = scheduledTask.execute();
            System.out.println(res);
            scheduledTask.setExecCount(scheduledTask.getExecCount() - 1);
            if(scheduledTask.getExecCount() > 0) {
                taskQueue.add(scheduledTask.nextTask());
            }
        }
        }
    }

    public void remove() {
        synchronized(this) {
            notify();
        }
    }
    
}
