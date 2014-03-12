package be.kuleuven.assemassist;

import be.kuleuven.assemassist.controller.AssemblyController;
import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.ui.UI;

public class AssemAssist {

	public static void main(String[] args) {
		AssemblyController controller = new AssemblyController(new CarManufacturingCompany());
		UI ui = new UI(controller);
		ui.login();
	}
}
