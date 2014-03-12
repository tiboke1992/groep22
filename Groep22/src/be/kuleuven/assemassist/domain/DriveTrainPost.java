package be.kuleuven.assemassist.domain;

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

}
