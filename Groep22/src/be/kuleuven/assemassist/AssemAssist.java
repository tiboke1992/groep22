package be.kuleuven.assemassist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.CarModel;
import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.WorkStation;

public class AssemAssist {

	private static final DateFormat COMPLETED_FORMAT = new SimpleDateFormat();// TODO
																				// proper
																				// format
	private static final DateFormat PENDING_FORMAT = new SimpleDateFormat();// TODO
																			// proper
																			// format

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
				System.out.println("Overview:");
				System.out.println("Pending orders:");
				for (CarOrder order : assemAssist.getCompany().getProductionSchedule().getPendingCarOrders()) {
					System.out.println(order.getId() + "\t\t"
							+ PENDING_FORMAT.format(order.getEstimatedCompletionTime()));
				}
				System.out.println("Completed orders:");
				for (CarOrder order : assemAssist.getCompany().getProductionSchedule().getCompletedCarOrders()) {
					System.out.println(order.getId() + "\t\t" + COMPLETED_FORMAT.format(order.getCompletionTime()));
				}
				System.out.println();
				System.out.println("What do you want to do?");
				System.out.println("1) Place a new order");
				System.out.println("*) Exit");
				option = scanner.nextInt();
				if (option == 1) {
					CarOrder order = new CarOrder();
					System.out.println("Available car models:");
					for (int i = 0; i < assemAssist.getCompany().getAvailableCarModels().size(); i++) {
						System.out.println((i + 1) + ") " + assemAssist.getCompany().getAvailableCarModels().get(i));
					}
					System.out.println("*) Exit");
					option = scanner.nextInt();
					if (option > 0 || option <= assemAssist.getCompany().getAvailableCarModels().size()) {
						CarModel model = assemAssist.getCompany().getAvailableCarModels().get(option - 1);
						// TODO display order form etc..
					}
				}
				break;
			case 2:
				System.out.println("At which workpost are you working?");
				for (int i = 0; i < WorkStation.getWorkStations().size(); i++)
					System.out.println(i + 1 + ") " + WorkStation.getWorkStations().get(i));
				System.out.println("*) Exit");
				option = scanner.nextInt();
				if (option > 0 || option <= WorkStation.getWorkStations().size()) {
					WorkStation workPost = WorkStation.getWorkStations().get(option - 1);

				}
				break;
			case 3:
				throw new UnsupportedOperationException("Not yet supported");
		}
		System.out.println("Thank you for using AssemAssist!");
	}

	public AssemAssist() {
		company = new CarManufacturingCompany();// TODO name etc
	}

	public CarManufacturingCompany getCompany() {
		return company;
	}
}
