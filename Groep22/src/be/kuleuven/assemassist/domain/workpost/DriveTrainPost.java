package be.kuleuven.assemassist.domain.workpost;

import be.kuleuven.assemassist.domain.AssemblyTask;
import be.kuleuven.assemassist.domain.CarAssemblyProcess;
import be.kuleuven.assemassist.domain.ProductionSchedule;

/**
 * This class is the Drivetrain Post of the assembly Process
 */

public class DriveTrainPost extends WorkStation {
	/**
	 * This constructor creates a new CarAssemblyProcess and adds the tasks
	 * which are needed to complete this process
	 */
	public DriveTrainPost(ProductionSchedule schedule) {
		super(schedule);
		init();
	}

	@Override
	public String toString() {
		return "DriveTrainPost";
	}

	@Override
	public void init() {
		CarAssemblyProcess assemblyProcess = new CarAssemblyProcess();
		assemblyProcess.addTask(AssemblyTask.INSERT_ENGINE);
		assemblyProcess.addTask(AssemblyTask.INSERT_GEARBOX);
		setAssemblyProcess(assemblyProcess);
	}

}
