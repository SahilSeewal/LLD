package priorityscheduler.service;

import priorityscheduler.constant.Type;
import priorityscheduler.model.Job;

public class JobService {
    public Job createJob(String jobName, Type priority) {
        return new Job(jobName, priority);
    }   
}
