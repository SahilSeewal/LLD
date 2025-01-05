package cache.model;

public class AccessDetails {
    private long accessTime;
    private int timesAccessed;
    public AccessDetails(long accessTime, int timesAccessed) {
        this.accessTime = accessTime;
        this.timesAccessed = timesAccessed;
    }
    public void setAccessTime(long accessTime) {
        this.accessTime = accessTime;
    }
    public void setTimesAccessed(int timesAccessed) {
        this.timesAccessed = timesAccessed;
    }
    public long getAccessTime() {
        return accessTime;
    }
    public int getTimesAccessed() {
        return timesAccessed;
    }
}
