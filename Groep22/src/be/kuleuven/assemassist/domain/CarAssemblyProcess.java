package be.kuleuven.assemassist.domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 
 * This class keeps track of all the assembly tasks
 * 
 */
public class CarAssemblyProcess {

	private Queue<AssemblyTask> tasks;
	private Map<AssemblyTask, Integer> taskStatus;
	private AssemblyLine assemblyLine;

	public CarAssemblyProcess() {
		tasks = new LinkedList<>();
		taskStatus = new HashMap<>();
	}

	public CarAssemblyProcess(CarAssemblyProcess carAssemblyProcess) {
		tasks = new LinkedList<>();
		taskStatus = new HashMap<>();
		for (AssemblyTask t : carAssemblyProcess.tasks)
			tasks.add(t);
		for (AssemblyTask t : carAssemblyProcess.taskStatus.keySet())
			taskStatus.put(t, carAssemblyProcess.taskStatus.get(t));
	}

	// public Queue<AssemblyTask> getAllTasks() {
	// return Collections.unmodifiableCollection(getTasks());
	// }

	public Queue<AssemblyTask> getTasks() {
		return this.tasks;
	}

	public List<AssemblyTask> getPendingTasks() {
		List<AssemblyTask> copy = new LinkedList<>();
		for (AssemblyTask task : tasks)
			if (taskStatus.get(task) == 0)
				copy.add(task);
		return copy;
	}

	public List<AssemblyTask> getCompletedTasks() {
		List<AssemblyTask> copy = new LinkedList<>();
		for (AssemblyTask task : tasks)
			if (taskStatus.get(task) > 0)
				copy.add(task);
		return copy;
	}

	public void completeTask(AssemblyTask task, int time) {
		if (!taskStatus.keySet().contains(task))
			throw new IllegalArgumentException("The task " + task + " does not belong to this assembly process.");
		if (time <= 0)
			throw new IllegalArgumentException("Invalid time in minutes for task completion: " + time);
		taskStatus.put(task, time);
	}

	public int getTotalTimeSpentOnTasks() {
		int total = 0;
		for (Integer i : taskStatus.values()) {
			total += i;
		}
		return total;
	}

	public void resetProcess() {
		for (AssemblyTask task : taskStatus.keySet())
			taskStatus.put(task, 0);
	}

	public void addTask(AssemblyTask assemblyTask) {
		tasks.add(assemblyTask);
		taskStatus.put(assemblyTask, 0);
	}

	public void setAssemblyLine(AssemblyLine assemblyLine) {
		this.assemblyLine = assemblyLine;
	}

	public AssemblyLine getAssemblyLine() {
		return assemblyLine;
	}
}
