package be.kuleuven.assemassist.event;

import be.kuleuven.assemassist.domain.role.CarMechanic;

public class ShowWorkPostsMenuEvent implements Event {

	private final CarMechanic carMechanic;

	public ShowWorkPostsMenuEvent(CarMechanic carMechanic) {
		this.carMechanic = carMechanic;
	}

	public CarMechanic getCarMechanic() {
		return carMechanic;
	}
}
