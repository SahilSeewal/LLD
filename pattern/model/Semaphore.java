package pattern.model;

import pattern.constants.Type;

public class Semaphore {
    private Type sharedType;

    public Semaphore(Type sharedType) {
        this.sharedType = sharedType;
    }

    public synchronized Type getSharedType() {
        System.out.println("getsharedtype: " + Thread.currentThread().getName());
        return sharedType;
    }

    public void setSharedType(Type sharedType) {
        System.out.println("getsharedtype: " + Thread.currentThread().getName());
        this.sharedType = sharedType;
    }    
}
