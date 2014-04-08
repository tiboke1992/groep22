package be.kuleuven.assemassist.domain.task;

import be.kuleuven.assemassist.domain.task.action.DummyAction;

/**
 * 
 * This class represents the installation of the seats
 * 
 */
public class InstallSeats extends AssemblyTask {
	public InstallSeats(){
		addAction(new DummyAction("Take the seats"));
	}

	@Override
	public String toString() {
		return "Install Seats";
	}

}
