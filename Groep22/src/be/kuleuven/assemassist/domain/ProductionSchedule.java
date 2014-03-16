package be.kuleuven.assemassist.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.joda.time.DateTime;

/**
 * 
 * This class represents the production schedule, it has a list of pending and
 * completed car orders
 * 
 */
public class ProductionSchedule {

	private Queue<CarOrder> pendingCarOrders;
	private List<CarOrder> completedCarOrders;
	private DateTime time;

	public ProductionSchedule() {
		pendingCarOrders = new ArrayDeque<>();
		completedCarOrders = new ArrayList<>();
		time = new DateTime().withHourOfDay(6).withMinuteOfHour(0).withSecondOfMinute(0);
	}

	public CarOrder getNextCarOrder() {
		return pendingCarOrders.peek();
	}

	public Queue<CarOrder> getPendingCarOrders() {
		return pendingCarOrders;
	}

	public List<CarOrder> getCompletedCarOrders() {
		return completedCarOrders;
	}

	public void addCarOrder(CarOrder order) {
		order.init();
		pendingCarOrders.add(order);
	}

	public DateTime getTime() {
		return time;
	}
}
