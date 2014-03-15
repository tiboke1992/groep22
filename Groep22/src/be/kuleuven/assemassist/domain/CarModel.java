package be.kuleuven.assemassist.domain;

public final class CarModel {

	private final String name;
	private final CarModelSpecification specification;
	private final CarManufacturingCompany carManufacturingCompany;

	public CarModel(CarManufacturingCompany company, String name, CarModelSpecification specification) {
		this.carManufacturingCompany = company;
		this.name = name;
		this.specification = specification;
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
}
