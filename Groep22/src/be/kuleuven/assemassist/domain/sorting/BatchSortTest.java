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
		CarOrder order1 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.AUTOMATIC_5, Seats.LEATHER_BLACK, Wheels.COMFORT, Spoiler.NONE);
		CarOrder order2 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.MANUAL_6, Seats.VINYL_GREY, Wheels.COMFORT, Spoiler.NONE);
		CarOrder order3 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.MANUAL_6, Seats.LEATHER_BLACK, Wheels.COMFORT, Spoiler.NONE);
		CarOrder order4 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.AUTOMATIC_5, Seats.LEATHER_WHITE, Wheels.COMFORT, Spoiler.NONE);
		CarOrder order5 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.AUTOMATIC_5, Seats.LEATHER_BLACK, Wheels.SPORTS, Spoiler.HIGH);
		CarOrder order6 = new CarOrder(null, Engine.PERFORMANCE, Gearbox.MANUAL_6, Seats.LEATHER_BLACK, Wheels.SPORTS, Spoiler.HIGH);
		
		List<CarOption> optionList = new ArrayList<CarOption>();
		optionList.add(Engine.PERFORMANCE);
		optionList.add(Gearbox.MANUAL_6);
		//optionList.add(Seats.LEATHER_BLACK);
		//optionList.add(Wheels.SPORTS);
		//optionList.add(Spoiler.HIGH);
		
		List<CarOrder> pending = new ArrayList<CarOrder>();
		pending.add(order1);
		pending.add(order2);
		pending.add(order3);
		pending.add(order4);
		pending.add(order5);
		pending.add(order6);
		
		BatchSort sort = new BatchSort(pending, optionList);
		for(CarOrder ord : sort.sortOrders()){
			String str = "";
			str += ord.getEngine().toString() +" -";
			str += ord.getGearbox().toString() + " -";
			str += ord.getSeats().toString() + " -";
			str += ord.getWheels().toString() + " -";
			str += ord.getSpoiler().toString() + " - \n";
			System.out.println(str);
		}
	}

}
