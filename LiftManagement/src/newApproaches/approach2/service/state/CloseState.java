package newApproaches.approach2.service.state;

import newApproaches.approach2.service.LiftService;
import newApproaches.approach2.model.Lift;
import newApproaches.approach2.service.LiftState;

public class CloseState implements LiftState{
	private LiftService liftService;
	public CloseState(LiftService liftService) {
		this.liftService = liftService;
	}
	@Override
	public void addRequestFromExternalPanal(int fromFloor, String direction, Lift lift) {
		// TODO Auto-generated method stub
		this.liftService.addRequestToLiftForOutsideUsers(fromFloor, direction, lift);
		if(lift.getCurrentFloor() == 0) {
			this.liftService.moveLift(lift);
		}
	}

	@Override
	public void addRequestFromInternalPanal(int toFloor, Lift lift) {
		// TODO Auto-generated method stub
		this.liftService.addRequestToLiftForInsideUsers(toFloor, lift);
		if(lift.getCurrentFloor() == 0) {
			this.liftService.moveLift(lift);
		}
	}

}
