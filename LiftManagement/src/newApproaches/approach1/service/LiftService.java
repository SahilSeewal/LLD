package newApproaches.approach1.service;

import newApproaches.approach1.model.Lift;


public class LiftService {
	LiftMovementStratergy liftMovementStratergy;
	public LiftService() {
		liftMovementStratergy = new LiftMovementStratergyImpl();
	}
	
	void requestFromExternalPanal(int floor, String direction, Lift lift) {
		liftMovementStratergy.addRequestFromExternalPanal(floor, direction, lift);
	}
	
	void requestFromInternalPanal(int floor, Lift lift) {
		liftMovementStratergy.addRequestFromInternalPanal(floor, lift);
	}
}
