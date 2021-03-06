package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.kuleuven.assemassist.domain.carmodel.CarModel;
import be.kuleuven.assemassist.domain.role.Manager;

/**
 * 
 * This class represents the car manufacturing company. A company has a name, a
 * manager, a production schedule and a list of car models
 * 
 */

public class CarManufacturingCompany {

	private String name;
	private Manager manager;
	private ProductionSchedule productionSchedule;
	private List<CarModel> carModels;
	private AssemblyLine assemblyLine;

	public CarManufacturingCompany(String name, Manager manager) {
		this.name = name;
		this.manager = manager;
		this.productionSchedule = new ProductionSchedule();
		this.carModels = new ArrayList<>();
	}

	public Manager getManager() {
		return manager;
	}

	public ProductionSchedule getProductionSchedule() {
		return productionSchedule;
	}

	public String getName() {
		return name;
	}

	public List<CarModel> getAvailableCarModels() {
		return Collections.unmodifiableList(this.getCarmodels());
	}

	private List<CarModel> getCarmodels() {
		return this.carModels;
	}

	public void addCarModel(CarModel model) {
		if (model == null)
			throw new IllegalArgumentException("Cannot add null CarModel");
		if (!model.getCarManufacturingCompany().equals(this))
			throw new IllegalArgumentException("Cannot add model from another manufacturer ["
					+ model.getCarManufacturingCompany() + "] to this manufacturer [" + this + "].");
		carModels.add(model);
	}

	@Override
	public String toString() {
		return name;
	}

	public void setAssemblyLine(AssemblyLine assemblyLine) {
		this.assemblyLine = assemblyLine;
	}

	public AssemblyLine getAssemblyLine() {
		return assemblyLine;
	}

}
