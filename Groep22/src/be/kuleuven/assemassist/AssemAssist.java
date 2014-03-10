package be.kuleuven.assemassist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import be.kuleuven.assemassist.domain.CarModel;
import be.kuleuven.assemassist.domain.CarOrder;

public class AssemAssist {

	private static final DateFormat COMPLETED_FORMAT = new SimpleDateFormat();//TODO proper format
	private static final DateFormat PENDING_FORMAT = new SimpleDateFormat();//TODO proper format
	
	private Queue<CarOrder> orders;
	private Queue<CarOrder> completed;
	
	public static void main(String[] args) {
		AssemAssist assemAssist = new AssemAssist();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Login as:");
		System.out.println("1) Garage holder");
		System.out.println("2) Car mechanic");
		System.out.println("3) Manager");
		System.out.println("*) Exit");
		int option = scanner.nextInt();
		switch(option){
		case 1:
			System.out.println("Successfully logged in as a garage holder!");
			System.out.println();
			System.out.println("Overview:");
			System.out.println("Pending orders:");
			for(CarOrder order : assemAssist.getOrders()){
				System.out.println(order.getId()+"\t\t"+PENDING_FORMAT.format(order.getEstimatedCompletionTime()));
			}
			System.out.println("Completed orders:");
			for(CarOrder order : assemAssist.getCompleted()){
				System.out.println(order.getId()+"\t\t"+COMPLETED_FORMAT.format(order.getCompletionTime()));
			}
			System.out.println();
			System.out.println("What do you want to do?");
			System.out.println("1) Place a new order");
			System.out.println("*) Exit");
			option = scanner.nextInt();
			if(option == 1){
				CarOrder order = new CarOrder();
				System.out.println("Available car models:");
				for(int i = 0; i < assemAssist.getAvailableCarModels().size(); i++){
					System.out.println((i+1)+") "+assemAssist.getAvailableCarModels().get(i));
				}
				System.out.println("*) Exit");
				option = scanner.nextInt();
				if(option > 0 || option <= assemAssist.getAvailableCarModels().size()){
					CarModel model = assemAssist.getAvailableCarModels().get(option-1);
					//TODO display order form etc..
				}
			}
			break;
		case 2:
		case 3:
			throw new UnsupportedOperationException("Not yet supported");
		}
		System.out.println("Thank you for using AssemAssist!");
	}
	
	private List<CarModel> getAvailableCarModels() {
		return new ArrayList<>();
	}

	public AssemAssist(){
		
	}
	
	public Queue<CarOrder> getOrders() {
		return orders;
	}
	
	public Queue<CarOrder> getCompleted() {
		return completed;
	}
}
