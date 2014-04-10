package be.kuleuven.assemassist.event;

import be.kuleuven.assemassist.domain.CarOrder;

public class CarOrderCompletedEvent implements Event {

	private final CarOrder order;

	public CarOrderCompletedEvent(CarOrder order) {
		this.order = order;
	}

	public CarOrder getOrder() {
		return order;
	}
}
