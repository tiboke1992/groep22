package be.kuleuven.assemassist.domain;

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

}
