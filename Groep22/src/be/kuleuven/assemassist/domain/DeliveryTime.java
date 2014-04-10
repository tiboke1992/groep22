package be.kuleuven.assemassist.domain;

import org.joda.time.DateTime;

/**
 * 
 * This class manages the delivery times information
 * 
 */
public class DeliveryTime {

	private DateTime startTime;
	private DateTime completionTime;
	private int estimatedTime;

	public DeliveryTime(DeliveryTime deliveryTime) {
		this.startTime = deliveryTime.startTime;
		this.completionTime = deliveryTime.completionTime;
	}

	public DeliveryTime(DateTime time) {
		if (time == null)
			throw new IllegalArgumentException("Start time cannot be null.");
		startTime = time;
	}

	public void setCompletionTime(DateTime completionTime) {
		if (completionTime == null || completionTime.isBefore(startTime))
			throw new IllegalArgumentException("Invalid completion time: " + completionTime);
		this.completionTime = completionTime;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public DateTime getCompletionTime() {
		return completionTime;
	}

	public void setEstimatedTime(int estimatedTime) {
		if (estimatedTime <= 0)
			throw new IllegalArgumentException("Estimated time cannot be zero or lower.");
		this.estimatedTime = estimatedTime;
	}

	public int getEstimatedTime() {
		return estimatedTime;
	}
}
