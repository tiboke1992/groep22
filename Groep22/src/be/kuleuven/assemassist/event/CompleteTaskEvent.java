package be.kuleuven.assemassist.event;

public class CompleteTaskEvent implements Event {

	private final int timeToComplete;

	public CompleteTaskEvent(int timeToComplete) {
		this.timeToComplete = timeToComplete;
	}

	public int getTimeToComplete() {
		return timeToComplete;
	}
}
