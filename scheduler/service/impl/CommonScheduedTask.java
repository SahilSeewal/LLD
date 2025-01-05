package service.impl;
import java.time.Instant;

import service.ScheduledTask;


public class CommonScheduedTask extends ScheduledTask{
    private long execTime;
    private int execCount;
    public CommonScheduedTask(long execTime, int execCount) {
        this.execTime = execTime;
        this.execCount = execCount;
    }
    @Override
    public String execute() {
        return "Task is executed by thread: " + Thread.currentThread().getName();
    }
    
    @Override
    public long getExecTime() {
        return this.execTime;
    }

    @Override
    public ScheduledTask nextTask() {
        return new CommonScheduedTask(Instant.now().toEpochMilli(), execCount);
    }

    @Override
    public int getExecCount() {
        return this.execCount;
    }

    @Override
    public void setExecCount(int execCount) {
        this.execCount = execCount;
    }
}
