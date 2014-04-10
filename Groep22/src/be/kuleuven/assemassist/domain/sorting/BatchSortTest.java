package be.kuleuven.assemassist.domain.sorting;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Spoiler;
import be.kuleuven.assemassist.domain.options.Wheels;

public class BatchSortTest {

	@Test
	public void test() {
		CarOrder order1 = new CarOrder(null, Engine.PERFORMANCE,
				Gearbox.AUTOMATIC_5, Seats.LEATHER_BLACK, Wheels.COMFORT,
				Spoiler.NONE);
		CarOrder order2 = new CarOrder(null, Engine.PERFORMANCE,
				Gearbox.MANUAL_6, Seats.VINYL_GREY, Wheels.COMFORT,
				Spoiler.NONE);
		CarOrder order3 = new CarOrder(null, Engine.PERFORMANCE,
				Gearbox.MANUAL_6, Seats.LEATHER_BLACK, Wheels.COMFORT,
				Spoiler.NONE);
		CarOrder order4 = new CarOrder(null, Engine.PERFORMANCE,
				Gearbox.AUTOMATIC_5, Seats.LEATHER_WHITE, Wheels.COMFORT,
				Spoiler.NONE);
		CarOrder order5 = new CarOrder(null, Engine.PERFORMANCE,
				Gearbox.AUTOMATIC_5, Seats.LEATHER_BLACK, Wheels.SPORTS,
				Spoiler.HIGH);
		CarOrder order6 = new CarOrder(null, Engine.PERFORMANCE,
				Gearbox.MANUAL_6, Seats.LEATHER_BLACK, Wheels.SPORTS,
				Spoiler.HIGH);
		CarOrder order7 = new CarOrder(null, Engine.PERFORMANCE,
				Gearbox.MANUAL_6, Seats.LEATHER_BLACK, Wheels.SPORTS,
				Spoiler.HIGH);
		CarOrder order8 = new CarOrder(null, Engine.PERFORMANCE,
				Gearbox.MANUAL_6, Seats.LEATHER_BLACK, Wheels.SPORTS,
				Spoiler.HIGH);

		List<CarOrder> pending = new ArrayList<CarOrder>();
		pending.add(order1);
		pending.add(order2);
		pending.add(order3);
		pending.add(order4);
		pending.add(order5);
		pending.add(order6);
		pending.add(order7);
		pending.add(order8);
		
		
		//show original order
		System.out.println("Initial list :");
		for (CarOrder ord : pending) {
			String str = "";
			str += ord.getEngine().toString() + " -";
			str += ord.getGearbox().toString() + " -";
			str += ord.getSeats().toString() + " -";
			str += ord.getWheels().toString() + " -";
			str += ord.getSpoiler().toString() + " - \n";
			System.out.println(str);
		}
		
		

		// Permutations teste
		BatchSort sort = new BatchSort(pending);
		// Permutations teste
		System.out.println();
		System.out.println("Show permutation set (more then 3 orders)");
		List<List<CarOption>> lijst = sort.getPermutations();
		for (List<CarOption> lo : lijst) {
			String str = "";
			for (CarOption option : lo) {
				str += option.toString() + " - ";
			}
			System.out.println(str);
		}

		List<CarOption> optionList = new ArrayList<CarOption>();
		optionList = lijst.get(0);
		sort.setOptions(optionList);
		List<CarOrder> result = sort.sortOrders();
		System.out.println("");
		System.out.println("Sorted List :");
		for (CarOrder ord : result) {
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
