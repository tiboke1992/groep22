package be.kuleuven.assemassist.domain.task;

import java.util.LinkedList;
import java.util.Queue;

import be.kuleuven.assemassist.domain.Action;

public class AssemblyTask {

	private Queue<AssemblyTask> actions;

	public AssemblyTask() {
		actions = new LinkedList<AssemblyTask>();
	}

	public Queue<AssemblyTask> getActions() {
		return actions;
	}

	public void setActions(Queue<AssemblyTask> actions) {
		this.actions = actions;
	}

	public void add(AssemblyTask action) {
		actions.add(action);
	}
	
	public String toString(){
		return "";
	}
}
