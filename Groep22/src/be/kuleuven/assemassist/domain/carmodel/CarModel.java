package be.kuleuven.assemassist.domain.carmodel;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;

/**
 * 
 * This class represents the car model. A car model has a name, a manufacturing
 * company and a car model specification
 * 
 */

public final class CarModel {

	private final int estimatedTimeCost;
	private final String name;
	private final CarModelSpecification specification;
	private final CarManufacturingCompany carManufacturingCompany;

	public CarModel(CarManufacturingCompany company, String name, int taskTimeCost, CarModelSpecification specification) {
		this.carManufacturingCompany = company;
		this.name = name;
		this.specification = specification;
		this.estimatedTimeCost = taskTimeCost;
	}

	public CarModelSpecification getSpecification() {
		return specification;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public CarManufacturingCompany getCarManufacturingCompany() {
		return carManufacturingCompany;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarModel other = (CarModel) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public int getEstimatedTimeCost() {
		return estimatedTimeCost;
	}

}
