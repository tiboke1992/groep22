package be.kuleuven.assemassist.domain;

import java.util.List;

import be.kuleuven.assemassist.domain.workpost.WorkStation;

public class Layout {

	// 1
	private AssemblyLine assemblyLine;
	// 1..*
	private List<WorkStation> workStations;
	// 1
	private ConveyorBelt conveyorBelt;

	public Layout(AssemblyLine assemblyLine, ConveyorBelt conveyorBelt) {
		this.setAssemblyLine(assemblyLine);
		// this.setWorkStations(WorkStation.getWorkStations()); TODO
		this.setConveyorBelt(conveyorBelt);
	}

	public AssemblyLine getAssemblyLine() {
		return assemblyLine;
	}

	public void setAssemblyLine(AssemblyLine assemblyLine) {
		this.assemblyLine = assemblyLine;
	}

	public List<WorkStation> getWorkStations() {
		return workStations;
	}

	public void setWorkStations(List<WorkStation> workStations) {
		this.workStations = workStations;
	}

	public ConveyorBelt getConveyorBelt() {
		return conveyorBelt;
	}

	public void setConveyorBelt(ConveyorBelt conveyorBelt) {
		this.conveyorBelt = conveyorBelt;
	}

}
