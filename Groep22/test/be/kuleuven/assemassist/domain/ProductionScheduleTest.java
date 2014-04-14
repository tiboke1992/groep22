package be.kuleuven.assemassist.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import be.kuleuven.assemassist.domain.carmodel.CarModel;
import be.kuleuven.assemassist.domain.carmodel.CarModelSpecification;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.role.Manager;

public class ProductionScheduleTest {

	@Test
	public void testAddCarOrder_orderAddedToPendingOrders() {
		ProductionSchedule schedule = new ProductionSchedule();
		assertEquals(0, schedule.getPendingCarOrders().size());
		schedule.addCarOrder(new CarOrder(new CarModel(new CarManufacturingCompany("bla", new Manager()), "bla", 50,
				new CarModelSpecification(new ArrayList<CarOption>()))));
		assertEquals(1, schedule.getPendingCarOrders().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCompleteOrder_onlyPendingOrdersCanBeCompleted_throwsIllegalArgumentException() {
		ProductionSchedule schedule = new ProductionSchedule();
		CarOrder order = new CarOrder(new CarModel(new CarManufacturingCompany("bla", new Manager()), "bla", 50,
				new CarModelSpecification(new ArrayList<CarOption>())));
		schedule.completeOrder(order);
	}

	@Test
	public void testCompleteOrder_orderDeletedFromPendingOrders() {
		ProductionSchedule schedule = new ProductionSchedule();
		CarOrder order = new CarOrder(new CarModel(new CarManufacturingCompany("bla", new Manager()), "bla", 50,
				new CarModelSpecification(new ArrayList<CarOption>())));
		schedule.addCarOrder(order);
		schedule.completeOrder(order);
		assertFalse(schedule.getPendingCarOrders().contains(order));
	}

	@Test
	public void testCompleteOrder_orderAddedToCompletedOrders() {
		ProductionSchedule schedule = new ProductionSchedule();
		CarOrder order = new CarOrder(new CarModel(new CarManufacturingCompany("bla", new Manager()), "bla", 50,
				new CarModelSpecification(new ArrayList<CarOption>())));
		schedule.addCarOrder(order);
		schedule.completeOrder(order);
		assertTrue(schedule.getCompletedCarOrders().contains(order));
	}
}
