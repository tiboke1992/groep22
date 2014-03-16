package be.kuleuven.assemassist.domain.task;

import be.kuleuven.assemassist.domain.task.action.DummyAction;

/**
 * 
 * This class represents the mounting of the wheels
 * 
 */
public class MountWheels extends AssemblyTask {

	public MountWheels(){
		add(new DummyAction("Take wheels"));
		add(new DummyAction("Put wheels on car"));
		add(new DummyAction("Tighten the bolts"));
	}
	@Override
	public String toString() {
		return "Mount Wheels";
	}

}
