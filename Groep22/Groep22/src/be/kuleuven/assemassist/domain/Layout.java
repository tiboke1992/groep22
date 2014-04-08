package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.domain.workpost.WorkStation;

public class Layout {

	private List<WorkStation> workStations;
	private ConveyorBelt conveyorBelt;

	public Layout(ConveyorBelt conveyorBelt) {
		workStations = new ArrayList<>();
		this.conveyorBelt = conveyorBelt;
	}

	public void addWorkStation(WorkStation workStation) {
		if (workStation == null)
			throw new IllegalArgumentException("Null workstation not allowed");
		workStations.add(workStation);
	}

	public ConveyorBelt getConveyorBelt() {
		return conveyorBelt;
	}

	public List<WorkStation> getWorkStations() {
		return workStations;
	}
}
