package newApproaches.approach1.service;

import java.io.Closeable;
import java.util.concurrent.PriorityBlockingQueue;

import newApproaches.approach1.constant.LiftState;
import newApproaches.approach1.model.Lift;

public class LiftMovementStratergyImpl implements LiftMovementStratergy {

	//State of object should be different from operations we want it to perform
	// Eg: MOVING_UP/DOWN, OPEN, CLOSE -> State //They decide which operation should be invoked at current state 
	// Operations: move(), addRequestToLiftForOutsideUsers() // contains the function that should be performed.
	// The methods which are actually exposed for external users  should only contain switch cases for states
	// Eg:  addRequestFromExternalPanal(), addRequestFromInternalPanal()
	@Override
	public void addRequestFromExternalPanal(int fromFloor, String direction, Lift lift) {
		// TODO Auto-generated method stub
		switch (lift.getState()) {
		case MOVING_UP-> {
			addRequestToLiftForOutsideUsers(fromFloor, direction, lift);
		}
		case MOVING_DOWN-> {
			addRequestToLiftForOutsideUsers(fromFloor, direction, lift);
		}
		case OPEN-> {
			addRequestToLiftForOutsideUsers(fromFloor, direction, lift);
		}
		case CLOSE-> {
			addRequestToLiftForOutsideUsers(fromFloor, direction, lift);
			if(lift.getCurrentFloor() == 0) {
				moveLift(lift);
			}
		}
		}
	}

	@Override
	public void addRequestFromInternalPanal(int toFloor, Lift lift) {
		// TODO Auto-generated method stub
		switch (lift.getState()) {
		case MOVING_UP-> {
			addRequestToLiftForInsideUsers(toFloor, lift);
		}
		case MOVING_DOWN-> {
			addRequestToLiftForInsideUsers(toFloor, lift);
		}
		case OPEN-> {
			addRequestToLiftForInsideUsers(toFloor, lift);
		}
		case CLOSE-> {
			addRequestToLiftForInsideUsers(toFloor, lift);
			if(lift.getCurrentFloor() == 0) {
				moveLift(lift);
			}
		}
		}
	}
	
	void addRequestToLiftForInsideUsers(int toFloor, Lift lift) {
		if(lift.getCurrentFloor() >=  toFloor) {
			lift.getUpRequest().add(toFloor);
		} else {
			lift.getDownRequest().add(toFloor);
		}
	}
	
	void addRequestToLiftForOutsideUsers(int fromFloor, String direction, Lift lift) {
		if("UP".equals(direction)) {
			lift.getUpRequest().add(fromFloor);
		} else {
			lift.getDownRequest().add(fromFloor);
		}
	}
	
	void moveLift(Lift lift) {
		while(!lift.getUpRequest().isEmpty() || !lift.getDownRequest().isEmpty()) {
			// Moving upward
			if(lift.getCurrentFloor() <= lift.getMaxFloorCount()) {
				lift.setState(LiftState.MOVING_UP);
				while(lift.getCurrentFloor() <= lift.getMaxFloorCount()) {
					if(!lift.getUpRequest().isEmpty() 
							&& lift.getUpRequest().peek() == lift.getCurrentFloor()) {
						System.out.println("Reached to floor: " + lift.getUpRequest().poll());
						open(lift);
						close(lift);
						if(lift.getCurrentFloor() == lift.getMaxFloorCount()) {
							lift.setState(LiftState.MOVING_DOWN);
							break;
						} else {
							lift.setState(LiftState.MOVING_UP);
						}
					}
					System.out.println("Moving towards floor: " + lift.getCurrentFloor() + 1);
					lift.setCurrentFloor(lift.getCurrentFloor() + 1);
				}
			}
			
			//Moving Downward
			if(lift.getCurrentFloor() >= 0) {
				lift.setState(LiftState.MOVING_DOWN);
				while(lift.getCurrentFloor() >= 0) {
					if(!lift.getDownRequest().isEmpty() 
							&& lift.getDownRequest().peek() == lift.getCurrentFloor()) {
						System.out.println("Reached to floor: " + lift.getDownRequest().poll());
						open(lift);
						close(lift);
						if(lift.getCurrentFloor() == 0) {
							break;
						} else {
							lift.setState(LiftState.MOVING_DOWN);
						}
					}
					System.out.println("Moving towards floor: " + (lift.getCurrentFloor() - 1));
					lift.setCurrentFloor(lift.getCurrentFloor() - 1);
				}
			}
			
		}
	}
	
	
	void open(Lift lift) {
		System.out.println("Opening lift on floor: " + lift.getCurrentFloor());
		lift.setState(LiftState.OPEN);
	}
	
	void close(Lift lift) {
		System.out.println("Closing lift on floor: " + lift.getCurrentFloor());
		lift.setState(LiftState.CLOSE);
	}

}
