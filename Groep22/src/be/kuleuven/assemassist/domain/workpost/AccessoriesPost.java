package be.kuleuven.assemassist.domain.workpost;

import be.kuleuven.assemassist.domain.AssemblyTask;
import be.kuleuven.assemassist.domain.CarAssemblyProcess;
import be.kuleuven.assemassist.domain.ProductionSchedule;

/**
 * This class is the Accessories Post of the assembly process.
 */

public class AccessoriesPost extends WorkStation {

	/**
	 * This constructor creates a new CarAssemblyProcess and adds the tasks
	 * which are needed to complete this process
	 */
	public AccessoriesPost(ProductionSchedule schedule) {
		super(schedule);
		init();
	}

	@Override
	public String toString() {
		return "AccessoriesPost";
	}

	@Override
	public void init() {
		CarAssemblyProcess assemblyProcess = new CarAssemblyProcess();
		assemblyProcess.addTask(AssemblyTask.INSTALL_SEATS);
		assemblyProcess.addTask(AssemblyTask.MOUNT_WHEELS);
		assemblyProcess.addTask(AssemblyTask.INSTALL_SPOILER);
		setAssemblyProcess(assemblyProcess);
	}

}
