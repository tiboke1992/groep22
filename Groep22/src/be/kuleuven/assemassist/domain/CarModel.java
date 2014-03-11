package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.List;

public class CarModel {
	private List<Car> cars;
	private CarModelSpecification specification; 
	
	public CarModel(CarModelSpecification specification){
		this.setSpecification(specification);
		this.cars = new ArrayList<Car>();
	}
	
	public CarModel(CarModelSpecification specification, List<Car> cars){
		this.setSpecification(specification);
		this.setCars(cars);
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public CarModelSpecification getSpecification() {
		return specification;
	}

	public void setSpecification(CarModelSpecification specification) {
		this.specification = specification;
	}
	
	
}
