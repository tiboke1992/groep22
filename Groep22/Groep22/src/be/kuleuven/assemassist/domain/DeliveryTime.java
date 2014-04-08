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
	private Map<Class<? extends WorkStation>, Long> timeSpentAtWorkposts;

	public DeliveryTime() {
		startTime = new DateTime();
		timeSpentAtWorkposts = new HashMap<>();
	}

	public DeliveryTime(DeliveryTime deliveryTime) {
		this.startTime = deliveryTime.startTime;
		this.timeSpentAtWorkposts = new HashMap<>();
		for (Class<? extends WorkStation> c : deliveryTime.timeSpentAtWorkposts
				.keySet()) {
			this.timeSpentAtWorkposts.put(c,
					deliveryTime.timeSpentAtWorkposts.get(c));
		}
	}

	/**
	 * 
	 * @return The estimated delivery time
	 */
	public DateTime getEstimatedDeliveryTime() {
		// TODO kijken of er nog andere orders zijn dus lijst bijhouden van
		// orders en kijken wat er nog op den band staat

		int minutesToAdd;
		Long driveTrainTime = timeSpentAtWorkposts.get(DriveTrainPost.class);
		if (driveTrainTime != null) {
			minutesToAdd = driveTrainTime.intValue();
			Long accessoriesTime = timeSpentAtWorkposts
					.get(AccessoriesPost.class);
			if (accessoriesTime != null)
				minutesToAdd += accessoriesTime.intValue();
			else
				minutesToAdd += 60;
		} else
			minutesToAdd = 120;
		return startTime.plusMinutes(minutesToAdd);
	}
}
