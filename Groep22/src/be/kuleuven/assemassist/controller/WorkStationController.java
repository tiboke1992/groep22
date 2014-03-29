package be.kuleuven.assemassist.controller;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.domain.task.action.Action;
import be.kuleuven.assemassist.domain.workpost.WorkStation;

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
		return getCompany().getAssemblyLine().getLayout().getWorkStations();
	}

	/**
	 * This method will select the chosen workstation and tell the gui the show
	 * the pending assembly tasks
	 * 
	 * @param workStation
	 *            represents the workstations
	 */
	public void selectWorkStation(int workStation) {
		WorkStation workPost = getWorkStations().get(workStation);
		carMechanic.setWorkStation(workPost);
		if (workPost.getCurrentCarOrder() == null) {
			getUi().showNoCarToWorkOn();
		} else {
			getUi().showPendingAssemblyTasks(workPost.getAssemblyProcess().getPendingTasks());
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
		List<AssemblyTask> tasks = carMechanic.getWorkStation().getAssemblyProcess().getPendingTasks();
		if (option > 0 && option <= tasks.size()) {
			lastTask = tasks.get(option - 1);
			List<Action> actions = new ArrayList<>();
			for (Action a : lastTask.getPendingActions())
				actions.add(a);
			getUi().showSequence(actions);
		}
	}

	public void setCarMechanic(CarMechanic carMechanic) {
		this.carMechanic = carMechanic;
	}

	/**
	 * This method will complete a single action it then checks if all actions
	 * are completed for the assembly task, it then tells the gui to show the
	 * remaining actions
	 */
	public void completeNextAction() {
		if (lastTask == null)
			throw new IllegalStateException();
		lastTask.completeAction();
		if (lastTask.getPendingActions().isEmpty()) {
			carMechanic.getWorkStation().getAssemblyProcess().completeTask(lastTask);
			getUi().showTaskCompleted(lastTask);
		}
		getUi().showPendingAssemblyTasks(carMechanic.getWorkStation().getAssemblyProcess().getPendingTasks());
	}

	public String getOverview() {
		String result = "Current State :";
		for (WorkStation station : getWorkStations()) {
			result += "\nWorkstation : " + station + " Current order: " + station.getCurrentCarOrder();
			result += "\n\nPending Tasks : +\n";
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
		if (!getNonePendingAllEmpty() && getCompany().getAssemblyLine().canAdvance()) {
			WorkStation lastStation = getCompany().getAssemblyLine().getLastWorkStation();
			CarOrder last = lastStation.getCurrentCarOrder();
			if (last != null) {
				getCompany().getProductionSchedule().completeOrder(last);
				lastStation.init();
			}

			WorkStation first = getCompany().getAssemblyLine().getLayout().getWorkStations().get(0);
			if (first != null) {
				lastStation.setCurrentCarOrder(first.getCurrentCarOrder());
				if (getCompany().getProductionSchedule().getTime()
						.isBefore(new DateTime().withHourOfDay(20).withMinuteOfHour(0).withSecondOfMinute(0))
						&& !getCompany().getProductionSchedule().getPendingCarOrders().isEmpty()) {
					first.setCurrentCarOrder(getCompany().getProductionSchedule().getNextWorkCarOrder());
				} else {
					first.setCurrentCarOrder(null);
				}
				first.init();
				getUi().showOverview();
				getUi().showAssemblyLineAdvanced();
			}
		} else
			getUi().showCanNotAdvanceError();
	}
}
