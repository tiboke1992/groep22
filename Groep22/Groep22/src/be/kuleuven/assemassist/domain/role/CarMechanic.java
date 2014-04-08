package be.kuleuven.assemassist.domain.role;

import be.kuleuven.assemassist.domain.workpost.WorkStation;

/**
 * 
 * This class represents the car mechanic
 * 
 */

public class CarMechanic implements Role {

	private WorkStation workStation;

	@Override
	public String toString() {
		return "Car Mechanic";
	}

	public void setWorkStation(WorkStation workPost) {
		this.workStation = workPost;
	}

	public WorkStation getWorkStation() {
		return workStation;
	}
}
