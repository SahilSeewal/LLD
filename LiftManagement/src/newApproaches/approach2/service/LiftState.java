package newApproaches.approach2.service;

import newApproaches.approach2.model.Lift;

public interface LiftState {
	void addRequestFromExternalPanal(int fromFloor, String direction, Lift lift);
	void addRequestFromInternalPanal(int toFloor, Lift lift);
}
