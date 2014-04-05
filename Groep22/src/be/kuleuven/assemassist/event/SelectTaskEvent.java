package be.kuleuven.assemassist.event;

public class SelectTaskEvent implements Event {

	private int taskId;

	public SelectTaskEvent(int taskId) {
		this.taskId = taskId;
	}

	public int getTaskId() {
		return taskId;
	}
}
