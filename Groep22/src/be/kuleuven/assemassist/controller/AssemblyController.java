package be.kuleuven.assemassist.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.CarModel;
import be.kuleuven.assemassist.domain.CarModelSpecification;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Wheels;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.role.GarageHolder;
import be.kuleuven.assemassist.domain.role.Manager;
import be.kuleuven.assemassist.domain.role.Role;
import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.domain.workpost.AccessoriesPost;
import be.kuleuven.assemassist.domain.workpost.DriveTrainPost;
import be.kuleuven.assemassist.domain.workpost.WorkStation;
import be.kuleuven.assemassist.ui.UI;

public class AssemblyController extends Controller {

	public CarManufacturingCompany company;
	private Role role;
	private UI ui;
	private List<AssemblyTask> assemblyTasks;

	public AssemblyController(CarManufacturingCompany company) {
		super(company);
	}


	public void shutdown() {
		System.out.println("Thanks for using AssemAssist.");
		System.exit(0);
	}


	public void setUi(UI ui) {
		this.ui = ui;
	}

}
