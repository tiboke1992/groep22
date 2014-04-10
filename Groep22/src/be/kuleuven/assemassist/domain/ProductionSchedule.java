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

	public ProductionSchedule() {
		pendingCarOrders = new ArrayList<>();
		workingCarOrders = new ArrayList<>();
		completedCarOrders = new ArrayList<>();
	}

	public CarOrder getNextWorkCarOrder() {
		if (workingCarOrders.isEmpty())
			return null;
		return workingCarOrders.remove(0);
	}

	public DateTime calculateExpectedDeliveryTime(CarOrder order) {
		if (completedCarOrders.contains(order))
			return order.getDeliveryTime().getCompletionTime();
		int idx = pendingCarOrders.indexOf(order);
		if (idx == -1)
			throw new IllegalArgumentException("Order " + order + " is not on the production schedule.");
		int time = 0;
		for (int i = 0; i <= idx; i++) {
			time += pendingCarOrders.get(i).getDeliveryTime().getTotalTimeSpentAtWorkposts();
		}
		return order.getDeliveryTime().getStartTime().plusMinutes(time);
	}

	public List<CarOrder> getPendingCarOrders() {
		return pendingCarOrders;
	}

	public List<CarOrder> getCompletedCarOrders() {
		return completedCarOrders;
	}

	public void addCarOrder(CarOrder order) {
		pendingCarOrders.add(order);
		workingCarOrders.add(order);
	}

	public void completeOrder(CarOrder order) {
		completedCarOrders.add(order);
		pendingCarOrders.remove(order);
	}
}
