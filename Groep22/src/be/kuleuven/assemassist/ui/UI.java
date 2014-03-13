package be.kuleuven.assemassist.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.controller.AssemblyController;
import be.kuleuven.assemassist.domain.CarModelSpecification;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.WorkStation;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.role.Role;
import be.kuleuven.assemassist.domain.task.AssemblyTask;

public class UI {

	private static final DateFormat COMPLETED_FORMAT = new SimpleDateFormat();// TODO
	private static final DateFormat PENDING_FORMAT = new SimpleDateFormat();// TODO

	private AssemblyController controller;
	private Scanner scanner;

	public UI(AssemblyController controller) {
		this.controller = controller;
		this.controller.setUi(this);
		this.scanner = new Scanner(System.in);
	}

	public void login() {
		System.out.println("Login as:");
		System.out.println("1) Garage holder");
		System.out.println("2) Car mechanic");
		System.out.println("3) Manager");
		System.out.println("*) Exit");
		controller.loginAs(scanner.nextInt());
	}

	public void showGreeting(Role role) {
		System.out.println("Successfully logged in as " + role + "!");
		System.out.println();
	}

	public void showOrders() {
		System.out.println("Overview:");
		System.out.println("Pending orders:");
		for (CarOrder order : controller.getPendingCarOrders()) {
			System.out.println(order.getId() + "\t\t"
					+ PENDING_FORMAT.format(order.getDeliveryTime().getEstimatedDeliveryTime()));
		}
		System.out.println("Completed orders:");
		for (CarOrder order : controller.getCompletedCarOrders()) {
			System.out.println(order.getId() + "\t\t"
					+ COMPLETED_FORMAT.format(order.getDeliveryTime().getEstimatedDeliveryTime()));
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
			controller.shutdown();
	}

	private void showCarModels() {
		System.out.println("Available car models:");
		for (int i = 0; i < controller.getAvailableCarModels().size(); i++) {
			System.out.println((i + 1) + ") " + controller.getAvailableCarModels().get(i));
		}
		System.out.println("*) Exit");
		int option = scanner.nextInt();
		if (option > 0 && option <= controller.getAvailableCarModels().size())
			controller.makeOrder(option - 1);
		else
			controller.shutdown();
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
		controller.shutdown();
		return null;
	}

	public void showDeliveryTime(DateTime time) {
		System.out.println("Estimated delivery time: " + PENDING_FORMAT.format(time));
	}

	public void showWorkPostMenu() {
		System.out.println("At which workpost are you working?");
		List<WorkStation> workStations = controller.getWorkStations();
		for (int i = 0; i < workStations.size(); i++)
			System.out.println(i + 1 + ") " + workStations.get(i));
		System.out.println("*) Exit");
		int option = scanner.nextInt();

		controller.selectWorkStation(option - 1);
	}

	public void showPendingAssemblyTasks(Collection<AssemblyTask> tasks) {
		int counter = 1;
		for (AssemblyTask a : tasks) {
			if (!a.isDone()) {
				System.out.println(counter + ": " + a.toString());
				counter++;
			}
		}
	}

	public void pickAssemblyTask() {
		System.out.println("What task do you want to work on?");
		int option = scanner.nextInt();
		controller.selectTask(option);
		
	}
}
