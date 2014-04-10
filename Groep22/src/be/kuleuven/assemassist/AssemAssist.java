package be.kuleuven.assemassist;

import java.io.IOException;

import be.kuleuven.assemassist.controller.OrderController;
import be.kuleuven.assemassist.controller.SystemController;
import be.kuleuven.assemassist.controller.WorkStationController;
import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.carmodel.CarModel;
import be.kuleuven.assemassist.domain.carmodel.CarModelReader;
import be.kuleuven.assemassist.domain.carmodel.TextCarModelReader;
import be.kuleuven.assemassist.domain.role.Manager;
import be.kuleuven.assemassist.ui.UI;

/**
 * 
 * This is our main class in which the objects are initialized
 * 
 */
public class AssemAssist {

	private static final TimeManager timeManager = new TimeManager();

	public static void main(String[] args) {
		CarManufacturingCompany carManufacturingCompany = new CarManufacturingCompany("Volkswagen Group", new Manager());
		addCarModels(carManufacturingCompany);
		SystemController assemblyController = new SystemController(carManufacturingCompany);
		OrderController orderController = new OrderController(carManufacturingCompany);
		WorkStationController workStationController = new WorkStationController(carManufacturingCompany);
		UI ui = new UI();
		ui.addController(workStationController);
		ui.addController(assemblyController);
		ui.addController(orderController);
		assemblyController.start();
		ui.showLoginOptions();
	}

	private static void addCarModels(CarManufacturingCompany carManufacturingCompany) {
		try {
			CarModelReader reader = new TextCarModelReader(carManufacturingCompany, AssemAssist.class.getClassLoader()
					.getResourceAsStream("models.txt"));
			for (CarModel m : reader.readCarModels())
				carManufacturingCompany.addCarModel(m);
		} catch (IOException e) {
			throw new RuntimeException("Could not load car models: " + e.getMessage());
		}

	}

	public static TimeManager getTimeManager() {
		return timeManager;
	}
}
