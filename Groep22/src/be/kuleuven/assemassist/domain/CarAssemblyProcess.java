package be.kuleuven.assemassist.domain;

import java.util.LinkedList;
import java.util.Queue;

import be.kuleuven.assemassist.domain.task.AssemblyTask;

public class CarAssemblyProcess {

	private Queue<AssemblyTask> tasks;

	public CarAssemblyProcess() {
		tasks = new LinkedList<>();
	}

	public Queue<AssemblyTask> getTasks() {
		return tasks;
	}

	public void addTask(AssemblyTask assemblyTask) {
		tasks.add(assemblyTask);
	}
}
