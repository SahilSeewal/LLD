package newApproaches.approach2.service.state;

import newApproaches.approach2.model.Lift;
import newApproaches.approach2.service.LiftService;
import newApproaches.approach2.service.LiftState;

public class MovingDownState implements LiftState {
	private LiftService liftService;
	public MovingDownState(LiftService liftService) {
		this.liftService = liftService;
	}
	@Override
	public void addRequestFromExternalPanal(int fromFloor, String direction, Lift lift) {
		// TODO Auto-generated method stub
		this.liftService.addRequestToLiftForOutsideUsers(fromFloor, direction, lift);
	}

	@Override
	public void addRequestFromInternalPanal(int toFloor, Lift lift) {
		// TODO Auto-generated method stub
		this.liftService.addRequestToLiftForInsideUsers(toFloor, lift);
	}

}
