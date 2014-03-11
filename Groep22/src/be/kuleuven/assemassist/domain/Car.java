package be.kuleuven.assemassist.domain;

public class Car {
	
	private CarModel carModel;
	private AssemblyLine assemblyLine;
	
	public Car(CarModel carMode, AssemblyLine assemblyLine){
		this.setCarModel(carModel);
		this.setAssemblyLine(assemblyLine);
	}

	public CarModel getCarModel() {
		return carModel;
	}

	public void setCarModel(CarModel carModel) {
		this.carModel = carModel;
	}

	public AssemblyLine getAssemblyLine() {
		return assemblyLine;
	}

	public void setAssemblyLine(AssemblyLine assemblyLine) {
		this.assemblyLine = assemblyLine;
	}
	
	
	
}
