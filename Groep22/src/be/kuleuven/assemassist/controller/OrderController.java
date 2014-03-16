package be.kuleuven.assemassist.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.CarModel;
import be.kuleuven.assemassist.domain.CarModelSpecification;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Wheels;

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
	 * This method creates a copy of the current pending car orders and returns it
	 * @return A collection of pending car orders
	 */
	public Collection<CarOrder> getPendingCarOrders() {
		Queue<CarOrder> orders = getCompany().getProductionSchedule().getPendingCarOrders();
		List<CarOrder> ordersCopy = new ArrayList<>();
		for (CarOrder carOrder : orders)
			ordersCopy.add(carOrder);
		return ordersCopy;
	}

	/**
	 * This method creates a copy of the current completed car orders and returns it
	 * @return A collection of completed car orders
	 */
	public Collection<CarOrder> getCompletedCarOrders() {
		List<CarOrder> orders = getCompany().getProductionSchedule().getCompletedCarOrders();
		List<CarOrder> ordersCopy = new ArrayList<>();
		for (CarOrder carOrder : orders)
			ordersCopy.add(carOrder);
		return ordersCopy;
	}
	
	
/**
 * This method creates a new car order and shows the results on the gui
 * @param modelOption representing the car model
 */
	public void makeOrder(int modelOption) {
		CarModel model = getCompany().getAvailableCarModels().get(modelOption);
		CarModelSpecification spec = model.getSpecification();
		CarOrder order = new CarOrder(spec);
		order.setEngine(getUi().askCarOption(spec, Engine.class));
		order.setGearbox(getUi().askCarOption(spec, Gearbox.class));
		order.setWheels(getUi().askCarOption(spec, Wheels.class));
		order.setSeats(getUi().askCarOption(spec, Seats.class));
		getCompany().getProductionSchedule().addCarOrder(order);
		getUi().showDeliveryTime(order.getDeliveryTime().getEstimatedDeliveryTime());
		getUi().showOrders();
		getUi().showMenu();
	}

	/**
	 * This method returns a list of the available car models
	 * @return A list of the available car models
	 */
	public List<CarModel> getAvailableCarModels() {
		List<CarModel> models = getCompany().getAvailableCarModels();
		List<CarModel> modelsCopy = new ArrayList<>(models.size());
		for (CarModel model : models)
			modelsCopy.add(model);
		return modelsCopy;
	}
}
