package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.domain.task.AssemblyTask;

public abstract class WorkStation {

	// 1
	private CarMechanic carMechanic;
	// 1
	private ConveyorBelt conveyorBelt;
	// 0..*
	private List<Tool> tools;
	// 0..*
	private List<AssemblyTask> assemblyTasks;

//	public WorkStation(CarMechanic carMechanic, ConveyorBelt conveyorBelt) {
//		this.setCarMechanic(carMechanic);
//		this.setConveyorBelt(conveyorBelt);
//		this.tools = new ArrayList<Tool>();
//		this.assemblyTasks = new ArrayList<>(assemblyTasks);
//	}
//
//	public WorkStation(CarMechanic carMechanic, ConveyorBelt conveyorBelt,
//			List<Tool> tools, List<AssemblyTask> assemblyTasks) {
//		this.setCarMechanic(carMechanic);
//		this.setConveyorBelt(conveyorBelt);
//		this.setTools(tools);
//		this.setAssemblyTasks(assemblyTasks);
//	}

	public static List<WorkStation> getWorkStations() {
		List<WorkStation> workStations = new ArrayList<>();
		workStations.add(new DriveTrainPost());
		workStations.add(new AccessoriesPost());
		return workStations;
	}

	public Car getCurrentCar() {
		return null;// TODO
	}

	public CarMechanic getCarMechanic() {
		return carMechanic;
	}

	public void setCarMechanic(CarMechanic carMechanic) {
		this.carMechanic = carMechanic;
	}

	public ConveyorBelt getConveyorBelt() {
		return conveyorBelt;
	}

	public void setConveyorBelt(ConveyorBelt conveyorBelt) {
		this.conveyorBelt = conveyorBelt;
	}

	public List<Tool> getTools() {
		return tools;
	}

	public void setTools(List<Tool> tools) {
		this.tools = tools;
	}

	public List<AssemblyTask> getAssemblyTasks() {
		return assemblyTasks;
	}

	public void setAssemblyTasks(List<AssemblyTask> assemblyTasks) {
		this.assemblyTasks = assemblyTasks;
	}
	
	public abstract String toString();

}
