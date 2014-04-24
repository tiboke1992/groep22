package be.kuleuven.assemassist.controller;

import static be.kuleuven.assemassist.AssemAssist.getTimeManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;

import org.joda.time.DateTime;
import org.joda.time.Days;

import be.kuleuven.assemassist.domain.AssemblyTask;
import be.kuleuven.assemassist.domain.CarAssemblyProcess;
import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.Delay;
import be.kuleuven.assemassist.domain.ProductionSchedule;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.workpost.WorkStation;
import be.kuleuven.assemassist.event.CarOrderCreatedEvent;
import be.kuleuven.assemassist.event.CheckAssemblyLineStatusEvent;
import be.kuleuven.assemassist.event.Event;
import be.kuleuven.assemassist.event.LoginEvent;
import be.kuleuven.assemassist.event.SelectTaskEvent;
import be.kuleuven.assemassist.event.ShowStatisticsEvent;
import be.kuleuven.assemassist.event.ShowWorkPostsMenuEvent;
import be.kuleuven.assemassist.event.TaskCompletedEvent;
import be.kuleuven.assemassist.event.WorkStationSelectionEvent;

/**
 * 
 * This class controls the workstations
 * 
 */
public class WorkStationController extends Controller {

	private CarMechanic carMechanic;
	private AssemblyTask lastTask;

	public WorkStationController(CarManufacturingCompany company) {
		super(company);
	}

	public List<WorkStation> getWorkStations() {
		return Collections.unmodifiableList(getCompany().getAssemblyLine().getLayout().getWorkStations());
	}

	/**
	 * This method will select the chosen workstation and tell the gui the show
	 * the pending assembly tasks
	 * 
	 * @param workStation
	 *            represents the workstations
	 */
	public void selectWorkStation(int workStation) {
		try {
			WorkStation workPost = getWorkStations().get(workStation);
			carMechanic.setWorkStation(workPost);
			if (workPost.getCurrentCarOrder() == null) {
				getUi().showNoCarToWorkOn();
			} else {
				getUi().showPendingAssemblyTasks(workPost.getAssemblyProcess().getPendingTasks());
			}
		} catch (Exception t) {
			getUi().showError(t);
			getUi().showLoginOptions();
		}
	}

	/**
	 * This method will select a task chosen by the user and show the sequence
	 * of actions needed to complete this task
	 * 
	 * @param option
	 *            represents the task number
	 */
	public void selectTask(int option) {
		try {
			List<AssemblyTask> tasks = carMechanic.getWorkStation().getAssemblyProcess().getPendingTasks();
			lastTask = tasks.get(option - 1);
			getUi().showHandleTask(carMechanic);
		} catch (Exception t) {
			getUi().showError(t);
			getUi().showLoginOptions();
		}
	}

	public void setCarMechanic(CarMechanic carMechanic) {
		this.carMechanic = carMechanic;
	}

	/**
	 * This method will complete a single action it then checks if all actions
	 * are completed for the assembly task, it then tells the gui to show the
	 * remaining actions
	 * 
	 * @param time
	 */
	public void completeTask(int time) {
		WorkStation workStation = carMechanic.getWorkStation();
		CarAssemblyProcess assemblyProcess = workStation.getAssemblyProcess();
		assemblyProcess.completeTask(lastTask, time);
		CarOrder current = workStation.getCurrentCarOrder();
		int estimatedTimeCost = workStation.getEstimatedTimeCost();
		current.getDeliveryTime().setEstimatedTime(
				current.getDeliveryTime().getEstimatedTime()
						- (estimatedTimeCost / assemblyProcess.getTasks().size() - time));
		if (getCompany().getAssemblyLine().canAdvance())
			advanceAssemblyLine();
		getUi().showTaskCompleted(lastTask);
		if (assemblyProcess.getPendingTasks().isEmpty()) {
			if (estimatedTimeCost * assemblyProcess.getTasks().size() < assemblyProcess.getTotalTimeSpentOnTasks())
				getCompany().getProductionSchedule().addDelay(
						new Delay(assemblyProcess.getTotalTimeSpentOnTasks() - estimatedTimeCost
								* assemblyProcess.getTasks().size(), getTimeManager().getTime(), current));
			getUi().showAllTasksCompleted();
		} else
			getUi().showPendingAssemblyTasks(assemblyProcess.getPendingTasks());
	}

	/**
	 * Produces a string representation of the workstations, their associated
	 * order and their tasks.
	 * 
	 * @return the overview
	 */
	private String getOverview() {
		StringBuilder overview = new StringBuilder("Current State :");
		for (WorkStation station : getWorkStations()) {
			if (station.getCurrentCarOrder() == null) {
				overview.append("\nWorkstation : ").append(station).append("\nCurrent order: ")
						.append("There is currently no car at this workstation");
			} else {
				overview.append("\nWorkstation : ").append(station).append("\nCurrent order: ")
						.append(station.getCurrentCarOrder().toString()).append("\n\nCompleted Tasks : \n")
						.append(getCompletedTasksText(station)).append("\nPending Tasks : \n")
						.append(getPendingTasksText(station));
			}
		}
		return overview.toString();
	}

	/**
	 * Produces a string representation of the completed tasks of the given
	 * workstation
	 * 
	 * @param station
	 *            the workstation that we want the list for
	 * @return list of completed tasks in string format
	 */
	private String getCompletedTasksText(WorkStation station) {
		StringBuilder result = new StringBuilder();
		List<AssemblyTask> completedTasks = station.getAssemblyProcess().getCompletedTasks();
		if (completedTasks.isEmpty()) {
			result.append("\tNo completed tasks.");
		} else {
			for (AssemblyTask task : completedTasks) {
				result.append("\t").append(task.toString()).append("\n");
			}
		}
		return result.toString();
	}

	/**
	 * Produces a string representation of the pending tasks of the given
	 * workstation
	 * 
	 * @param station
	 *            the workstation that we want the list for
	 * @return list of pending tasks in string format
	 */
	private String getPendingTasksText(WorkStation station) {
		StringBuilder result = new StringBuilder();
		if (station.getAssemblyProcess().getPendingTasks().isEmpty()) {
			result.append("\tNo pending tasks.");
		} else {
			for (AssemblyTask task : station.getAssemblyProcess().getPendingTasks()) {
				result.append("\t").append(task.toString()).append("\n");
			}
		}
		return result.toString();
	}

	/**
	 * Advances the assembly line
	 */
	public void advanceAssemblyLine() {
		try {
			ProductionSchedule schedule = getCompany().getProductionSchedule();
			WorkStation lastStation = getCompany().getAssemblyLine().getLastWorkStation();
			CarOrder last = lastStation.getCurrentCarOrder();
			if (last != null) {
				schedule.completeOrder(last);
				lastStation.init();
				getUi().showWorkOrderCompleted(last);
			}

			WorkStation first = getCompany().getAssemblyLine().getFirstWorkStation();
			lastStation.setCurrentCarOrder(first.getCurrentCarOrder());
			if ((last == null || (last != null && schedule.isBeforeEndOfDay(schedule
					.calculateExpectedDeliveryTime(last)))) && !schedule.getPendingCarOrders().isEmpty()) {
				first.setCurrentCarOrder(schedule.getNextWorkCarOrder());
			} else {
				first.setCurrentCarOrder(null);
			}
			first.init();
			getUi().showAssemblyLineAdvanced();
		} catch (Exception t) {
			getUi().showError(t);
		}
	}

	@Override
	public void handleEvent(Event event) {
		if (event instanceof LoginEvent) {
			if (((LoginEvent) event).getRoleId() == 2)
				setCarMechanic(new CarMechanic());
		} else if (event instanceof WorkStationSelectionEvent) {
			selectWorkStation(((WorkStationSelectionEvent) event).getWorkStationId());
		} else if (event instanceof SelectTaskEvent) {
			selectTask(((SelectTaskEvent) event).getTaskId());
		} else if (event instanceof TaskCompletedEvent)
			completeTask(((TaskCompletedEvent) event).getTimeToComplete());
		else if (event instanceof ShowWorkPostsMenuEvent) {
			ShowWorkPostsMenuEvent e = (ShowWorkPostsMenuEvent) event;
			setCarMechanic(e.getCarMechanic());
			getUi().showWorkPostMenu(getWorkStations());
		} else if (event instanceof CarOrderCreatedEvent) {
			if (getCompany().getAssemblyLine().canAdvance())
				advanceAssemblyLine();
		} else if (event instanceof CheckAssemblyLineStatusEvent) {
			getUi().showAssemblyLineStatus(carMechanic, getOverview());
		} else if (event instanceof ShowStatisticsEvent) {
			getUi().showStatistics(getAverageProducedCarsPerDay(), getMedianProducedCars(),
					getNumberOfCompletedOnDateTime(getTimeManager().getTime()),
					getNumberOfCompletedOnDateTime(getTimeManager().getTime().minusDays(1)), getAverageDelay(),
					getMedianDelay(), getLastDelay(), getSecondLastDelay());
		}
	}

	public double getAverageDelay() {
		double delay = 0;
		SortedSet<Delay> delays = getCompany().getProductionSchedule().getDelays();
		if (delays.isEmpty())
			return 0;
		for (Delay d : delays)
			delay += d.getMinutesDelay();
		return delay / delays.size();
	}

	public double getMedianDelay() {
		List<Delay> delays = new ArrayList<>();
		for (Delay d : getCompany().getProductionSchedule().getDelays())
			delays.add(d);
		Collections.sort(delays, new Comparator<Delay>() {
			@Override
			public int compare(Delay o1, Delay o2) {
				return o1.getMinutesDelay() - o2.getMinutesDelay();
			}
		});
		if (delays.isEmpty())
			return 0;
		if (delays.size() % 2 == 0)
			return (delays.get(delays.size() / 2).getMinutesDelay() + delays.get(delays.size() / 2 - 1)
					.getMinutesDelay()) / 2;
		return delays.get(delays.size() / 2).getMinutesDelay();
	}

	public Delay getLastDelay() {
		SortedSet<Delay> delays = getCompany().getProductionSchedule().getDelays();
		if (delays.isEmpty())
			return null;
		return delays.last();
	}

	public Delay getSecondLastDelay() {
		SortedSet<Delay> delays = getCompany().getProductionSchedule().getDelays();
		Delay last = getLastDelay();
		if (last == null)
			return null;
		SortedSet<Delay> headSet = delays.headSet(last);
		if (headSet.isEmpty())
			return null;
		return headSet.last();
	}

	public double getAverageProducedCarsPerDay() {
		double result = 0;
		List<CarOrder> completed = getCompany().getProductionSchedule().getCompletedCarOrders();
		if (!completed.isEmpty()) {
			DateTime startingTime = completed.get(0).getDeliveryTime().getCompletionTime();
			DateTime endingOrderTime = completed.get(completed.size() - 1).getDeliveryTime().getCompletionTime();
			double days = Days.daysBetween(startingTime, endingOrderTime).getDays() + 1;
			result = completed.size() / days;
		}
		return result;
	}

	public double getMedianProducedCars() {
		double result = 0;
		List<CarOrder> completed = getCompany().getProductionSchedule().getCompletedCarOrders();
		if (!completed.isEmpty()) {
			List<Integer> list = new ArrayList<Integer>();
			DateTime startingTime = completed.get(0).getDeliveryTime().getCompletionTime();
			DateTime endingOrderTime = completed.get(completed.size() - 1).getDeliveryTime().getCompletionTime();
			endingOrderTime = endingOrderTime.plusDays(1);
			while (startingTime.toLocalDate().isBefore(endingOrderTime.toLocalDate())) {
				int number = getNumberOfCompletedOnDateTime(startingTime);
				list.add(number);
				startingTime = startingTime.plusDays(1);
			}
			Collections.sort(list);
			if (list.size() == 1)
				result = list.get(0);
			else if (list.size() % 2 == 0) {
				double first = list.get((list.size() / 2) - 1);
				double second = list.get(list.size() / 2);
				result = (first + second) / 2;
			} else {
				result = list.get((list.size() / 2));
			}

		}
		return result;
	}

	public int getNumberOfCompletedOnDateTime(DateTime time) {
		if (time == null)
			throw new IllegalArgumentException("The given time cant be null");
		int result = 0;

		for (CarOrder order : getCompany().getProductionSchedule().getCompletedCarOrders()) {
			if (Days.daysBetween(time, order.getDeliveryTime().getCompletionTime()).getDays() == 0)
				result++;
		}

		return result;
	}
}
