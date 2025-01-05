package cache.model;

public class Record<KEY, VALUE> {
    private KEY key;
    private VALUE value;
    private AccessDetails accessDetails;

    public Record(KEY key, VALUE value, AccessDetails accessDetails) {
        this.key = key;
        this.value = value;
        this.accessDetails = accessDetails; 
    }

    public KEY getKey() {
        return key;
    }

    public void setAccessDetails(AccessDetails accessDetails) {
        this.accessDetails = accessDetails;
    }

    public VALUE getValue() {
        return value;
    }

    public AccessDetails getAccessDetails() {
        return accessDetails;
    }
}
