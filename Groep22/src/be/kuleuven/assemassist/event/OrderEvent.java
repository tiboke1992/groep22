package be.kuleuven.assemassist.event;

import be.kuleuven.assemassist.domain.carmodel.CarModel;

public class OrderEvent implements Event {

	private CarModel model;

	public OrderEvent(CarModel model) {
		this.model = model;
	}

	public CarModel getModel() {
		return model;
	}
}
