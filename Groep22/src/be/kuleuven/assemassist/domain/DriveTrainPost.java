package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.Queue;

import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.domain.task.InsertEngine;
import be.kuleuven.assemassist.domain.task.InsertGearBox;

public class DriveTrainPost extends WorkStation {

	private CarAssemblyProcess assemblyProcess;

	public DriveTrainPost() {
		assemblyProcess = new CarAssemblyProcess();
		assemblyProcess.addTask(new InsertEngine());
		assemblyProcess.addTask(new InsertGearBox());
	}

	@Override
	public String toString() {
		return "DriveTrainPost";
	}

	@Override
	public ArrayList<AssemblyTask> getAssemblyTasks() {
		Queue<AssemblyTask> tasks = assemblyProcess.getTasks();
		ArrayList<AssemblyTask> result = new ArrayList<AssemblyTask>(tasks);
		return result;
	}

}
