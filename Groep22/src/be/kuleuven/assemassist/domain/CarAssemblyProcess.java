package be.kuleuven.assemassist.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import be.kuleuven.assemassist.domain.task.AssemblyTask;

/**
 * 
 * This class keeps track of all the assembly tasks 
 *
 */
public class CarAssemblyProcess {

	private Queue<AssemblyTask> tasks;
	private Map<AssemblyTask, Boolean> taskStatus;

	public CarAssemblyProcess() {
		tasks = new LinkedList<>();
		taskStatus = new HashMap<>();
	}

	public Queue<AssemblyTask> getAllTasks() {
		Queue<AssemblyTask> copy = new LinkedList<>();
		for (AssemblyTask task : tasks)
			copy.add(task);
		return copy;
	}

	public List<AssemblyTask> getPendingTasks() {
		List<AssemblyTask> copy = new LinkedList<>();
		for (AssemblyTask task : tasks)
			if (!taskStatus.get(task))
				copy.add(task);
		return copy;
	}

	public void completeTask(AssemblyTask task) {
		if (!taskStatus.keySet().contains(task))
			throw new IllegalArgumentException("The task " + task + " does not belong to this assembly process.");
		taskStatus.put(task, true);
	}

	public void resetProcess() {
		for (AssemblyTask task : taskStatus.keySet())
			taskStatus.put(task, false);
	}

	public void addTask(AssemblyTask assemblyTask) {
		tasks.add(assemblyTask);
		taskStatus.put(assemblyTask, false);
	}
}
