package be.kuleuven.assemassist.controller;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.role.GarageHolder;
import be.kuleuven.assemassist.domain.role.Manager;
import be.kuleuven.assemassist.domain.role.Role;

public class SystemController extends Controller {

	private Role role;

	public SystemController(CarManufacturingCompany company) {
		super(company);
	}

	public void loginAs(int roleId) {
		switch (roleId) {
			case 1:
				role = new GarageHolder();
				getUi().showGreeting(role);
				getUi().showOrders();
				getUi().showMenu();
				break;
			case 2:
				role = new CarMechanic();
				getUi().showGreeting(role);
				getUi().showWorkPostMenu();
				break;
			case 3:
				role = new Manager();
				getUi().showGreeting(role);
				getUi().showManagerMeu();
				getUi().showOverview();
				break;
			default:
				shutdown();
		}
	}

	public void shutdown() {
		System.out.println("Thanks for using AssemAssist.");
		System.exit(0);
	}

}
