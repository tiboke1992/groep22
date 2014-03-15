package be.kuleuven.assemassist.domain.workpost;

import java.util.List;

import be.kuleuven.assemassist.domain.Car;
import be.kuleuven.assemassist.domain.CarAssemblyProcess;
import be.kuleuven.assemassist.domain.ConveyorBelt;
import be.kuleuven.assemassist.domain.Tool;
import be.kuleuven.assemassist.domain.role.CarMechanic;

public abstract class WorkStation {

	private CarMechanic carMechanic;
	private ConveyorBelt conveyorBelt;
	private List<Tool> tools;
	private Car currentCar;

	private CarAssemblyProcess assemblyProcess;

	public WorkStation() {
	}

	public Car getCurrentCar() {
		return currentCar;
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

}
