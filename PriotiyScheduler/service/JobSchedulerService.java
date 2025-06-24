package priorityscheduler.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import priorityscheduler.model.Job;

public class JobSchedulerService {
    private PriorityBlockingQueue<Job> priorityBlockingQueue;
    public JobSchedulerService() {
        this.priorityBlockingQueue = new PriorityBlockingQueue<>(100, new priorityBlockingQueueComparator());
    }

    public class priorityBlockingQueueComparator implements Comparator<Job> {

        @Override
        public int compare(Job o1, Job o2) {
            return o1.getPriority().ordinal() - o2.getPriority().ordinal();
        }            
    }

    public void scheduleJob(Job job) {
        this.priorityBlockingQueue.offer(job);
    }

    public List<Job> getScheduledJobList() {
        List<Job> jobList = new ArrayList<>();
        while(!this.priorityBlockingQueue.isEmpty()){
            try {
                jobList.add(this.priorityBlockingQueue.take());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
       return jobList; 
    }
}
