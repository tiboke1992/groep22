package be.kuleuven.assemassist.domain;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.domain.workpost.WorkStation;

/**
 * 
 * This class manages the delivery times information
 * 
 */
public class DeliveryTime {

	private DateTime startTime;
	private DateTime completionTime;
	private Map<Class<? extends WorkStation>, Integer> timeSpentAtWorkposts;

	public DeliveryTime() {
		startTime = new DateTime();
		timeSpentAtWorkposts = new HashMap<>();
	}

	public DeliveryTime(DeliveryTime deliveryTime) {
		this.startTime = deliveryTime.startTime;
		this.completionTime = deliveryTime.completionTime;
		this.timeSpentAtWorkposts = new HashMap<>();
		for (Class<? extends WorkStation> c : deliveryTime.timeSpentAtWorkposts.keySet()) {
			this.timeSpentAtWorkposts.put(c, deliveryTime.timeSpentAtWorkposts.get(c));
		}
	}

	public int getTimeSpentAtWorkpost(Class<? extends WorkStation> workStation) {
		Integer i = timeSpentAtWorkposts.get(workStation);
		return i == null ? 60 : i;
	}

	public void setCompletionTime(DateTime completionTime) {
		this.completionTime = completionTime;
	}

	/**
	 * 
	 * @return Expected total time spent at workposts
	 */
	public int getTotalTimeSpentAtWorkposts() {
		int total = 0;
		for (Integer i : timeSpentAtWorkposts.values()) {
			total += i;
		}
		for (int i = 0; i < 2 - timeSpentAtWorkposts.values().size(); i++)
			total += 60;
		return total;
	}

	public void completeTask(Class<? extends WorkStation> workStation, int timeInMinutes) {
		timeSpentAtWorkposts.put(workStation, timeInMinutes);
	}

	/**
	 * 
	 * @return The estimated delivery time
	 */
	// public DateTime getEstimatedDeliveryTime() {
	// // TODO kijken of er nog andere orders zijn dus lijst bijhouden van
	// // orders en kijken wat er nog op den band staat
	//
	// int minutesToAdd;
	// Integer driveTrainTime = timeSpentAtWorkposts.get(DriveTrainPost.class);
	// if (driveTrainTime != null) {
	// minutesToAdd = driveTrainTime.intValue();
	// Integer accessoriesTime =
	// timeSpentAtWorkposts.get(AccessoriesPost.class);
	// if (accessoriesTime != null)
	// minutesToAdd += accessoriesTime.intValue();
	// else
	// minutesToAdd += 60;
	// } else
	// minutesToAdd = 120;
	// return startTime.plusMinutes(minutesToAdd);
	// }

	public DateTime getStartTime() {
		return startTime;
	}

	public DateTime getCompletionTime() {
		return completionTime;
	}
}
