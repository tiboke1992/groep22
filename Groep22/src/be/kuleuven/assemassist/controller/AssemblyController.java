package be.kuleuven.assemassist.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

import be.kuleuven.assemassist.domain.AccessoriesPost;
import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.CarModel;
import be.kuleuven.assemassist.domain.CarModelSpecification;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.DriveTrainPost;
import be.kuleuven.assemassist.domain.WorkStation;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Wheels;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.role.GarageHolder;
import be.kuleuven.assemassist.domain.role.Manager;
import be.kuleuven.assemassist.domain.role.Role;
import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.ui.UI;

public class AssemblyController extends Controller {

	public CarManufacturingCompany company;
	private Role role;
	private UI ui;
	private List<AssemblyTask> assemblyTasks;

	public AssemblyController(CarManufacturingCompany company) {
		super(company);
	}

	public List<CarModel> getAvailableCarModels() {
		List<CarModel> models = company.getAvailableCarModels();
		List<CarModel> modelsCopy = new ArrayList<>(models.size());
		for (CarModel model : models)
			modelsCopy.add(model);// TODO moeten we ok elk carmodel zelf klonen?
		return modelsCopy;
	}

	public void loginAs(int roleId) {
		switch (roleId) {
		case 1:
			role = new GarageHolder();
			ui.showGreeting(role);
			ui.showOrders();
			ui.showMenu();
			break;
		case 2:
			role = new CarMechanic();
			ui.showGreeting(role);
			ui.showWorkPostMenu();
			// ui.pickAssemblyTask();
			break;
		case 3:
			role = new Manager();
			ui.showGreeting(role);
			break;
		default:
			throw new IllegalArgumentException("Invalid roleId " + roleId);
		}
	}

	public Collection<CarOrder> getPendingCarOrders() {
		Queue<CarOrder> orders = company.getProductionSchedule()
				.getPendingCarOrders();
		List<CarOrder> ordersCopy = new ArrayList<>();
		for (CarOrder carOrder : orders)
			ordersCopy.add(carOrder);
		return ordersCopy;
	}

	public Collection<CarOrder> getCompletedCarOrders() {
		List<CarOrder> orders = company.getProductionSchedule()
				.getCompletedCarOrders();
		List<CarOrder> ordersCopy = new ArrayList<>();
		for (CarOrder carOrder : orders)
			ordersCopy.add(carOrder);
		return ordersCopy;
	}

	public void shutdown() {
		System.out.println("Thanks for using AssemAssist.");
		System.exit(0);
	}

	public void makeOrder(int modelOption) {
		CarModel model = getAvailableCarModels().get(modelOption);
		CarModelSpecification spec = model.getSpecification();
		CarOrder order = new CarOrder(spec);
		order.setEngine(ui.askCarOption(spec, Engine.class));
		order.setGearbox(ui.askCarOption(spec, Gearbox.class));
		order.setWheels(ui.askCarOption(spec, Wheels.class));
		order.setSeats(ui.askCarOption(spec, Seats.class));
		company.getProductionSchedule().addCarOrder(order);
		ui.showDeliveryTime(order.getDeliveryTime().getEstimatedDeliveryTime());
		ui.showMenu();
	}

	public List<WorkStation> getWorkStations() {
		List<WorkStation> workStations = new ArrayList<>();
		workStations.add(new DriveTrainPost());
		workStations.add(new AccessoriesPost());
		return workStations;
	}

	public void setUi(UI ui) {
		this.ui = ui;
	}

}
