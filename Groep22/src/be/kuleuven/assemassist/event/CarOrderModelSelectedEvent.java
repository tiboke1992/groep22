package be.kuleuven.assemassist.event;

import be.kuleuven.assemassist.domain.carmodel.CarModel;

public class CarOrderModelSelectedEvent implements Event {

	private CarModel model;

	public CarOrderModelSelectedEvent(CarModel model) {
		this.model = model;
	}

	public CarModel getModel() {
		return model;
	}
}
