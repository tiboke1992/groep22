package be.kuleuven.assemassist.domain.workpost;

import be.kuleuven.assemassist.domain.CarAssemblyProcess;
import be.kuleuven.assemassist.domain.ProductionSchedule;
import be.kuleuven.assemassist.domain.task.InstallSeats;
import be.kuleuven.assemassist.domain.task.MountWheels;

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
		CarAssemblyProcess assemblyProcess = new CarAssemblyProcess();
		assemblyProcess.addTask(new InstallSeats());
		assemblyProcess.addTask(new MountWheels());
		setAssemblyProcess(assemblyProcess);
	}

	@Override
	public String toString() {
		return "AccessoriesPost";
	}

}
