package newApproaches.approach1.service;

import newApproaches.approach1.model.Lift;

public interface LiftMovementStratergy {
	void addRequestFromExternalPanal(int fromFloor, String direction, Lift lift);
	void addRequestFromInternalPanal(int toFloor, Lift lift);
}
