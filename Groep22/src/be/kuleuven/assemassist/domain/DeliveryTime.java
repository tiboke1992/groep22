package be.kuleuven.assemassist.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DeliveryTime {

	private Date startTime;
	private Map<WorkStation, Long> timeSpentAtWorkposts;

	public DeliveryTime() {
		startTime = new Date();
		timeSpentAtWorkposts = new HashMap<>();
	}
}
