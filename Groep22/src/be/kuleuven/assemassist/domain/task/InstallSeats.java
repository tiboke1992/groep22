package be.kuleuven.assemassist.domain.task;

import be.kuleuven.assemassist.domain.task.action.DummyAction;

/**
 * 
 * This class represents the installation of the seats
 * 
 */
public class InstallSeats extends AssemblyTask {
	public InstallSeats(){
		add(new DummyAction("Take the seats"));
		add(new DummyAction("Place seats in car"));
		add(new DummyAction("Test if the are confy"));
	}

	@Override
	public String toString() {
		return "Install Seats";
	}

}
