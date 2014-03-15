package be.kuleuven.assemassist.domain.workpost;

import be.kuleuven.assemassist.domain.CarAssemblyProcess;
import be.kuleuven.assemassist.domain.task.InsertEngine;
import be.kuleuven.assemassist.domain.task.InsertGearBox;

/**
 * This class is the Drivetrain Post of the assembly Process
 */

public class DriveTrainPost extends WorkStation {
	/**
	 * This constructor creates a new CarAssemblyProcess and adds the tasks
	 * which are needed to complete this process
	 */
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
