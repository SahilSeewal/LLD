package LiftManagement.models;

import java.util.concurrent.atomic.AtomicInteger;

public class Floor {
    private AtomicInteger val;

    public Floor(AtomicInteger val) {
        this.val = val;
    }

    public AtomicInteger getVal() {
        return val;
    }

    public void setVal(AtomicInteger val) {
        this.val = val;
    }
}
