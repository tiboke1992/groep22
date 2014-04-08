package be.kuleuven.assemassist.event;

public class WorkStationSelectionEvent implements Event {

	private int workStationId;

	public WorkStationSelectionEvent(int id) {
		this.workStationId = id;
	}

	public int getWorkStationId() {
		return workStationId;
	}
}
