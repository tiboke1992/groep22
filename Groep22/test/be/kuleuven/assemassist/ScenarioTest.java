package be.kuleuven.assemassist;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import be.kuleuven.assemassist.controller.OrderController;
import be.kuleuven.assemassist.controller.SystemController;
import be.kuleuven.assemassist.controller.WorkStationController;
import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.carmodel.CarModel;
import be.kuleuven.assemassist.domain.carmodel.CarModelSpecification;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Wheels;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.role.Manager;
import be.kuleuven.assemassist.ui.TestUI;

public class ScenarioTest {

	private CarManufacturingCompany carManufacturingCompany;
	private CarModel audiA6;
	private SystemController systemController;
	private OrderController orderController;
	private WorkStationController workStationController;

	private static int GARAGE_HOLDER = 1;
	private static int CAR_MECHANIC = 2;
	private static int MANAGER = 3;

	private AtomicBoolean pass;
	private AtomicBoolean pass2;
	private AtomicBoolean pass3;

	@Before
	public void setup() {
		pass = new AtomicBoolean(false);
		pass2 = new AtomicBoolean(false);
		pass3 = new AtomicBoolean(false);
		carManufacturingCompany = new CarManufacturingCompany("Volkswagen Group", new Manager());
		List<CarOption> allowedOptions = new ArrayList<>();
		allowedOptions.add(Engine.PERFORMANCE);
		allowedOptions.add(Gearbox.MANUAL_5);
		allowedOptions.add(Gearbox.AUTOMATIC_5);
		allowedOptions.add(Seats.LEATHER_BLACK);
		allowedOptions.add(Seats.VINYL_GREY);
		allowedOptions.add(Wheels.SPORTS);
		allowedOptions.add(Wheels.COMFORT);
		audiA6 = new CarModel(carManufacturingCompany, "Audi A6", 50, new CarModelSpecification(allowedOptions));
		carManufacturingCompany.addCarModel(audiA6);
		systemController = new SystemController(carManufacturingCompany);
		orderController = new OrderController(carManufacturingCompany);
		workStationController = new WorkStationController(carManufacturingCompany);
	}

	@Test
	public void testWorkAsMechanicNoCarOrdersAtWorkPost() {
		new TestUI(systemController, orderController, workStationController) {
			@Override
			public void showNoCarToWorkOn() {
				pass(pass);
			}
		};
		systemController.start();
		systemController.loginAs(CAR_MECHANIC);
		workStationController.setCarMechanic(new CarMechanic());
		workStationController.selectWorkStation(1);
		if (!pass.get())
			fail("Worked on car while no car available");
	}

	private void pass(AtomicBoolean b) {
		b.set(true);
	}
}
