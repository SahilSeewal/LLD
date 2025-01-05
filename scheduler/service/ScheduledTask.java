package service;
public abstract class ScheduledTask {
    abstract public String execute();
    abstract public long getExecTime();
    abstract public ScheduledTask nextTask();
    abstract public int getExecCount();
    abstract public void setExecCount(int execCount);
}
