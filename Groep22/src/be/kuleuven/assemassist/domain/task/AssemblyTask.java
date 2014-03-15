package be.kuleuven.assemassist.domain.task;

import java.util.LinkedList;
import java.util.Queue;

import be.kuleuven.assemassist.domain.task.action.Action;

public abstract class AssemblyTask {

	private Queue<Action> actions;

	public AssemblyTask() {
		actions = new LinkedList<Action>();
	}

	public Queue<Action> getActions() {
		Queue<Action> copy = new LinkedList<>();
		for (Action a : actions)
			copy.add(a);
		return copy;
	}

	public void add(Action action) {
		if (action == null)
			throw new IllegalArgumentException("Null action not allowed");
		actions.add(action);
	}

	public abstract String toString();

	public boolean isDone() {
		return false;
	}
}
