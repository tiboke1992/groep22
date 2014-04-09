package be.kuleuven.assemassist.domain.task;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import be.kuleuven.assemassist.domain.task.action.Action;

/**
 * 
 * This class serves as a super class for all possible assembly tasks. It has a
 * list of actions that need to be performed for the task to be completed.
 * Classes that require these lists will receive a copy of the list. A queue is
 * used to represent these actions because they need to be performed in a
 * certain order.
 * 
 */

public abstract class AssemblyTask {

	private final List<Action> actions;
	private Queue<Action> pendingActions;

	public AssemblyTask() {
		actions = new LinkedList<>();
		pendingActions = new LinkedList<>();
	}

	/**
	 * Returns a copy of the actions list
	 * 
	 * 
	 * @return A LinkedList of all actions
	 */
	public Queue<Action> getAllActions() {
		Queue<Action> copy = new LinkedList<>();
		for (Action action : actions)
			copy.add(action);
		return copy;
	}

	/**
	 * Returns a copy of the remaining actions list
	 * 
	 * @return A LinkedList of the remaining actions
	 */
	public List<Action> getPendingActions() {
		List<Action> copy = new LinkedList<>();
		for (Action action : pendingActions)
			copy.add(action);
		return copy;
	}

	public void completeAction() {
		pendingActions.poll();
	}

	/**
	 * Restores the pending actions to the initial actions
	 */
	public void resetTask() {
		pendingActions.clear();
		for (Action a : actions)
			pendingActions.add(a);
	}

	/**
	 * Adds a action to the action list
	 * 
	 * @param action
	 *            The action to add to the list
	 */
	public void addAction(Action action) {
		if (action == null)
			throw new IllegalArgumentException("Null action not allowed");
		actions.add(action);
		pendingActions.add(action);
	}

	public abstract String toString();
}
