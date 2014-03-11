package be.kuleuven.assemassist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.CarModel;
import be.kuleuven.assemassist.domain.CarModelSpecification;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.WorkStation;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Wheels;
import be.kuleuven.assemassist.domain.task.AssemblyTask;

public class AssemAssist {

	private static final DateFormat COMPLETED_FORMAT = new SimpleDateFormat();// TODO
																				// proper
																				// format
	private static final DateFormat PENDING_FORMAT = new SimpleDateFormat();// TODO
																			// proper
																			// formatter

	private CarManufacturingCompany company;

	public static void main(String[] args) {
		AssemAssist assemAssist = new AssemAssist();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Login as:");
		System.out.println("1) Garage holder");
		System.out.println("2) Car mechanic");
		System.out.println("3) Manager");
		System.out.println("*) Exit");
		int option = scanner.nextInt();
		switch (option) {
			case 1:
				System.out.println("Successfully logged in as a garage holder!");
				System.out.println();
				assemAssist.showOrders();
				assemAssist.showMenu();
				option = scanner.nextInt();
				if (option == 1) {
					assemAssist.showCarModels();
					option = scanner.nextInt();
					if (option > 0 || option <= assemAssist.getCompany().getAvailableCarModels().size()) {
						CarModel model = assemAssist.getCompany().getAvailableCarModels().get(option - 1);
						CarModelSpecification spec = model.getSpecification();
						Engine engine = askCarOption(scanner, spec, Engine.class);
						if (engine != null) {
							Gearbox gearbox = askCarOption(scanner, spec, Gearbox.class);
							if (gearbox != null) {
								Wheels wheels = askCarOption(scanner, spec, Wheels.class);
								if (wheels != null) {
									Seats seats = askCarOption(scanner, spec, Seats.class);
									CarOrder order = new CarOrder(spec);
									order.setEngine(engine);
									order.setGearbox(gearbox);
									order.setWheels(wheels);
									order.setSeats(seats);
									assemAssist.getCompany().getProductionSchedule().addCarOrder(order);
									System.out.println("Estimated delivery time: "
											+ order.getDeliveryTime().getEstimatedDeliveryTime());
								}
							}
						}
						// if user gets here should go back to the menu.
					}
				}
				break;
			case 2:
				/*
				 * / Naar dit gedeelte moete kijken
				 */
				System.out.println("At which workpost are you working?");
				for (int i = 0; i < WorkStation.getWorkStations().size(); i++)
					System.out.println(i + 1 + ") " + WorkStation.getWorkStations().get(i));
				System.out.println("*) Exit");
				option = scanner.nextInt();
				if (option > 0 || option <= WorkStation.getWorkStations().size()) {
					WorkStation workPost = WorkStation.getWorkStations().get(option - 1);
					/*
					 * / show pending assemblytasks
					 */
					int counter = 1;
					for (AssemblyTask a : workPost.getAssemblyTask().getActions()) {
						System.out.println(counter + ": " + a.toString());
						counter++;
					}

				}
				break;
			case 3:
				throw new UnsupportedOperationException("Not yet supported");
		}
		System.out.println("Thank you for using AssemAssist!");
	}

	private static <T extends CarOption> T askCarOption(Scanner scanner, CarModelSpecification spec,
			Class<T> carOptionClass) {
		System.out.println();
		System.out.println("Choose an " + carOptionClass.getSimpleName() + ":");
		List<T> possibleOptions = spec.filterOutInvalidOptions(carOptionClass.getEnumConstants(), carOptionClass);
		for (int i = 0; i < possibleOptions.size(); i++)
			System.out.println((i + 1) + ") " + possibleOptions.get(i));
		System.out.println("*) Exit");
		int option = scanner.nextInt();
		if (option > 0 || option <= possibleOptions.size())
			return possibleOptions.get(option - 1);
		return null;
	}

	private void showOrders() {
		System.out.println("Overview:");
		System.out.println("Pending orders:");
		for (CarOrder order : getCompany().getProductionSchedule().getPendingCarOrders()) {
			System.out.println(order.getId() + "\t\t"
					+ PENDING_FORMAT.format(order.getDeliveryTime().getEstimatedDeliveryTime()));
		}
		System.out.println("Completed orders:");
		for (CarOrder order : getCompany().getProductionSchedule().getCompletedCarOrders()) {
			System.out.println(order.getId() + "\t\t"
					+ COMPLETED_FORMAT.format(order.getDeliveryTime().getEstimatedDeliveryTime()));
		}
	}

	private void showMenu() {
		System.out.println();
		System.out.println("What do you want to do?");
		System.out.println("1) Place a new order");
		System.out.println("*) Exit");
	}

	private void showCarModels() {
		System.out.println("Available car models:");
		for (int i = 0; i < getCompany().getAvailableCarModels().size(); i++) {
			System.out.println((i + 1) + ") " + getCompany().getAvailableCarModels().get(i));
		}
		System.out.println("*) Exit");
	}

	public AssemAssist() {
		company = new CarManufacturingCompany();// TODO name etc
	}

	public CarManufacturingCompany getCompany() {
		return company;
	}
}
