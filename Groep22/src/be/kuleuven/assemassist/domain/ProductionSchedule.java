package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

/**
 * 
 * This class represents the production schedule, it has a list of pending and
 * completed car orders
 * 
 */
public class ProductionSchedule {

	private List<CarOrder> pendingCarOrders;
	private List<CarOrder> workingCarOrders;
	private List<CarOrder> completedCarOrders;
	private DateTime time;

	public ProductionSchedule() {
		pendingCarOrders = new ArrayList<>();
		workingCarOrders = new ArrayList<>();
		completedCarOrders = new ArrayList<>();
		time = new DateTime().withHourOfDay(6).withMinuteOfHour(0).withSecondOfMinute(0);
	}

	public CarOrder getNextCarOrder() {
		return pendingCarOrders.get(0);
	}

	public CarOrder getNextWorkCarOrder() {
		return workingCarOrders.remove(0);
	}

	/**
	 * 
	 * @return
	 */
	public List<CarOrder> getPendingCarOrders() {
		return pendingCarOrders;
	}

	/**
	 * 
	 * @return
	 */
	public List<CarOrder> getCompletedCarOrders() {
		return completedCarOrders;
	}

	public void addCarOrder(CarOrder order) {
		order.init();
		pendingCarOrders.add(order);
		workingCarOrders.add(order);
	}

	public DateTime getTime() {
		return time;
	}

	public void completeOrder(CarOrder order) {
		completedCarOrders.add(order);
		pendingCarOrders.remove(order);
	}
}
