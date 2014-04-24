package be.kuleuven.assemassist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import be.kuleuven.assemassist.controller.OrderController;
import be.kuleuven.assemassist.controller.SystemController;
import be.kuleuven.assemassist.controller.WorkStationController;
import be.kuleuven.assemassist.domain.AssemblyLine;
import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.ProductionSchedule;
import be.kuleuven.assemassist.domain.carmodel.CarModel;
import be.kuleuven.assemassist.domain.carmodel.CarModelSpecification;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Wheels;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.role.Manager;
import be.kuleuven.assemassist.domain.workpost.WorkStation;
import be.kuleuven.assemassist.ui.TestUI;

public class SchedulingScenarioTest {

	private CarManufacturingCompany carManufacturingCompany;
	private CarModel model;
	private SystemController systemController;
	private OrderController orderController;
	private WorkStationController workStationController;

	private static int GARAGE_HOLDER = 1;
	private static int CAR_MECHANIC = 2;
	private static int MANAGER = 3;

	@Before
	public void setup() {
		carManufacturingCompany = new CarManufacturingCompany("Volkswagen Group", new Manager());
		List<CarOption> allowedOptions = new ArrayList<>();
		allowedOptions.add(Engine.PERFORMANCE);
		allowedOptions.add(Gearbox.MANUAL_5);
		allowedOptions.add(Gearbox.AUTOMATIC_5);
		allowedOptions.add(Seats.LEATHER_BLACK);
		allowedOptions.add(Seats.VINYL_GREY);
		allowedOptions.add(Wheels.SPORTS);
		allowedOptions.add(Wheels.COMFORT);
		model = new CarModel(carManufacturingCompany, "Audi A6", 60, new CarModelSpecification(allowedOptions));
		carManufacturingCompany.addCarModel(model);
		systemController = new SystemController(carManufacturingCompany);
		orderController = new OrderController(carManufacturingCompany);
		workStationController = new WorkStationController(carManufacturingCompany);
	}

	@Test
	public void testSchedulingOrderGetsScheduledForNextDayIfNotEnoughTime() {
		new TestUI(systemController, orderController, workStationController);
		systemController.start();
		systemController.loginAs(GARAGE_HOLDER);
		for (int i = 0; i < 8; i++)
			orderController.makeOrder(model);

		AssemblyLine assemblyLine = carManufacturingCompany.getAssemblyLine();
		ProductionSchedule productionSchedule = carManufacturingCompany.getProductionSchedule();
		WorkStation first = assemblyLine.getFirstWorkStation();
		WorkStation last = assemblyLine.getLastWorkStation();
		assertNull(first.getCurrentCarOrder());
		assertNull(last.getCurrentCarOrder());
		if (assemblyLine.canAdvance())
			workStationController.advanceAssemblyLine();
		UUID id = first.getCurrentCarOrder().getId();
		assertNotNull(first.getCurrentCarOrder());
		assertNull(last.getCurrentCarOrder());
		systemController.loginAs(CAR_MECHANIC);
		workStationController.setCarMechanic(new CarMechanic());
		completeTasksAtWorkStation(0);
		assertNotNull(first.getCurrentCarOrder());
		assertNotNull(last.getCurrentCarOrder());
		assertEquals(last.getCurrentCarOrder().getId(), id);
		id = first.getCurrentCarOrder().getId();
		completeTasksAtWorkStation(1);
		assertNotNull(first.getCurrentCarOrder());
		assertNotNull(last.getCurrentCarOrder());
		assertEquals(last.getCurrentCarOrder().getId(), id);
		DateTime time = productionSchedule.calculateExpectedDeliveryTime(productionSchedule.getPendingCarOrders().get(
				productionSchedule.getPendingCarOrders().size() - 1));
		assertTrue(time.isBefore(time.plusDays(1).withFields(ProductionSchedule.START_OF_DAY)));
		orderController.makeOrder(model);
		DateTime newTime = productionSchedule.calculateExpectedDeliveryTime(productionSchedule.getPendingCarOrders()
				.get(productionSchedule.getPendingCarOrders().size() - 1));
		assertFalse(newTime.isBefore(time.plusDays(1).withFields(ProductionSchedule.START_OF_DAY)));
	}

	private void completeTasksAtWorkStation(int workStation) {
		workStationController.selectWorkStation(workStation);
		while (!carManufacturingCompany.getAssemblyLine().getLayout().getWorkStations().get(workStation)
				.getAssemblyProcess().getPendingTasks().isEmpty()) {
			workStationController.selectTask(1);
			workStationController.completeTask(30);
		}
	}

	private void printOrdersAtStationInfo() {
		CarOrder first = carManufacturingCompany.getAssemblyLine().getFirstWorkStation().getCurrentCarOrder();
		CarOrder last = carManufacturingCompany.getAssemblyLine().getLastWorkStation().getCurrentCarOrder();
		if (first != null)
			System.out.println("work1: " + first.getId() + " "
					+ carManufacturingCompany.getProductionSchedule().calculateExpectedDeliveryTime(first).toDate());
		if (last != null)
			System.out.println("work2: " + last.getId() + " "
					+ carManufacturingCompany.getProductionSchedule().calculateExpectedDeliveryTime(last).toDate());

	}
}
