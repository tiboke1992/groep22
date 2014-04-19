package be.kuleuven.assemassist.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import be.kuleuven.assemassist.domain.carmodel.CarModel;
import be.kuleuven.assemassist.domain.carmodel.CarModelSpecification;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Spoiler;
import be.kuleuven.assemassist.domain.options.Wheels;
import be.kuleuven.assemassist.domain.role.Manager;
import be.kuleuven.assemassist.domain.sorting.BatchSort;

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

	@Test
	public void testScenario() {
		ProductionSchedule schedule = new ProductionSchedule();

		// orders maken en toevoegen
		CarOrder order1 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.AUTOMATIC_5, Seats.LEATHER_BLACK,
				Wheels.COMFORT, Spoiler.NONE);
		CarOrder order2 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.MANUAL_6, Seats.VINYL_GREY, Wheels.COMFORT,
				Spoiler.NONE);
		CarOrder order3 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.MANUAL_6, Seats.LEATHER_BLACK, Wheels.COMFORT,
				Spoiler.NONE);
		CarOrder order4 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.AUTOMATIC_5, Seats.LEATHER_WHITE,
				Wheels.COMFORT, Spoiler.NONE);
		CarOrder order5 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.AUTOMATIC_5, Seats.LEATHER_BLACK,
				Wheels.SPORTS, Spoiler.HIGH);
		CarOrder order6 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.MANUAL_6, Seats.LEATHER_BLACK, Wheels.COMFORT,
				Spoiler.HIGH);
		CarOrder order7 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.MANUAL_6, Seats.LEATHER_BLACK, Wheels.WINTER,
				Spoiler.HIGH);
		CarOrder order8 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.MANUAL_6, Seats.LEATHER_BLACK, Wheels.SPORTS,
				Spoiler.HIGH);

		schedule.addCarOrder(order1);
		schedule.addCarOrder(order2);
		schedule.addCarOrder(order3);
		schedule.addCarOrder(order4);
		schedule.addCarOrder(order5);
		schedule.addCarOrder(order6);
		schedule.addCarOrder(order7);
		schedule.addCarOrder(order8);

		List<CarOption> options = new ArrayList<CarOption>();
		options.add(Engine.PERFORMANCE);
		options.add(Gearbox.MANUAL_6);
		options.add(Seats.LEATHER_BLACK);
		options.add(Spoiler.HIGH);

		System.out.println("Current sorting algo " + schedule.getSortingAlgorithm().getClass());

		schedule.changeSortingAlgorithm(new BatchSort(), options);

		for (CarOrder ord : schedule.getPendingCarOrders()) {
			String str = "";
			str += ord.getEngine().toString() + " -";
			str += ord.getGearbox().toString() + " -";
			str += ord.getSeats().toString() + " -";
			str += ord.getWheels().toString() + " -";
			str += ord.getSpoiler().toString() + " - \n";
			System.out.println(str);
		}

		CarOrder order9 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.MANUAL_6, Seats.LEATHER_BLACK, Wheels.COMFORT,
				Spoiler.HIGH);
		schedule.addCarOrder(order9);

		System.out.println();
		System.out.println("--------------------------------------");

		for (CarOrder ord : schedule.getPendingCarOrders()) {
			String str = "";
			str += ord.getEngine().toString() + " -";
			str += ord.getGearbox().toString() + " -";
			str += ord.getSeats().toString() + " -";
			str += ord.getWheels().toString() + " -";
			str += ord.getSpoiler().toString() + " - \n";
			System.out.println(str);
		}

		schedule.completeOrder(schedule.getNextWorkCarOrder());
		schedule.completeOrder(schedule.getNextWorkCarOrder());
		schedule.completeOrder(schedule.getNextWorkCarOrder());
		schedule.completeOrder(schedule.getNextWorkCarOrder());

		// show remaining.. there shouldnt be one left that has the conditions
		// and the algorithm should have be changed back to fifo

		System.out.println();
		System.out.println("----------------------------------");

		CarOrder order10 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.MANUAL_5, Seats.LEATHER_BLACK,
				Wheels.COMFORT, Spoiler.HIGH);
		schedule.addCarOrder(order10);
		System.out.println(schedule.getSortingAlgorithm().getClass());

		for (CarOrder ord : schedule.getPendingCarOrders()) {
			String str = "";
			str += ord.getEngine().toString() + " -";
			str += ord.getGearbox().toString() + " -";
			str += ord.getSeats().toString() + " -";
			str += ord.getWheels().toString() + " -";
			str += ord.getSpoiler().toString() + " - \n";
			System.out.println(str);
		}

	}
}
