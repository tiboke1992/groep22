package be.kuleuven.assemassist.domain.task;

import java.util.LinkedList;
import java.util.Queue;

import be.kuleuven.assemassist.domain.Action;

public class AssemblyTask {

	private Queue<Action> actions;

	public AssemblyTask() {
		actions = new LinkedList<Action>();
	}

	public Queue<Action> getActions() {
		return actions;
	}

	public void setActions(Queue<Action> actions) {
		this.actions = actions;
	}

	public void add(Action action) {
		actions.add(action);
	}

	public String toString() {
		return "";
	}

	public boolean isDone() {
		return false;
	}
}
