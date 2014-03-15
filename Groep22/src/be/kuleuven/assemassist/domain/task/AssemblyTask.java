package be.kuleuven.assemassist.domain.task;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import be.kuleuven.assemassist.domain.task.action.Action;

public abstract class AssemblyTask {

	private final List<Action> actions;
	private Queue<Action> pendingActions;

	public AssemblyTask() {
		actions = new LinkedList<Action>();
		pendingActions = new LinkedList<>();
	}

	public Queue<Action> getAllActions() {
		Queue<Action> copy = new LinkedList<>();
		for (Action action : actions)
			copy.add(action);
		return copy;
	}

	public List<Action> getPendingActions() {
		List<Action> copy = new LinkedList<>();
		for (Action action : pendingActions)
			copy.add(action);
		return copy;
	}

	public void completeAction() {
		pendingActions.poll();
	}

	public void resetTask() {
		pendingActions.clear();
		for (Action a : actions)
			pendingActions.add(a);
	}

	public void add(Action action) {
		if (action == null)
			throw new IllegalArgumentException("Null action not allowed");
		actions.add(action);
		pendingActions.add(action);
	}

	public abstract String toString();
}
