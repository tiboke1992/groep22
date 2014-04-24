package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.sorting.BatchSort;
import be.kuleuven.assemassist.domain.sorting.FifoSort;
import be.kuleuven.assemassist.domain.sorting.SortingAlgorithm;

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
	private SortingAlgorithm sortingAlgorithm;
	private List<CarOption> carOptions;
	private int overworkMinutes;
	private SortedSet<Delay> delays;

	public static final LocalTime START_OF_DAY = new LocalTime(6, 0, 0);
	public static final LocalTime END_OF_DAY = new LocalTime(22, 0, 0);

	public ProductionSchedule() {
		pendingCarOrders = new ArrayList<>();
		workingCarOrders = new ArrayList<>();
		completedCarOrders = new ArrayList<>();
		delays = new TreeSet<>(new Comparator<Delay>() {
			@Override
			public int compare(Delay o1, Delay o2) {
				return (int) (o1.getTime().getMillis() - o2.getTime().getMillis());
			}
		});
		setSortingAlgorithm(new FifoSort());
		setCarOptions(new ArrayList<CarOption>());
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
		DateTime expected = order.getDeliveryTime().getStartTime();
		for (int i = 0; i <= idx; i++) {
			int minutes = pendingCarOrders.get(i).getDeliveryTime().getEstimatedTime();
			if (isBeforeEndOfDay(expected.plusMinutes(minutes))) {
				expected = expected.plusMinutes(minutes);
			} else
				expected = expected.withFields(START_OF_DAY).plusDays(1).plusMinutes(minutes);
		}
		return expected;
	}

	public boolean isBeforeEndOfDay(DateTime time) {
		return time.isBefore(time.withFields(END_OF_DAY).minusMinutes(overworkMinutes));
	}

	public List<CarOrder> getPendingCarOrders() {
		return pendingCarOrders;
	}

	public List<CarOrder> getCompletedCarOrders() {
		return completedCarOrders;
	}

	public List<CarOrder> getWorkingCarOrders() {
		return this.workingCarOrders;
	}

	public void addCarOrder(CarOrder order) {
		if (order == null)
			throw new IllegalArgumentException("Cannot add null order.");
		pendingCarOrders.add(order);
		workingCarOrders.add(order);
		this.sortCheck();
	}

	public void completeOrder(CarOrder order) {
		if (order == null)
			throw new IllegalArgumentException("Cannot complete null order.");
		if (!pendingCarOrders.contains(order))
			throw new IllegalArgumentException("Order is not a pending order.");
		completedCarOrders.add(order);
		pendingCarOrders.remove(order);
	}

	public void changeSortingAlgorithm(SortingAlgorithm algorithm, List<CarOption> carOptions) {
		if (algorithm == null)
			throw new IllegalArgumentException("The algorithm can't be null");
		if (algorithm instanceof BatchSort && carOptions == null)
			throw new IllegalArgumentException("The batchSort algorithm cant have a null list of carOptions");
		this.setSortingAlgorithm(algorithm);
		this.setCarOptions(carOptions);
		this.sortCheck();
	}

	private void sortCheck() {
		if (this.getSortingAlgorithm() != null && this.getCarOptions() != null) {
			if (this.getSortingAlgorithm() instanceof BatchSort && !this.getCarOptions().isEmpty()) {
				BatchSort batchSort = (BatchSort) this.getSortingAlgorithm();
				batchSort.setOptions(this.getCarOptions());
				batchSort.setPending(this.workingCarOrders);
				if (batchSort.countSuitableCarOrders(this.getCarOptions()) == 0) {
					this.setSortingAlgorithm(new FifoSort());
					this.setCarOptions(new ArrayList<CarOption>());
				} else {
					this.workingCarOrders = batchSort.sortOrders();
					batchSort.setPending(pendingCarOrders);
					this.pendingCarOrders = batchSort.sortOrders();
				}
			}
		}
	}

	public SortingAlgorithm getSortingAlgorithm() {
		return sortingAlgorithm;
	}

	public void setSortingAlgorithm(SortingAlgorithm sortingAlgorithm) {
		this.sortingAlgorithm = sortingAlgorithm;
	}

	public List<CarOption> getCarOptions() {
		return carOptions;
	}

	public void setCarOptions(List<CarOption> carOptions) {
		this.carOptions = carOptions;
	}

	public int getOverworkMinutes() {
		return overworkMinutes;
	}

	public void addDelay(Delay d) {
		delays.add(d);
	}

	public SortedSet<Delay> getDelays() {
		return delays;
	}

	public void setOverworkMinutes(int overworkMinutes) {
		this.overworkMinutes = overworkMinutes;
	}
}
