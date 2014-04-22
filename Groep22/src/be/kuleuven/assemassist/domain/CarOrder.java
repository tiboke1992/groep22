package be.kuleuven.assemassist.domain;

import java.util.UUID;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.domain.carmodel.CarModel;
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
	private CarModel carModel;
	private CarAssemblyProcess carAssemblyProcess;

	private Engine engine;
	private Gearbox gearbox;
	private Seats seats;
	private Wheels wheels;
	private Spoiler spoiler;

	public CarOrder(CarModel model) {
		this.id = UUID.randomUUID();
		this.carModel = model;
	}

	public CarOrder(CarModel carModel, Engine engine, Gearbox gearbox, Seats seats, Wheels wheels, Spoiler spoiler) {
		this.id = UUID.randomUUID();
		this.carModel = carModel;
		setEngine(engine);
		setGearbox(gearbox);
		setSeats(seats);
		setWheels(wheels);
		setSpoiler(spoiler);
	}

	public CarOrder(CarOrder order) {
		this.id = order.getId();
		this.carModel = new CarModel(order.carModel.getCarManufacturingCompany(), order.carModel.getName(),
				order.carModel.getEstimatedTimeCost(), new CarModelSpecification(order.carModel.getSpecification()));
		this.deliveryTime = order.getDeliveryTime() == null ? null : new DeliveryTime(order.getDeliveryTime());
		this.engine = order.getEngine();
		this.gearbox = order.getGearbox();
		this.seats = order.getSeats();
		this.wheels = order.getWheels();
		this.spoiler = order.getSpoiler();
		this.carAssemblyProcess = new CarAssemblyProcess(order.carAssemblyProcess);
	}

	public void init(DateTime time, int totalEstimatedTimeCost) {
		deliveryTime = new DeliveryTime(time);
		deliveryTime.setEstimatedTime(totalEstimatedTimeCost);
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

	public CarModel getCarModel() {
		return carModel;
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
