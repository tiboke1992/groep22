package be.kuleuven.assemassist.controller;

import be.kuleuven.assemassist.domain.AssemblyLine;
import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.ConveyorBelt;
import be.kuleuven.assemassist.domain.Layout;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.role.GarageHolder;
import be.kuleuven.assemassist.domain.role.Role;
import be.kuleuven.assemassist.domain.workpost.AccessoriesPost;
import be.kuleuven.assemassist.domain.workpost.DriveTrainPost;
import be.kuleuven.assemassist.event.Event;
import be.kuleuven.assemassist.event.LoginEvent;
import be.kuleuven.assemassist.event.ShutdownEvent;

/**
 * 
 * This class represents the system and handles the login
 * 
 */
public class SystemController extends Controller {

	public SystemController(CarManufacturingCompany company) {
		super(company);
	}

	public void start() {
		ConveyorBelt conveyorBelt = new ConveyorBelt();
		Layout layout = new Layout(conveyorBelt);
		layout.addWorkStation(new DriveTrainPost(getCompany().getProductionSchedule()));
		layout.addWorkStation(new AccessoriesPost(getCompany().getProductionSchedule()));
		AssemblyLine assemblyLine = new AssemblyLine(layout);
		getCompany().setAssemblyLine(assemblyLine);
	}

	public void loginAs(int roleId) {
		Role role;
		switch (roleId) {
			case 1:
				role = new GarageHolder();
				getUi().showGreeting(role.toString());
				getUi().showOrders();
				getUi().showMenu();
				break;
			case 2:
				role = new CarMechanic();
				getUi().showGreeting(role.toString());
				getUi().showWorkPostMenu();
				break;
			case 3:
				role = getCompany().getManager();
				getUi().showGreeting(role.toString());
				getUi().showManagerMenu();
				break;
			default:
				shutdown();
		}
	}

	public void shutdown() {
		System.out.println("Thanks for using AssemAssist.");
		System.exit(0);
	}

	@Override
	public void handleEvent(Event event) {
		if (event instanceof LoginEvent) {
			loginAs(((LoginEvent) event).getRoleId());
		}else if(event instanceof ShutdownEvent)
			shutdown();
	}

}
