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

	public WorkStationController(CarManufacturingCompany company) {
		super(company);
	}

	public List<WorkStation> getWorkStations() {
		List<WorkStation> workStations = new ArrayList<>();
		workStations.add(new DriveTrainPost());
		workStations.add(new AccessoriesPost());
		return workStations;
	}

	public void selectWorkStation(int workStation) {
		WorkStation workPost = getWorkStations().get(workStation);
		carMechanic.setWorkStation(workPost);
		getUi().showPendingAssemblyTasks(workPost.getAssemblyProcess().getPendingTasks());
	}

	public void selectTask(int option) {
		List<AssemblyTask> tasks = carMechanic.getWorkStation().getAssemblyProcess().getPendingTasks();
		if (option > 0 && option <= tasks.size()) {
			AssemblyTask task = tasks.get(option - 1);
			getUi().showSequence(task);
		} else {
			System.out.println("TODO");// TODO
		}

	}

	public void setCarMechanic(CarMechanic carMechanic) {
		this.carMechanic = carMechanic;
	}
}
