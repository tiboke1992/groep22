package be.kuleuven.assemassist.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Test;

import be.kuleuven.assemassist.domain.options.CarOption;

public class ProductionScheduleTest {

	@Test
	public void testAddCarOrder_orderAddedToPendingOrders() {
		ProductionSchedule schedule = new ProductionSchedule();
		assertEquals(0, schedule.getPendingCarOrders().size());
		schedule.addCarOrder(new CarOrder(new CarModelSpecification(new ArrayList<CarOption>())));
		assertEquals(1, schedule.getPendingCarOrders().size());
	}

	@Test
	public void testAddCarOrder_initsCarOrder() {
		ProductionSchedule schedule = new ProductionSchedule();
		CarOrder order = new CarOrder(new CarModelSpecification(new ArrayList<CarOption>()));
		assertNull(order.getDeliveryTime());
		schedule.addCarOrder(order);
		assertNotNull(order.getDeliveryTime());
	}
}
