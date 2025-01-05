package service;

import java.util.Comparator;

public class ScheduledTaskComparator implements Comparator<ScheduledTask> {
    @Override
    public int compare(ScheduledTask t1, ScheduledTask t2) {
        if(t1.getExecTime() < t2.getExecTime()) {
            return 1;
        }  else if(t1.getExecTime() > t2.getExecTime()) {
            return -1;
        }
        return 0;
    }
}