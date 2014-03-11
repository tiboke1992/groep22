package be.kuleuven.assemassist.domain;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class DeliveryTime {

	private DateTime startTime;
	private Map<Class<? extends WorkStation>, Long> timeSpentAtWorkposts;

	public DeliveryTime() {
		startTime = new DateTime();
		timeSpentAtWorkposts = new HashMap<>();
	}

	public DateTime getEstimatedDeliveryTime() {
		int minutesToAdd;
		Long driveTrainTime = timeSpentAtWorkposts.get(DriveTrainPost.class);
		if (driveTrainTime != null) {
			minutesToAdd = driveTrainTime.intValue();
			Long accessoriesTime = timeSpentAtWorkposts.get(AccessoriesPost.class);
			if (accessoriesTime != null)
				minutesToAdd += accessoriesTime.intValue();
			else
				minutesToAdd += 60;
		} else
			minutesToAdd = 120;
		return startTime.plusMinutes(minutesToAdd);
	}
}
