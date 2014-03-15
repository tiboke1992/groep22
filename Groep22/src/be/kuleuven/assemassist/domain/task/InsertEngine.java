package be.kuleuven.assemassist.domain.task;

import be.kuleuven.assemassist.domain.task.action.DummyAction;

/**
 * 
 * This class represents the insertion of the engine
 * 
 */

public class InsertEngine extends AssemblyTask {

	public InsertEngine() {
		add(new DummyAction("Take engine"));
		add(new DummyAction("Put engine in car"));
		add(new DummyAction("Tighten the engine bolts"));
	}

	@Override
	public String toString() {
		return "Insert Engine";
	}

}
