package test;
import java.time.Instant;
import java.util.concurrent.PriorityBlockingQueue;

import controller.TaskScheduler;
import dao.TaskQueue;
import dao.impl.PriorityTaskQueue;
import service.ScheduledTask;
import service.ScheduledTaskComparator;
import service.impl.CommonScheduedTask;


public class Schedule {

    public static void main(String[] args){
    ScheduledTask task1 = new CommonScheduedTask(Instant.now().toEpochMilli() + 2000, 2);
    ScheduledTask task2 = new CommonScheduedTask(Instant.now().toEpochMilli() + 100, 2);
    ScheduledTask task3 = new CommonScheduedTask(Instant.now().toEpochMilli() + 500, 2);
    ScheduledTask task4 = new CommonScheduedTask(Instant.now().toEpochMilli() + 200, 2);

    TaskQueue<ScheduledTask> taskQueue = new PriorityTaskQueue(new PriorityBlockingQueue<>(10, new ScheduledTaskComparator()));
    taskQueue.add(task1);
    taskQueue.add(task2);
    taskQueue.add(task3);
    taskQueue.add(task4);
    
    TaskScheduler taskScheduler = new TaskScheduler(taskQueue);
    System.out.println("Triggered job scheduler");
    taskScheduler.triggerScheduledTasks();
    // try {
    //   //  Thread.currentThread().join();
    // } catch (InterruptedException e) {
    //     // TODO Auto-generated catch block
    //     e.printStackTrace();
    // }
}
}