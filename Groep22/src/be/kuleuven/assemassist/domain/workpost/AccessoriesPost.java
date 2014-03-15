package be.kuleuven.assemassist.domain.workpost;

import be.kuleuven.assemassist.domain.CarAssemblyProcess;
import be.kuleuven.assemassist.domain.task.InstallSeats;
import be.kuleuven.assemassist.domain.task.MountWheels;

public class AccessoriesPost extends WorkStation {

	public AccessoriesPost() {
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
