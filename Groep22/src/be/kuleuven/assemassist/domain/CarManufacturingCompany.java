package be.kuleuven.assemassist.domain;

import java.util.List;

import be.kuleuven.assemassist.domain.role.Manager;

public class CarManufacturingCompany {

	private Manager manager;
	private ProductionSchedule productionSchedule;

	public Manager getManager() {
		return manager;
	}

	public ProductionSchedule getProductionSchedule() {
		return productionSchedule;
	}

	public List<CarModel> getAvailableCarModels() {
		// TODO Auto-generated method stub
		return null;
	}
}
