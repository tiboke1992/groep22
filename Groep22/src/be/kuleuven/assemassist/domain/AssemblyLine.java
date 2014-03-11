package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.List;

public class AssemblyLine {
	// 1
	private Layout layout;
	// 0..*
	private List<Car> cars;
	// 0..*
	private List<CarAssemblyProcess> carAssemblyProcesses;

	public AssemblyLine(Layout layout) {
		this.setLayout(layout);
		this.cars = new ArrayList<Car>();
		this.carAssemblyProcesses = new ArrayList<CarAssemblyProcess>();
	}

	public AssemblyLine(Layout layout, List<Car> cars,
			List<CarAssemblyProcess> carAssemblyProcesses) {
		this.setLayout(layout);
		this.setCars(cars);
		this.setCarAssemblyProcesses(carAssemblyProcesses);
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public List<CarAssemblyProcess> getCarAssemblyProcesses() {
		return carAssemblyProcesses;
	}

	public void setCarAssemblyProcesses(
			List<CarAssemblyProcess> carAssemblyProcesses) {
		this.carAssemblyProcesses = carAssemblyProcesses;
	}

}
