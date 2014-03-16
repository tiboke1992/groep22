package be.kuleuven.assemassist;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.controller.OrderController;
import be.kuleuven.assemassist.controller.SystemController;
import be.kuleuven.assemassist.controller.WorkStationController;
import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.CarModel;
import be.kuleuven.assemassist.domain.CarModelSpecification;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Wheels;
import be.kuleuven.assemassist.domain.role.Manager;
import be.kuleuven.assemassist.ui.UI;

public class AssemAssist {

	public static void main(String[] args) {
		CarManufacturingCompany carManufacturingCompany = new CarManufacturingCompany("Volkswagen Group", new Manager());
		addCarModels(carManufacturingCompany);
		SystemController assemblyController = new SystemController(carManufacturingCompany);
		OrderController orderController = new OrderController(carManufacturingCompany);
		WorkStationController workStationController = new WorkStationController(carManufacturingCompany);
		UI ui = new UI(assemblyController, orderController, workStationController);
		assemblyController.start();
		ui.showLoginOptions();
	}

	private static void addCarModels(CarManufacturingCompany carManufacturingCompany) {
		List<CarOption> allowedOptions = new ArrayList<>();
		allowedOptions.add(Engine.PERFORMANCE);
		allowedOptions.add(Gearbox.MANUAL);
		allowedOptions.add(Gearbox.AUTOMATIC);
		allowedOptions.add(Seats.LEATHER_BLACK);
		allowedOptions.add(Seats.VINYL_GREY);
		allowedOptions.add(Wheels.SPORTS);
		allowedOptions.add(Wheels.COMFORT);
		CarModelSpecification spec = new CarModelSpecification(allowedOptions);
		carManufacturingCompany.addCarModel(new CarModel(carManufacturingCompany, "Audi A6", spec));

	}
}
