package be.kuleuven.assemassist.domain;

import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.domain.task.InsertEngine;
import be.kuleuven.assemassist.domain.task.InsertGearBox;


public class DriveTrainPost extends WorkStation {

	private AssemblyTask assemblyTask;
	
	public DriveTrainPost(){
		assemblyTask = new AssemblyTask();
		assemblyTask.add(new InsertEngine());
		assemblyTask.add(new InsertGearBox());
	}
	
	@Override
	public String toString() {
		return "DriveTrainPost";
	}
	
	@Override
	public AssemblyTask getAssemblyTask() {
		return assemblyTask;
	}



}
