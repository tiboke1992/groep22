package be.kuleuven.assemassist.domain;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.domain.workpost.AccessoriesPost;
import be.kuleuven.assemassist.domain.workpost.DriveTrainPost;
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

	public DeliveryTime(DeliveryTime deliveryTime) {
		this.startTime = deliveryTime.startTime;
		this.completionTime = deliveryTime.completionTime;
		this.timeSpentAtWorkposts = new HashMap<>();
		for (Class<? extends WorkStation> c : deliveryTime.timeSpentAtWorkposts.keySet()) {
			this.timeSpentAtWorkposts.put(c, deliveryTime.timeSpentAtWorkposts.get(c));
		}
	}

	public DeliveryTime(DateTime time) {
		startTime = time;
		timeSpentAtWorkposts = new HashMap<>();
		timeSpentAtWorkposts.put(DriveTrainPost.class, 60);
		timeSpentAtWorkposts.put(AccessoriesPost.class, 60);
	}

	public int getTimeSpentAtWorkpost(Class<? extends WorkStation> workStation) {
		return timeSpentAtWorkposts.get(workStation);
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
		return total;
	}

	public void setTimeSpentOnTaskAtWorkpost(Class<? extends WorkStation> workStation, int timeInMinutes) {
		int time = timeSpentAtWorkposts.get(workStation);
		timeSpentAtWorkposts.put(workStation, time - timeInMinutes);
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public DateTime getCompletionTime() {
		return completionTime;
	}
}
