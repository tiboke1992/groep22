package be.kuleuven.assemassist.controller;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.domain.task.action.Action;
import be.kuleuven.assemassist.domain.workpost.WorkStation;

public class WorkStationController extends Controller {

	private CarMechanic carMechanic;
	private AssemblyTask lastTask;

	public WorkStationController(CarManufacturingCompany company) {
		super(company);
	}

	public List<WorkStation> getWorkStations() {
		return getCompany().getAssemblyLine().getLayout().getWorkStations();
	}

	public void selectWorkStation(int workStation) {
		WorkStation workPost = getWorkStations().get(workStation);
		carMechanic.setWorkStation(workPost);
		if (getCompany().getProductionSchedule().getPendingCarOrders().isEmpty()) {
			getUi().showNoCarToWorkOn();
		} else {
			getUi().showPendingAssemblyTasks(workPost.getAssemblyProcess().getPendingTasks());
		}

	}

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
			result += "\nWorkstation : " + station.toString();
			result += "\n\nPending Tasks : +\n";
			for (AssemblyTask task : station.getAssemblyProcess().getPendingTasks()) {
				result += task.toString() + "\n";
			}
		}
		return result;
	}

}
