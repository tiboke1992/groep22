package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.domain.task.InstallSeats;
import be.kuleuven.assemassist.domain.task.MountWheels;

public class AccessoriesPost extends WorkStation {

	private CarAssemblyProcess assemblyProcess;

	public AccessoriesPost() {
		assemblyProcess = new CarAssemblyProcess();
		assemblyProcess.addTask(new InstallSeats());
		assemblyProcess.addTask(new MountWheels());
	}
	


	@Override
	public String toString() {
		return "AccessoriesPost";
	}



	@Override
	public ArrayList<AssemblyTask> getAssemblyTasks() {
		Queue<AssemblyTask> tasks = assemblyProcess.getTasks();
		ArrayList<AssemblyTask> result = new ArrayList<AssemblyTask>(tasks);
		return result;
	}

}
