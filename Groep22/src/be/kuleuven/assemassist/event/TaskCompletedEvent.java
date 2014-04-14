package be.kuleuven.assemassist.event;

public class TaskCompletedEvent implements Event {

	private final int timeToComplete;

	public TaskCompletedEvent(int timeToComplete) {
		this.timeToComplete = timeToComplete;
	}

	public int getTimeToComplete() {
		return timeToComplete;
	}
}
