package be.kuleuven.assemassist.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.controller.Controller;
import be.kuleuven.assemassist.domain.AssemblyTask;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.ProductionSchedule;
import be.kuleuven.assemassist.domain.carmodel.CarModel;
import be.kuleuven.assemassist.domain.carmodel.CarModelSpecification;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.options.Spoiler;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.sorting.SortingAlgorithm;
import be.kuleuven.assemassist.domain.sorting.SupportedSortingAlgorithms;
import be.kuleuven.assemassist.domain.workpost.WorkStation;
import be.kuleuven.assemassist.event.CarOrderCompletedEvent;
import be.kuleuven.assemassist.event.CarOrderCreatedEvent;
import be.kuleuven.assemassist.event.CarOrderModelSelectedEvent;
import be.kuleuven.assemassist.event.ChangeSchedulingAlgorithmEvent;
import be.kuleuven.assemassist.event.CheckAssemblyLineStatusEvent;
import be.kuleuven.assemassist.event.LoginEvent;
import be.kuleuven.assemassist.event.SelectBatchSortingAlgorithm;
import be.kuleuven.assemassist.event.SelectTaskEvent;
import be.kuleuven.assemassist.event.ShowCarModelsEvent;
import be.kuleuven.assemassist.event.ShowOrderDetailsEvent;
import be.kuleuven.assemassist.event.ShowOrdersEvent;
import be.kuleuven.assemassist.event.ShowWorkPostsMenuEvent;
import be.kuleuven.assemassist.event.ShutdownEvent;
import be.kuleuven.assemassist.event.TaskCompletedEvent;
import be.kuleuven.assemassist.event.WorkStationSelectionEvent;

public class UI extends AbstractUI {

	private static final DateFormat COMPLETED_FORMAT = new SimpleDateFormat();// TODO
	private static final DateFormat PENDING_FORMAT = new SimpleDateFormat();// TODO

	private Scanner scanner;

	public UI() {
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
		System.out.println("1) check assembly line status");
		System.out.println("2) select alternative scheduling algorithm ");
		System.out.println("3) login as someone else");
		System.out.println("*) exit");
		try {
			int option = scanner.nextInt();
			if (option == 1) {
				pushEvent(new CheckAssemblyLineStatusEvent());
			} else if (option == 2) {
				showSchedulingAlgorithms();
			} else if (option == 3) {
				showLoginOptions();
			} else {
				shutdown();
			}
		} catch (Throwable t) {
			shutdown();
		}
	}

	// TODO current algoritme nog tonen
	public void showSchedulingAlgorithms() {
		System.out.println("0) exit");
		int counter = 1;
		for (SupportedSortingAlgorithms sort : SupportedSortingAlgorithms
				.values()) {
			System.out.println(counter + ") " + sort.toString());
			counter++;
		}
		try {
			int option = scanner.nextInt();
			if (option == 0) {
				showManagerMenu();
			} else if (option > 0
					&& option <= SupportedSortingAlgorithms.values().length) {
				SupportedSortingAlgorithms sort = SupportedSortingAlgorithms
						.values()[option - 1];
				pushEvent(new ChangeSchedulingAlgorithmEvent(sort));
			}
		} catch (Throwable t) {
			shutdown();
		}
	}

	public void showGreeting(String role) {
		System.out.println("Successfully logged in as " + role + "!");
		System.out.println();
	}

	public void showOrders(Collection<CarOrder> pending,
			Collection<CarOrder> completed, ProductionSchedule schedule) {
		System.out.println();
		System.out.println("Overview:");
		System.out.println("Pending orders:");
		for (CarOrder order : pending) {
			System.out.println(order.getId()
					+ "\t\t"
					+ PENDING_FORMAT.format(schedule
							.calculateExpectedDeliveryTime(order).toDate()));
		}
		System.out.println("Completed orders:");
		for (CarOrder order : completed) {
			System.out.println(order.getId()
					+ "\t\t"
					+ COMPLETED_FORMAT.format(order.getDeliveryTime()
							.getCompletionTime().toDate()));
		}
	}
	
	public void showChangedToFifoSort(){
		System.out.println("Succesfully changed the sorting algorithm to FIFO-Sorting");
		showManagerMenu();
	}

	public void showGarageHolderMenu() {
		pushEvent(new ShowOrdersEvent());
		System.out.println();
		System.out.println("What do you want to do?");
		System.out.println("1) Place a new order");
		System.out.println("2) Log in as someting else");
		System.out.println("3) View order details");
		System.out.println("*) Exit");
		try {
			int option = scanner.nextInt();
			if (option == 1)
				pushEvent(new ShowCarModelsEvent());
			else if (option == 2)
				showLoginOptions();
			else if (option == 3)
				pushEvent(new ShowOrderDetailsEvent());
			else
				shutdown();
		} catch (Throwable t) {
			shutdown();
		}
	}

	public void showOrderDetails(List<CarOrder> pending,
			List<CarOrder> completed, ProductionSchedule schedule) {
		int i = 0;
		System.out.println();
		System.out.println("Pending orders:");
		for (CarOrder order : pending) {
			System.out.println(i++ + ") " + order);
		}
		System.out.println("Completed orders:");
		for (CarOrder order : completed) {
			System.out.println(i++ + ") " + order);
		}
		System.out.println("*) Back to menu");
		try {
			int option = scanner.nextInt();
			if (option >= 0 && option < pending.size()) {
				showOrderDetail(pending.get(option), schedule);
			} else if ((option - pending.size()) < completed.size()) {
				showOrderDetail(completed.get(option - pending.size()),
						schedule);
			} else
				showGarageHolderMenu();
		} catch (Throwable t) {
			showGarageHolderMenu();
		}
	}

	private void showOrderDetail(CarOrder order,
			ProductionSchedule productionSchedule) {
		System.out.println("Order " + order.getId());
		if (order.getDeliveryTime().getCompletionTime() == null)
			System.out.println("\tEstimated delivery time: "
					+ productionSchedule.calculateExpectedDeliveryTime(order)
							.toDate());
		else
			System.out.println("\tDelivery time: "
					+ order.getDeliveryTime().getCompletionTime());
		System.out.println("\tOptions:");
		System.out.println("\t\tEngine: " + order.getEngine());
		System.out.println("\t\tGearbox: " + order.getGearbox());
		System.out.println("\t\tSeats: " + order.getSeats());
		System.out.println("\t\tWheels: " + order.getWheels());
		if (order.getSpoiler() != Spoiler.NONE)
			System.out.println("\t\tSpoiler: " + order.getSpoiler());
		System.out.println();
		System.out.println("Enter any key to return to menu");
		try {
			scanner.nextInt();
		} catch (Throwable t) {
		}
		showGarageHolderMenu();
	}

	public void showCarModels(List<CarModel> carModels) {
		System.out.println("Available car models:");
		for (int i = 0; i < carModels.size(); i++) {
			System.out.println((i + 1) + ") " + carModels.get(i));
		}
		System.out.println("*) Exit");
		try {
			int option = scanner.nextInt();
			if (option > 0 && option <= carModels.size())
				pushEvent(new CarOrderModelSelectedEvent(
						carModels.get(option - 1)));
			else
				shutdown();
		} catch (Throwable t) {
			shutdown();
		}
	}

	public <T extends CarOption> T askCarOption(CarModelSpecification spec,
			Class<T> carOptionClass) {
		System.out.println();
		System.out.println("Choose an " + carOptionClass.getSimpleName() + ":");
		List<T> possibleOptions = spec.filterOutInvalidOptions(
				carOptionClass.getEnumConstants(), carOptionClass);
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

	public void onOrderCompleted(DateTime time) {
		System.out.println("Estimated delivery time: "
				+ PENDING_FORMAT.format(time.toDate()));
		pushEvent(new CarOrderCreatedEvent());
	}

	public void showWorkPostMenu(List<WorkStation> workStations) {
		System.out.println("At which workpost are you working?");
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

	public void showAllTasksCompleted() {
		System.out.println("All tasks completed succesfully");
		showLoginOptions();
	}

	public void showPendingAssemblyTasks(List<AssemblyTask> tasks) {
		System.out.println();
		System.out.println("What task do you want to work on?");
		System.out.println("0) login as someone else");
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

	public void showHandleTask(CarMechanic mechanic) {
		System.out.println("");
		System.out.println("1) finish this task");
		System.out.println("*) return to menu");
		try {
			int option = scanner.nextInt();
			if (option == 1) {
				System.out
						.println("Enter the time it took to complete this task.");
				pushEvent(new TaskCompletedEvent(scanner.nextInt()));
			} else
				pushEvent(new ShowWorkPostsMenuEvent(mechanic));
		} catch (Throwable t) {
			pushEvent(new ShowWorkPostsMenuEvent(mechanic));
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

	public void showAssemblyLineAdvanced() {
		System.out.println();
		System.out.println("Assembly line succesfully advanced");
	}

	public void showWorkOrderCompleted(CarOrder order) {
		System.out.println("Order " + order + " has been completed.");
		pushEvent(new CarOrderCompletedEvent(order));
	}

	public void showAssemblyLineStatus(String overview) {
		System.out.println(overview);
		showManagerMenu();
	}

	public void showBatchPermutations(List<List<CarOption>> caroptions) {
		if(caroptions.isEmpty()){
			System.out.println("There is no set of options with more then 3 orders");
			showManagerMenu();
		}else{
			System.out.println("0) exit");
			int counter = 1;
			for (List<CarOption> options : caroptions) {
				String str = "";
				for (CarOption option : options) {
					str += option.toString() + " - ";
				}
				System.out.println("Option : " + counter + ") " + str);
				counter++;
			}
			try {
				int option = scanner.nextInt();
				if (option == 0) {
					showManagerMenu();;
				} else if(option > 0 && option <= caroptions.size()) {
					List<CarOption> selectedOption = caroptions.get(option - 1);
					pushEvent(new SelectBatchSortingAlgorithm(selectedOption));
				}else{
					shutdown();
				}
			} catch (Throwable t) {
				shutdown();
			}
			
		}
		
	}

	public void showBatchSortSelected() {
		System.out.println("Succesfully changed the sorting algorithm to Batch-Sorting");
		showManagerMenu();
	}

}
