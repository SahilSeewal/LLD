package priorityscheduler.model;

import priorityscheduler.constant.Type;

public class Job {
    private String jobName;
    private Type priority;
    public String getJobName() {
        return jobName;
    }
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public Type getPriority() {
        return priority;
    }
    public Job(String jobName, Type priority) {
        this.jobName = jobName;
        this.priority = priority;
    }
    public void setPriority(Type priority) {
        this.priority = priority;
    }
}
