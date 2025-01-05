package controller;
import java.util.ArrayList;
import java.util.List;

import dao.TaskQueue;
import service.ScheduledTask;
import service.impl.TaskRunner;

public class TaskScheduler {
    private TaskQueue<ScheduledTask> taskQueue;
    public TaskScheduler(TaskQueue<ScheduledTask> taskQueue) {
        this.taskQueue = taskQueue;
    }
    public void triggerScheduledTasks() {
      List<Thread> threadList = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
        Thread thread = new Thread(new TaskRunner(this.taskQueue));
        threadList.add(thread);
        thread.start();
      }  
    }
}
