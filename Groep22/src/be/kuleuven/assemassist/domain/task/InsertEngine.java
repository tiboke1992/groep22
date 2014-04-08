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
	}

	@Override
	public String toString() {
		return "Insert Engine";
	}

}
