package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.domain.role.CarMechanic;
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

	public WorkStation() {
		assemblyTasks = new ArrayList<>();
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

	public abstract ArrayList<AssemblyTask> getAssemblyTasks();

	public void setAssemblyTasks(List<AssemblyTask> assemblyTasks) {
		this.assemblyTasks = assemblyTasks;
	}

}
