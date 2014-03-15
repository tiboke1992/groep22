package be.kuleuven.assemassist.domain.task;

import be.kuleuven.assemassist.domain.task.action.DummyAction;

public class InsertEngine extends AssemblyTask {

	public InsertEngine() {
		add(new DummyAction());
		add(new DummyAction());
		add(new DummyAction());
	}

	@Override
	public String toString() {
		return "Insert Engine";
	}

}
