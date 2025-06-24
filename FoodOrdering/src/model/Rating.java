package model;

import java.util.concurrent.atomic.AtomicInteger;

public enum Rating {
	ONE(new AtomicInteger(1)), 
	TWO(new AtomicInteger(2)), 
	THREE(new AtomicInteger(3)), 
	FOUR(new AtomicInteger(4)), 
	FIVE(new AtomicInteger(5));
	
	private AtomicInteger value;

	Rating(AtomicInteger value) {
        this.value = value;
    }
	
	public AtomicInteger getValue() {
		return value;
	}

	public void setValue(AtomicInteger value) {
		this.value = value;
	}
}
