package newApproaches.approach1.model;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import newApproaches.approach1.constant.LiftState;

public class Lift {
	private int liftNumber;
	private int maxFloorCount;
	private int currentFloor;
	private LiftState state;
	private PriorityBlockingQueue<Integer> upRequest;
	private PriorityBlockingQueue<Integer> downRequest;
	
	public Lift() {
		this.upRequest = new PriorityBlockingQueue(100, Comparator.reverseOrder());
		this.downRequest = new PriorityBlockingQueue(100);
	}
	
	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}
	
	public PriorityBlockingQueue<Integer> getUpRequest() {
		return upRequest;
	}

	public PriorityBlockingQueue<Integer> getDownRequest() {
		return downRequest;
	}

	public void setUpRequest(PriorityBlockingQueue<Integer> upRequest) {
		this.upRequest = upRequest;
	}

	public void setDownRequest(PriorityBlockingQueue<Integer> downRequest) {
		this.downRequest = downRequest;
	}
	
	public int getMaxFloorCount() {
		return maxFloorCount;
	}

	public void setMaxFloorCount(int maxFloorCount) {
		this.maxFloorCount = maxFloorCount;
	}
	
	public int getLiftNumber() {
		return liftNumber;
	}

	public LiftState getState() {
		return state;
	}
	public void setLiftNumber(int liftNumber) {
		this.liftNumber = liftNumber;
	}

	public void setState(LiftState state) {
		this.state = state;
	}
}
