package be.kuleuven.assemassist.domain.task;

import be.kuleuven.assemassist.domain.task.action.DummyAction;

/**
 * 
 * This class represents the insertion of the engine
 * 
 */

public class InsertEngine extends AssemblyTask {

	public InsertEngine() {
		addAction(new DummyAction("Take engine"));
		addAction(new DummyAction("Put engine in car"));
		addAction(new DummyAction("Tighten the engine bolts"));
	}

	@Override
	public String toString() {
		return "Insert Engine";
	}

}
