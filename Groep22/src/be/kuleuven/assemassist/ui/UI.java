package be.kuleuven.assemassist.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.controller.OrderController;
import be.kuleuven.assemassist.controller.SystemController;
import be.kuleuven.assemassist.controller.WorkStationController;
import be.kuleuven.assemassist.domain.CarModelSpecification;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.role.Role;
import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.domain.task.action.Action;
import be.kuleuven.assemassist.domain.workpost.WorkStation;

public class UI {

	private static final DateFormat COMPLETED_FORMAT = new SimpleDateFormat();// TODO
	private static final DateFormat PENDING_FORMAT = new SimpleDateFormat();// TODO

	private SystemController systemController;
	private OrderController orderController;
	private WorkStationController workStationController;
	private Scanner scanner;

	public UI(SystemController systemController, OrderController orderController,
			WorkStationController workStationController) {
		this.systemController = systemController;
		this.systemController.setUi(this);
		this.orderController = orderController;
		this.orderController.setUi(this);
		this.workStationController = workStationController;
		this.workStationController.setUi(this);
		this.scanner = new Scanner(System.in);
	}

	public void showLoginOptions() {
		System.out.println("Login as:");
		System.out.println("1) Garage holder");
		System.out.println("2) Car mechanic");
		System.out.println("3) Manager");
		System.out.println("*) Exit");
		systemController.loginAs(scanner.nextInt());
	}

	public void showGreeting(Role role) {
		System.out.println("Successfully logged in as " + role + "!");
		System.out.println();
		if (role instanceof CarMechanic)
			workStationController.setCarMechanic((CarMechanic) role);
	}

	public void showOrders() {
		System.out.println();
		System.out.println("Overview:");
		System.out.println("Pending orders:");
		for (CarOrder order : orderController.getPendingCarOrders()) {
			System.out.println(order.getId() + "\t\t"
					+ PENDING_FORMAT.format(order.getDeliveryTime().getEstimatedDeliveryTime().toDate()));
		}
		System.out.println("Completed orders:");
		for (CarOrder order : orderController.getCompletedCarOrders()) {
			System.out.println(order.getId() + "\t\t"
					+ COMPLETED_FORMAT.format(order.getDeliveryTime().getEstimatedDeliveryTime().toDate()));
		}
	}

	public void showMenu() {
		System.out.println();
		System.out.println("What do you want to do?");
		System.out.println("1) Place a new order");
		System.out.println("*) Exit");
		int option = scanner.nextInt();
		if (option == 1)
			showCarModels();
		else
			systemController.shutdown();
	}

	private void showCarModels() {
		System.out.println("Available car models:");
		for (int i = 0; i < orderController.getAvailableCarModels().size(); i++) {
			System.out.println((i + 1) + ") " + orderController.getAvailableCarModels().get(i));
		}
		System.out.println("*) Exit");
		int option = scanner.nextInt();
		if (option > 0 && option <= orderController.getAvailableCarModels().size())
			orderController.makeOrder(option - 1);
		else
			systemController.shutdown();
	}

	public <T extends CarOption> T askCarOption(CarModelSpecification spec, Class<T> carOptionClass) {
		System.out.println();
		System.out.println("Choose an " + carOptionClass.getSimpleName() + ":");
		List<T> possibleOptions = spec.filterOutInvalidOptions(carOptionClass.getEnumConstants(), carOptionClass);
		for (int i = 0; i < possibleOptions.size(); i++)
			System.out.println((i + 1) + ") " + possibleOptions.get(i));
		System.out.println("*) Exit");
		int option = scanner.nextInt();
		if (option > 0 && option <= possibleOptions.size())
			return possibleOptions.get(option - 1);
		systemController.shutdown();
		return null;
	}

	public void showDeliveryTime(DateTime time) {
		System.out.println("Estimated delivery time: " + PENDING_FORMAT.format(time.toDate()));
	}

	public void showWorkPostMenu() {
		System.out.println("At which workpost are you working?");
		List<WorkStation> workStations = workStationController.getWorkStations();
		for (int i = 0; i < workStations.size(); i++)
			System.out.println(i + 1 + ") " + workStations.get(i));
		System.out.println("*) Exit");
		int option = scanner.nextInt();

		workStationController.selectWorkStation(option - 1);
	}

	public void showPendingAssemblyTasks(Collection<AssemblyTask> tasks) {
		int counter = 1;
		for (AssemblyTask a : tasks) {
			if (!a.isDone()) {
				System.out.println(counter + ": " + a);
				counter++;
			}
		}
	}

	public void pickAssemblyTask() {
		System.out.println("What task do you want to work on?");
		int option = scanner.nextInt();
		workStationController.selectTask(option);

	}

	public void showSequence(AssemblyTask task) {
		if (!task.getActions().isEmpty()) {
			System.out.println(task + " Has the following sequence of actions:");
			for (Action a : task.getActions())
				System.out.println("\t" + a);
			System.out.println("Press ENTER when you have finished the next action.");
		} else {

		}
	}
}
