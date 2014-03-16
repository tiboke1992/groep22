package be.kuleuven.assemassist.controller;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.domain.workpost.AccessoriesPost;
import be.kuleuven.assemassist.domain.workpost.DriveTrainPost;
import be.kuleuven.assemassist.domain.workpost.WorkStation;


/**
 * 
 * This class controls the workstations
 *
 */
public class WorkStationController extends Controller {

	private CarMechanic carMechanic;
	private AssemblyTask lastTask;
	private List<WorkStation> workStations;

	public WorkStationController(CarManufacturingCompany company) {
		super(company);
		init();
	}
	
	/**
	 * Creation of the workstations
	 */
	public void init(){
		workStations = new ArrayList<WorkStation>();
		workStations.add(new DriveTrainPost(super.getCompany().getProductionSchedule()));
		workStations.add(new AccessoriesPost(super.getCompany().getProductionSchedule()));
	}

	public List<WorkStation> getWorkStations() {
		return this.workStations;
	}

	/**
	 * This method will select the chosen workstation and tell the gui the show the pending
	 * assembly tasks
	 * @param workStation represents the workstations
	 */
	public void selectWorkStation(int workStation) {
		WorkStation workPost = getWorkStations().get(workStation);
		carMechanic.setWorkStation(workPost);
		if(super.getCompany().getProductionSchedule().getPendingCarOrders().size() == 0){
			getUi().showNoCarToWorkOn();
		}else{
			getUi().showPendingAssemblyTasks(
					workPost.getAssemblyProcess().getPendingTasks());
		}
		
	}

	/**
	 * This method will select a task chosen by the user and show the sequence of actions
	 * needed to complete this task
	 * @param option represents the task number
	 */
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

	/**
	 * This method will complete a single action
	 * it then checks if all actions are completed for the assembly task,
	 * it then tells the gui to show the remaining actions
	 */
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
	
	public String getOverview(){
		String result = "Current State :";
		for(WorkStation station : getWorkStations()){
			result += "\nWorkstation : " + station.toString();
			result += "\n\nPending Tasks : +\n";
			for(AssemblyTask task : station.getAssemblyProcess().getPendingTasks()){
				result += task.toString() + "\n";
			}
		}
		return result;
	}

}
