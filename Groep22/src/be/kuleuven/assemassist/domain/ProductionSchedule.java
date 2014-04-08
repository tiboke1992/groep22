package be.kuleuven.assemassist.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.domain.task.action.Action;

/**
 * 
 * This class represents the production schedule, it has a list of pending and
 * completed car orders
 * 
 */
public class ProductionSchedule {

	private Queue<CarOrder> pendingCarOrders;
	private Queue<CarOrder> workingCarOrders;
	private List<CarOrder> completedCarOrders;
	private DateTime time;

	public ProductionSchedule() {
		pendingCarOrders = new ArrayDeque<>();
		workingCarOrders = new ArrayDeque<>();
		completedCarOrders = new ArrayList<>();
		time = new DateTime().withHourOfDay(6).withMinuteOfHour(0)
				.withSecondOfMinute(0);
	}

	public CarOrder getNextCarOrder() {
		return pendingCarOrders.peek();
	}
	
	public CarOrder getNextWorkCarOrder(){
		return workingCarOrders.poll();
	}
	
	/**
	 * 
	 * @return
	 */
	public Queue<CarOrder> getPendingCarOrders() {
		Queue<CarOrder> copy = new LinkedList<>();
		for(CarOrder order : pendingCarOrders)
			copy.add(order);
		return copy;
	}

	/**
	 * 
	 * @return
	 */
	public List<CarOrder> getCompletedCarOrders() {
		List<CarOrder> copy = new LinkedList<>();
		for(CarOrder order: completedCarOrders)
			copy.add(order);
		return copy;
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
