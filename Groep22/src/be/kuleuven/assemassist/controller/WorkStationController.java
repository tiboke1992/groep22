package be.kuleuven.assemassist.controller;

import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.AssemAssist;
import be.kuleuven.assemassist.domain.AssemblyTask;
import be.kuleuven.assemassist.domain.CarAssemblyProcess;
import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.workpost.WorkStation;
import be.kuleuven.assemassist.event.AssemblyAdvanceEvent;
import be.kuleuven.assemassist.event.CompleteTaskEvent;
import be.kuleuven.assemassist.event.Event;
import be.kuleuven.assemassist.event.LoginEvent;
import be.kuleuven.assemassist.event.SelectTaskEvent;
import be.kuleuven.assemassist.event.ShowWorkPostsMenuEvent;
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
		workStation
				.getCurrentCarOrder()
				.getDeliveryTime()
				.setEstimatedTime(
						workStation.getCurrentCarOrder().getDeliveryTime().getEstimatedTime()
								- (workStation.getEstimatedTaskTimeCost() - time));
		getUi().showTaskCompleted(lastTask);
		if (assemblyProcess.getPendingTasks().isEmpty()) {
			getUi().showAllTasksCompleted();
		} else
			getUi().showPendingAssemblyTasks(assemblyProcess.getPendingTasks());
	}

	private String getOverview() {
		String result = "Current State :";
		for (WorkStation station : getWorkStations()) {
			String current = (station.getCurrentCarOrder() == null) ? "There is currently no car at this workstation"
					: station.getCurrentCarOrder().toString();
			result += "\nWorkstation : " + station + "\nCurrent order: " + current;
			result += "\n\nPending Tasks : \n";
			result += getPendingTasksText(station);

		}
		return result;
	}

	private String getPendingTasksText(WorkStation station) {
		String result = "";
		if (station.getAssemblyProcess().getPendingTasks().isEmpty()) {
			result += "No pending tasks.";
		} else {
			for (AssemblyTask task : station.getAssemblyProcess().getPendingTasks()) {
				result += task.toString() + "\n";
			}
		}
		return result;
	}

	public boolean getNonePendingAllEmpty() {
		return noPendingCarOrders() && allWorkstationsEmpty();
	}

	private boolean noPendingCarOrders() {
		return this.getCompany().getProductionSchedule().getPendingCarOrders().isEmpty();
	}

	private boolean allWorkstationsEmpty() {
		return !getCompany().getAssemblyLine().isCarLeftAtAWorkStation();
	}

	public void advanceAssemblyLine() {
		try {
			if (!getNonePendingAllEmpty() && getCompany().getAssemblyLine().canAdvance()) {
				WorkStation lastStation = getCompany().getAssemblyLine().getLastWorkStation();
				CarOrder last = lastStation.getCurrentCarOrder();
				if (last != null) {
					getCompany().getProductionSchedule().completeOrder(last);
					lastStation.init();
					getUi().showWorkOrderCompleted(last);
				}

				WorkStation first = getCompany().getAssemblyLine().getLayout().getWorkStations().get(0);
				if (first != null) {
					lastStation.setCurrentCarOrder(first.getCurrentCarOrder());
					if (AssemAssist.getTimeManager().getTime()
							.isBefore(new DateTime().withHourOfDay(20).withMinuteOfHour(0).withSecondOfMinute(0))
							&& !getCompany().getProductionSchedule().getPendingCarOrders().isEmpty()) {
						first.setCurrentCarOrder(getCompany().getProductionSchedule().getNextWorkCarOrder());
					} else {
						first.setCurrentCarOrder(null);
					}
					first.init();
					getUi().showOverview(getOverview());
					getUi().showAssemblyLineAdvanced();
				}
			} else
				getUi().showCanNotAdvanceError();
			getUi().showManagerMenu();
		} catch (Exception t) {
			getUi().showError(t);
			getUi().showLoginOptions();
		}
	}

	@Override
	public void handleEvent(Event event) {
		if (event instanceof AssemblyAdvanceEvent)
			advanceAssemblyLine();
		else if (event instanceof LoginEvent) {
			if (((LoginEvent) event).getRoleId() == 2)
				setCarMechanic(new CarMechanic());
		} else if (event instanceof WorkStationSelectionEvent) {
			selectWorkStation(((WorkStationSelectionEvent) event).getWorkStationId());
		} else if (event instanceof SelectTaskEvent) {
			selectTask(((SelectTaskEvent) event).getTaskId());
		} else if (event instanceof CompleteTaskEvent)
			completeTask(((CompleteTaskEvent) event).getTimeToComplete());
		else if (event instanceof ShowWorkPostsMenuEvent) {
			ShowWorkPostsMenuEvent e = (ShowWorkPostsMenuEvent) event;
			setCarMechanic(e.getCarMechanic());
			getUi().showWorkPostMenu(getWorkStations());
		}
	}
}
