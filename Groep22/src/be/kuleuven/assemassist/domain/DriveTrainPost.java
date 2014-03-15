package be.kuleuven.assemassist.domain;

import be.kuleuven.assemassist.domain.task.InsertEngine;
import be.kuleuven.assemassist.domain.task.InsertGearBox;

public class DriveTrainPost extends WorkStation {

	public DriveTrainPost() {
		CarAssemblyProcess assemblyProcess = new CarAssemblyProcess();
		assemblyProcess.addTask(new InsertEngine());
		assemblyProcess.addTask(new InsertGearBox());
		setAssemblyProcess(assemblyProcess);
	}

	@Override
	public String toString() {
		return "DriveTrainPost";
	}

}
