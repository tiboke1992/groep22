package be.kuleuven.assemassist.domain;

import java.util.UUID;

import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Wheels;

public class CarOrder {

	private UUID id;
	private DeliveryTime deliveryTime;

	private Engine engine;
	private Gearbox gearbox;
	private Seats seats;
	private Wheels wheels;

	public CarOrder() {
		this.id = UUID.randomUUID();
	}

	public UUID getId() {
		return id;
	}

	public DeliveryTime getDeliveryTime() {
		return deliveryTime;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public Gearbox getGearbox() {
		return gearbox;
	}

	public void setGearbox(Gearbox gearbox) {
		this.gearbox = gearbox;
	}

	public Seats getSeats() {
		return seats;
	}

	public void setSeats(Seats seats) {
		this.seats = seats;
	}

	public Wheels getWheels() {
		return wheels;
	}

	public void setWheels(Wheels wheels) {
		this.wheels = wheels;
	}

	public void init() {
		deliveryTime = new DeliveryTime();
	}

}
