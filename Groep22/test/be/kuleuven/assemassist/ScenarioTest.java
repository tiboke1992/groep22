package be.kuleuven.assemassist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.joda.time.DateTime;
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
import be.kuleuven.assemassist.domain.task.AssemblyTask;
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
		audiA6 = new CarModel(carManufacturingCompany, "Audi A6", new CarModelSpecification(allowedOptions));
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

	@Test
	public void testManagerAdvancesAssemblyLineNoCarOrders() {
		new TestUI(systemController, orderController, workStationController) {
			@Override
			public void showCanNotAdvanceError() {
				pass(pass);
			}
		};
		systemController.start();
		systemController.loginAs(MANAGER);
		workStationController.advanceAssemblyLine();
		if (!pass.get())
			fail("Advanced when not possible");
	}

	@Test
	public void testGarageHolderMakesCarOrder_ManagerAdvancesAssemblyLine_MechanicPerformsOnlySomeTasks_ManagerTriesToAdvanceButFails() {
		new TestUI(systemController, orderController, workStationController) {
			@SuppressWarnings("unchecked")
			@Override
			public <T extends CarOption> T askCarOption(CarModelSpecification spec, Class<T> carOptionClass) {
				if (carOptionClass == Engine.class)
					return (T) Engine.PERFORMANCE;
				else if (carOptionClass == Gearbox.class)
					return (T) Gearbox.MANUAL_5;
				else if (carOptionClass == Wheels.class)
					return (T) Wheels.SPORTS;
				else if (carOptionClass == Seats.class)
					return (T) Seats.VINYL_GREY;
				else {
					fail("Unexpected car option type: " + carOptionClass.getName());
					return null;
				}
			}

			@Override
			public void showDeliveryTime(DateTime time) {
				pass(pass);
			}

			@Override
			public void showAssemblyLineAdvanced() {
				pass(pass2);
			}

			@Override
			public void showTaskCompleted(AssemblyTask task) {
				pass(pass);
			}

			@Override
			public void showCanNotAdvanceError() {
				pass(pass3);
			}
		};
		systemController.start();
		systemController.loginAs(GARAGE_HOLDER);
		assertTrue(carManufacturingCompany.getProductionSchedule().getPendingCarOrders().isEmpty());
		orderController.makeOrder(carManufacturingCompany.getAvailableCarModels().get(0));
		assertFalse(carManufacturingCompany.getProductionSchedule().getPendingCarOrders().isEmpty());
		if (!pass.get())
			fail("Did not show delivery time.");
		pass.set(false);
		systemController.loginAs(MANAGER);
		workStationController.advanceAssemblyLine();
		if (!pass2.get())
			fail("Assembly line not advanced.");
		systemController.loginAs(CAR_MECHANIC);
		workStationController.setCarMechanic(new CarMechanic());
		workStationController.selectWorkStation(1);
		workStationController.selectTask(1);
		while (!pass.get())
			workStationController.completeNextAction();
		pass.set(false);
		systemController.loginAs(MANAGER);
		workStationController.advanceAssemblyLine();
		if (!pass3.get())
			fail("Assembly line advanced while it shouldn't");
	}

	@Test
	public void testCarOrderCreated_Advanced_AllTasksCompleted_CreateCarOrder_Advanced() {
		new TestUI(systemController, orderController, workStationController) {
			@SuppressWarnings("unchecked")
			@Override
			public <T extends CarOption> T askCarOption(CarModelSpecification spec, Class<T> carOptionClass) {
				if (carOptionClass == Engine.class)
					return (T) Engine.PERFORMANCE;
				else if (carOptionClass == Gearbox.class)
					return (T) Gearbox.MANUAL_5;
				else if (carOptionClass == Wheels.class)
					return (T) Wheels.SPORTS;
				else if (carOptionClass == Seats.class)
					return (T) Seats.VINYL_GREY;
				else {
					fail("Unexpected car option type: " + carOptionClass.getName());
					return null;
				}
			}

			@Override
			public void showDeliveryTime(DateTime time) {
				pass(pass);
			}

			@Override
			public void showAssemblyLineAdvanced() {
				pass(pass2);
			}

			@Override
			public void showTaskCompleted(AssemblyTask task) {
				pass(pass);
			}

			@Override
			public void showCanNotAdvanceError() {
				fail();
			}
		};
		systemController.start();
		systemController.loginAs(GARAGE_HOLDER);
		assertTrue(carManufacturingCompany.getProductionSchedule().getPendingCarOrders().isEmpty());
		orderController.makeOrder(carManufacturingCompany.getAvailableCarModels().get(0));
		assertFalse(carManufacturingCompany.getProductionSchedule().getPendingCarOrders().isEmpty());
		if (!pass.get())
			fail("Did not show delivery time.");
		pass.set(false);
		systemController.loginAs(MANAGER);
		workStationController.advanceAssemblyLine();
		if (!pass2.get())
			fail("Assembly line not advanced.");
		pass2.set(false);
		systemController.loginAs(CAR_MECHANIC);
		workStationController.setCarMechanic(new CarMechanic());
		workStationController.selectWorkStation(0);
		while (!workStationController.getWorkStations().get(0).getAssemblyProcess().getPendingTasks().isEmpty()) {
			workStationController.selectTask(1);
			while (!pass.get())
				workStationController.completeNextAction();
			pass.set(false);
		}
		systemController.loginAs(GARAGE_HOLDER);
		orderController.makeOrder(carManufacturingCompany.getAvailableCarModels().get(0));
		if (!pass.get())
			fail("Did not show delivery time.");
		pass.set(false);
		systemController.loginAs(MANAGER);
		workStationController.advanceAssemblyLine();
		if (!pass2.get())
			fail("Assembly line not advanced.");
	}

	private void pass(AtomicBoolean b) {
		b.set(true);
	}
}
