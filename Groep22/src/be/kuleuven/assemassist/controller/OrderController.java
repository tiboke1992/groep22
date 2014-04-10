package be.kuleuven.assemassist.controller;

import java.util.Collections;
import java.util.List;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.carmodel.CarModel;
import be.kuleuven.assemassist.domain.carmodel.CarModelSpecification;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Spoiler;
import be.kuleuven.assemassist.domain.options.Wheels;
import be.kuleuven.assemassist.event.Event;
import be.kuleuven.assemassist.event.OrderEvent;
import be.kuleuven.assemassist.event.ShowCarModelsEvent;
import be.kuleuven.assemassist.event.ShowOrderDetailsEvent;
import be.kuleuven.assemassist.event.ShowOrdersEvent;

/**
 * 
 * This class controls the car orders
 * 
 */
public class OrderController extends Controller {

	public OrderController(CarManufacturingCompany company) {
		super(company);
	}

	/**
	 * This method creates a copy of the current pending car orders and returns
	 * it
	 * 
	 * @return A collection of pending car orders
	 */
	public List<CarOrder> getPendingCarOrders() {
		return Collections.unmodifiableList(getCompany().getProductionSchedule().getPendingCarOrders());
	}

	/**
	 * This method creates a copy of the current completed car orders and
	 * returns it
	 * 
	 * @return A collection of completed car orders
	 */
	public List<CarOrder> getCompletedCarOrders() {
		return Collections.unmodifiableList(getCompany().getProductionSchedule().getCompletedCarOrders());
	}

	/**
	 * This method creates a new car order and shows the results on the gui
	 * 
	 * @param modelOption
	 *            representing the car model
	 */
	public void makeOrder(CarModel model) {
		try {
			CarModelSpecification spec = model.getSpecification();
			CarOrder order = new CarOrder(spec);
			order.setEngine(getUi().askCarOption(spec, Engine.class));
			order.setGearbox(getUi().askCarOption(spec, Gearbox.class));
			order.setWheels(getUi().askCarOption(spec, Wheels.class));
			order.setSeats(getUi().askCarOption(spec, Seats.class));
			order.setSpoiler(getUi().askCarOption(spec, Spoiler.class));
			getCompany().getProductionSchedule().addCarOrder(order);
			getUi().showDeliveryTime(getCompany().getProductionSchedule().calculateExpectedDeliveryTime(order));
			getUi().showGarageHolderMenu();
		} catch (Exception t) {
			getUi().showError(t);
			getUi().showLoginOptions();
		}
	}

	/**
	 * This method returns a list of the available car models
	 * 
	 * @return A list of the available car models
	 */
	public List<CarModel> getAvailableCarModels() {
		return Collections.unmodifiableList(getCompany().getAvailableCarModels());
	}

	@Override
	public void handleEvent(Event event) {
		if (event instanceof OrderEvent) {
			makeOrder(((OrderEvent) event).getModel());
		} else if (event instanceof ShowOrdersEvent) {
			getUi().showOrders(getPendingCarOrders(), getCompletedCarOrders(), getCompany().getProductionSchedule());
		} else if (event instanceof ShowCarModelsEvent) {
			getUi().showCarModels(getAvailableCarModels());
		} else if (event instanceof ShowOrderDetailsEvent)
			getUi().showOrderDetails(getPendingCarOrders(), getCompletedCarOrders(),
					getCompany().getProductionSchedule());
	}
}
