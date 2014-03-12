package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.domain.role.Manager;

public class CarManufacturingCompany {

	private Manager manager;
	private ProductionSchedule productionSchedule;

	public CarManufacturingCompany() {
		this.productionSchedule = new ProductionSchedule();
	}

	public Manager getManager() {
		return manager;
	}

	public ProductionSchedule getProductionSchedule() {
		return productionSchedule;
	}

	public List<CarModel> getAvailableCarModels() {
		return new ArrayList<>();
	}
}
