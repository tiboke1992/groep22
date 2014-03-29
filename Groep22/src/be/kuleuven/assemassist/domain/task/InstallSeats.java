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
		addAction(new DummyAction("Place seats in car"));
		addAction(new DummyAction("Test if the are confy"));
	}

	@Override
	public String toString() {
		return "Install Seats";
	}

}
