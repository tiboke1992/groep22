package be.kuleuven.assemassist.domain;

import java.util.UUID;

import be.kuleuven.assemassist.domain.carmodel.CarModelSpecification;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Spoiler;
import be.kuleuven.assemassist.domain.options.Wheels;

/**
 * 
 * This class represents a car order. a car order has a unique id, a delivery
 * time and a model specification. A car also consist of an engine, a gear box,
 * seats and wheels
 * 
 */
public class CarOrder {

	private UUID id;
	private DeliveryTime deliveryTime;
	private CarModelSpecification modelSpecification;
	private CarAssemblyProcess carAssemblyProcess;

	private Engine engine;
	private Gearbox gearbox;
	private Seats seats;
	private Wheels wheels;
	private Spoiler spoiler;

	public CarOrder(CarModelSpecification modelSpecification) {
		this.id = UUID.randomUUID();
		this.modelSpecification = modelSpecification;
	}

	public CarOrder(CarOrder order) {
		this.id = order.getId();
		this.modelSpecification = new CarModelSpecification(order.getModelSpecification());
		this.deliveryTime = order.getDeliveryTime() == null ? null : new DeliveryTime(order.getDeliveryTime());
		this.engine = order.getEngine();
		this.gearbox = order.getGearbox();
		this.seats = order.getSeats();
		this.wheels = order.getWheels();
		this.spoiler = order.getSpoiler();
		this.carAssemblyProcess = new CarAssemblyProcess(order.carAssemblyProcess);
	}

	public void init() {
		deliveryTime = new DeliveryTime();
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

	public CarModelSpecification getModelSpecification() {
		return modelSpecification;
	}

	public CarAssemblyProcess getCarAssemblyProcess() {
		return carAssemblyProcess;
	}

	public void setCarAssemblyProcess(CarAssemblyProcess carAssemblyProcess) {
		this.carAssemblyProcess = carAssemblyProcess;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CarOrder))
			return false;
		CarOrder co = (CarOrder) obj;
		return co.id.equals(id);
	}

	@Override
	public String toString() {
		return "CarOrder " + getId();
	}

	public Spoiler getSpoiler() {
		return spoiler;
	}

	public void setSpoiler(Spoiler spoiler) {
		this.spoiler = spoiler;
	}
}
