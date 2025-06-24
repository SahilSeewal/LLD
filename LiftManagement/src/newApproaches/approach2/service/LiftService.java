package newApproaches.approach2.service;


import newApproaches.approach2.model.Lift;
import newApproaches.approach2.service.state.CloseState;
import newApproaches.approach2.service.state.MovingDownState;
import newApproaches.approach2.service.state.MovingUpState;
import newApproaches.approach2.service.state.OpenState;


//State of object should be different from operations we want it to perform
// Eg: MOVING_UP/DOWN, OPEN, CLOSE -> State //They decide which operation should be invoked at current state 
// Operations: move(), addRequestToLiftForOutsideUsers() // contains the function that should be performed.
// The methods which are actually exposed for external users  should only contain switch cases for states
// Eg:  addRequestFromExternalPanal(), addRequestFromInternalPanal()
// Only a single lift can be possible in is case bcz this statement should be present in service liftState = new CloseState(this);
public class LiftService {
	private LiftState liftState;
	public LiftService() {
		liftState = new CloseState(this);
	}
	
	public void requestFromExternalPanal(int floor, String direction, Lift lift) {
		liftState.addRequestFromExternalPanal(floor, direction, lift);
	}
			
	public void addRequestToLiftForInsideUsers(int toFloor, Lift lift) {
		if(lift.getCurrentFloor() >=  toFloor) {
			lift.getUpRequest().add(toFloor);
		} else {
			lift.getDownRequest().add(toFloor);
		}
	}
	
	public void addRequestToLiftForOutsideUsers(int fromFloor, String direction, Lift lift) {
		if("UP".equals(direction)) {
			lift.getUpRequest().add(fromFloor);
		} else {
			lift.getDownRequest().add(fromFloor);
		}
	}
	
	public void changeState(LiftState liftState) {
		this.liftState = liftState;
	}
	
	public void moveLift(Lift lift) {
		while(!lift.getUpRequest().isEmpty() || !lift.getDownRequest().isEmpty()) {
			// Moving upward
			if(lift.getCurrentFloor() <= lift.getMaxFloorCount()) {
				changeState(new MovingUpState(this));
				while(lift.getCurrentFloor() <= lift.getMaxFloorCount()) {
					if(!lift.getUpRequest().isEmpty() 
							&& lift.getUpRequest().peek() == lift.getCurrentFloor()) {
						System.out.println("Reached to floor: " + lift.getUpRequest().poll());
						open(lift);
						close(lift);
						if(lift.getCurrentFloor() == lift.getMaxFloorCount()) {
							changeState(new MovingDownState(this));
							break;
						} else {
							changeState(new MovingUpState(this));
						}
					}
					System.out.println("Moving towards floor: " + lift.getCurrentFloor() + 1);
					lift.setCurrentFloor(lift.getCurrentFloor() + 1);
				}
			}
			
			//Moving Downward
			if(lift.getCurrentFloor() >= 0) {
				changeState(new MovingDownState(this));
				while(lift.getCurrentFloor() >= 0) {
					if(!lift.getDownRequest().isEmpty() 
							&& lift.getDownRequest().peek() == lift.getCurrentFloor()) {
						System.out.println("Reached to floor: " + lift.getDownRequest().poll());
						open(lift);
						close(lift);
						if(lift.getCurrentFloor() == 0) {
							break;
						} else {
							changeState(new MovingDownState(this));
						}
					}
					System.out.println("Moving towards floor: " + (lift.getCurrentFloor() - 1));
					lift.setCurrentFloor(lift.getCurrentFloor() - 1);
				}
			}
			
		}
	}
	
	
	public void open(Lift lift) {
		System.out.println("Opening lift on floor: " + lift.getCurrentFloor());
		changeState(new OpenState(this));
	}
	
	public void close(Lift lift) {
		System.out.println("Closing lift on floor: " + lift.getCurrentFloor());
		changeState(new CloseState(this));
	}

}
