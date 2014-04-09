package be.kuleuven.assemassist.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.controller.Controller;
import be.kuleuven.assemassist.controller.OrderController;
import be.kuleuven.assemassist.controller.WorkStationController;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.carmodel.CarModel;
import be.kuleuven.assemassist.domain.carmodel.CarModelSpecification;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.domain.task.action.Action;
import be.kuleuven.assemassist.domain.workpost.WorkStation;
import be.kuleuven.assemassist.event.AssemblyAdvanceEvent;
import be.kuleuven.assemassist.event.CompleteActionEvent;
import be.kuleuven.assemassist.event.LoginEvent;
import be.kuleuven.assemassist.event.OrderEvent;
import be.kuleuven.assemassist.event.SelectTaskEvent;
import be.kuleuven.assemassist.event.ShutdownEvent;
import be.kuleuven.assemassist.event.WorkStationSelectionEvent;

public class UI extends AbstractUI {

	private static final DateFormat COMPLETED_FORMAT = new SimpleDateFormat();// TODO
	private static final DateFormat PENDING_FORMAT = new SimpleDateFormat();// TODO

	private OrderController orderController;
	private WorkStationController workStationController;
	private Scanner scanner;

	public UI(OrderController orderController, WorkStationController workStationController) {
		this.orderController = orderController;
		addController(orderController);
		this.workStationController = workStationController;
		addController(workStationController);
		this.scanner = new Scanner(System.in);
	}

	@Override
	public void addController(Controller c) {
		super.addController(c);
		c.setUi(this);
	}

	public void showLoginOptions() {
		System.out.println("Login as:");
		System.out.println("1) Garage holder");
		System.out.println("2) Car mechanic");
		System.out.println("3) Manager");
		System.out.println("*) Exit");
		try {
			pushEvent(new LoginEvent(scanner.nextInt()));
		} catch (Throwable t) {
			shutdown();
		}
	}

	public void showManagerMenu() {
		System.out.println("1) advance assembly line");
		System.out.println("2) login as someone else");
		System.out.println("*) exit");
		try {
			int option = scanner.nextInt();
			if (option == 1) {
				pushEvent(new AssemblyAdvanceEvent());
			} else if (option == 2) {
				showLoginOptions();
			} else {
				shutdown();
			}
		} catch (Throwable t) {
			shutdown();
		}
	}

	public void showGreeting(String role) {
		System.out.println("Successfully logged in as " + role + "!");
		System.out.println();
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
		System.out.println("2) Log in as someting else");
		System.out.println("*) Exit");
		try {
			int option = scanner.nextInt();
			if (option == 1)
				showCarModels();
			else if (option == 2) {
				showLoginOptions();
			} else {
				shutdown();
			}
		} catch (Throwable t) {
			shutdown();
		}
	}

	private void showCarModels() {
		System.out.println("Available car models:");
		List<CarModel> carModels = orderController.getAvailableCarModels();
		for (int i = 0; i < carModels.size(); i++) {
			System.out.println((i + 1) + ") " + carModels.get(i));
		}
		System.out.println("*) Exit");
		try {
			int option = scanner.nextInt();
			if (option > 0 && option <= carModels.size())
				pushEvent(new OrderEvent(carModels.get(option - 1)));
			else
				shutdown();
		} catch (Throwable t) {
			shutdown();
		}
	}

	public <T extends CarOption> T askCarOption(CarModelSpecification spec, Class<T> carOptionClass) {
		System.out.println();
		System.out.println("Choose an " + carOptionClass.getSimpleName() + ":");
		List<T> possibleOptions = spec.filterOutInvalidOptions(carOptionClass.getEnumConstants(), carOptionClass);
		for (int i = 0; i < possibleOptions.size(); i++)
			System.out.println((i + 1) + ") " + possibleOptions.get(i));
		System.out.println("*) Exit");
		try {
			int option = scanner.nextInt();
			if (option > 0 && option <= possibleOptions.size())
				return possibleOptions.get(option - 1);
			shutdown();
		} catch (Throwable t) {
			shutdown();
		}
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
		System.out.println("0) login as someone else");
		System.out.println("*) Exit");
		try {
			int option = scanner.nextInt();
			if (option == 0) {
				showLoginOptions();
			} else if (option - 1 >= 0 && option - 1 <= workStations.size()) {
				pushEvent(new WorkStationSelectionEvent(option - 1));
			} else {
				shutdown();
			}
		} catch (Throwable t) {
			shutdown();
		}
	}

	public void showTaskCompleted(AssemblyTask task) {
		System.out.println("Task : " + task + " Completed.");
	}

	public void showPendingAssemblyTasks(List<AssemblyTask> tasks) {
		if (tasks.isEmpty()) {
			System.out.println("All tasks completed succesfully");
			showLoginOptions();
		} else {
			System.out.println("0) login as someone else");
			System.out.println("What task do you want to work on?");
			for (int i = 0; i < tasks.size(); i++) {
				System.out.println(i + 1 + ") " + tasks.get(i));
			}
			try {
				int option = scanner.nextInt();
				if (option == 0) {
					showLoginOptions();
				} else {
					pushEvent(new SelectTaskEvent(option));
				}
			} catch (Throwable t) {
				shutdown();
			}
		}
	}

	public void showSequence(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++)
			System.out.println((i + 1) + ") " + actions.get(i));
		if (actions.size() == 0) {
			System.out.println("You have already finished this task");
		} else {
			System.out.println("");
			System.out.println("Press 0 to exit");
			System.out.println("Press 1 to finish this task");
			System.out.println("Press 2 to login as another user");
		}
		try {
			int option = scanner.nextInt();
			if (option == 0) {
				shutdown();
			} else if (option == 1) {
				pushEvent(new CompleteActionEvent());
			} else if (option == 2) {
				showLoginOptions();
			}
		} catch (Throwable t) {
			shutdown();
		}
	}

	public void shutdown() {
		pushEvent(new ShutdownEvent());
	}

	public void showNoCarToWorkOn() {
		System.out.println("There is currently no car to work on");
		showLoginOptions();
	}

	public void showOverview(String overview) {
		System.out.println("Overview :");
		System.out.println(overview);
	}

	public void showCanNotAdvanceError() {
		System.out.println("The assembly line could not be advanced.");
		showManagerMenu();
	}

	public void showAssemblyLineAdvanced() {
		System.out.println("Assembly line succesfully advanced");
		this.showManagerMenu();
	}

	public void showError(Throwable t) {
		System.out.println("Something went wrong: " + t.getMessage());
		System.out.println("Logging out..");
	}
}
