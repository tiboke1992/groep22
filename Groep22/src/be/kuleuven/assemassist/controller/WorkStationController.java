package be.kuleuven.assemassist.controller;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.domain.workpost.AccessoriesPost;
import be.kuleuven.assemassist.domain.workpost.DriveTrainPost;
import be.kuleuven.assemassist.domain.workpost.WorkStation;

public class WorkStationController extends Controller {

	private CarMechanic carMechanic;
	private AssemblyTask lastTask;

	public WorkStationController(CarManufacturingCompany company) {
		super(company);
	}

	public List<WorkStation> getWorkStations() {
		List<WorkStation> workStations = new ArrayList<>();
		workStations.add(new DriveTrainPost(super.getCompany().getProductionSchedule()));
		workStations.add(new AccessoriesPost(super.getCompany().getProductionSchedule()));
		return workStations;
	}

	public void selectWorkStation(int workStation) {
		WorkStation workPost = getWorkStations().get(workStation);
		carMechanic.setWorkStation(workPost);
		getUi().showPendingAssemblyTasks(
				workPost.getAssemblyProcess().getPendingTasks());
	}

	public void selectTask(int option) {
		List<AssemblyTask> tasks = carMechanic.getWorkStation()
				.getAssemblyProcess().getPendingTasks();
		if (option > 0 && option <= tasks.size()) {
			lastTask = tasks.get(option - 1);
			getUi().showSequence(lastTask.getPendingActions());// TODO copy of
		} else {
			System.out.println("TODO");// TODO
		}

	}

	public void setCarMechanic(CarMechanic carMechanic) {
		this.carMechanic = carMechanic;
	}

	public void completeNextAction() {
		if (lastTask == null)
			throw new IllegalStateException();
		lastTask.completeAction();
		if (lastTask.getPendingActions().size() == 0) {
			carMechanic.getWorkStation().getAssemblyProcess()
					.completeTask(lastTask);
			System.out.println("Task : " + lastTask.toString() + " Completed.");
			if (checkAllTasksCompleted()) {
				System.out
						.println("All assembly tasks for this car are completed.");
				System.out.println("We can now move the conveyor belt");
			}
		}
		getUi().showPendingAssemblyTasks(
				carMechanic.getWorkStation().getAssemblyProcess()
						.getPendingTasks());
	}

	public boolean checkAllTasksCompleted() {
		return carMechanic.getWorkStation().getAssemblyProcess()
				.getPendingTasks().size() == 0;
	}

}
