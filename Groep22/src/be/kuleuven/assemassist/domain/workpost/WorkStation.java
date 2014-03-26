package be.kuleuven.assemassist.domain.workpost;

import java.util.List;

import be.kuleuven.assemassist.domain.CarAssemblyProcess;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.ConveyorBelt;
import be.kuleuven.assemassist.domain.ProductionSchedule;
import be.kuleuven.assemassist.domain.Tool;
import be.kuleuven.assemassist.domain.role.CarMechanic;

/**
 * 
 * This class forms a superclass for all workstations. It has a car mechanic who
 * works at this workstation, a car for the mechanic to work on, a conveyor belt
 * which transports the car between workstations and a list of tools that the
 * car mechanic uses.
 * 
 */

public abstract class WorkStation {

	private CarMechanic carMechanic;
	private ConveyorBelt conveyorBelt;
	private List<Tool> tools;
	private CarOrder currentCarOrder;

	private CarAssemblyProcess assemblyProcess;
	private ProductionSchedule schedule;

	public WorkStation(ProductionSchedule schedule) {
		this.schedule = schedule;
	}

	public CarOrder getCurrentCarOrder() {
		return currentCarOrder;
	}

	public void setCurrentCarOrder(CarOrder currentCarOrder) {
		this.currentCarOrder = currentCarOrder;
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

	public CarAssemblyProcess getAssemblyProcess() {
		return assemblyProcess;
	}

	public void setAssemblyProcess(CarAssemblyProcess assemblyProcess) {
		this.assemblyProcess = assemblyProcess;
	}

	public ProductionSchedule getSchedule() {
		return schedule;
	}
	
	public abstract void init();

}
