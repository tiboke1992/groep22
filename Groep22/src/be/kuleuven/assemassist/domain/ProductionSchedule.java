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
			time += pendingCarOrders.get(i).getDeliveryTime().getEstimatedTime();
		}
		return order.getDeliveryTime().getStartTime().plusMinutes(time);
	}

	public boolean canHandleAnotherCarOrder(CarOrder order) {// TODO use
		DateTime lastExpectedOrderTime = calculateExpectedDeliveryTime(pendingCarOrders
				.get(pendingCarOrders.size() - 1));
		int time = order.getDeliveryTime().getEstimatedTime();
		DateTime maxTimeForNewOrder = new DateTime(lastExpectedOrderTime).withHourOfDay(22).withMinuteOfHour(0)
				.withSecondOfMinute(0).minusMinutes(time);
		return lastExpectedOrderTime.isBefore(maxTimeForNewOrder);
	}

	public List<CarOrder> getPendingCarOrders() {
		return pendingCarOrders;
	}

	public List<CarOrder> getCompletedCarOrders() {
		return completedCarOrders;
	}

	public void addCarOrder(CarOrder order) {
		if (order == null)
			throw new IllegalArgumentException("Cannot add null order.");
		pendingCarOrders.add(order);
		workingCarOrders.add(order);
	}

	public void completeOrder(CarOrder order) {
		if (order == null)
			throw new IllegalArgumentException("Cannot complete null order.");
		if (!pendingCarOrders.contains(order))
			throw new IllegalArgumentException("Order is not a pending order.");
		completedCarOrders.add(order);
		pendingCarOrders.remove(order);
	}
}
