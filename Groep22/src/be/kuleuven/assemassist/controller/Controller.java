package be.kuleuven.assemassist.controller;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.ui.UI;

/**
 * 
 * This class forms a superclass for all the controllers
 *
 */

public abstract class Controller {

	private CarManufacturingCompany company;
	private UI ui;

	public Controller(CarManufacturingCompany company) {
		this.company = company;
	}

	public CarManufacturingCompany getCompany() {
		return company;
	}

	public UI getUi() {
		return ui;
	}

	public void setUi(UI ui) {
		this.ui = ui;
	}
}
