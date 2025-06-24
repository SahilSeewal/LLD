package priorityscheduler.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import priorityscheduler.constant.Type;
import priorityscheduler.model.Job;
import priorityscheduler.service.JobSchedulerService;
import priorityscheduler.service.JobService;

public class PrioritySchedulerTest {
    public static void main(String []args) {
        JobSchedulerService jobSchedulerService = new JobSchedulerService();
        JobService jobService = new JobService();


        Job[] jobs={
            jobService.createJob("Job1", Type.HIGH),
            jobService.createJob("Job2", Type.LOW),
            jobService.createJob("Job3", Type.MEDIUM),
            jobService.createJob("Job9", Type.MEDIUM),
            jobService.createJob("Job8", Type.LOW),
            jobService.createJob("Job6", Type.HIGH)
        };

        List<CompletableFuture<Void>> jobSchedulingTaskList = new ArrayList<>();
        for(int i=0;i<jobs.length; i++) {
            final int idx = i;
            CompletableFuture<Void> task = CompletableFuture.runAsync(()->{
                 jobSchedulerService.scheduleJob(jobs[idx]);
            }); 
            jobSchedulingTaskList.add(task);   
        }

        CompletableFuture.allOf(jobSchedulingTaskList
                         .toArray(new CompletableFuture[0])).join();
        
        List<Job> scheduledJobs = jobSchedulerService.getScheduledJobList();
        for(Job job: scheduledJobs) {
            System.out.println(job.getJobName() + " " + job.getPriority());
        }  
    }   
}
