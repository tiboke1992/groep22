package be.kuleuven.assemassist.domain;
import be.kuleuven.assemassist.domain.task.*;

public class AccessoriesPost extends WorkStation {

	private AssemblyTask assemblyTask;
	
	public AccessoriesPost(){
		assemblyTask = new AssemblyTask();
		assemblyTask.add(new InstallSeats());
		assemblyTask.add(new MountWheels());
	}
	
	@Override
	public String toString() {
		return "AccessoriesPost";
	}

	public AssemblyTask getAssemblyTask() {
		return assemblyTask;
	}

	public void setAssemblyTask(AssemblyTask assemblyTask) {
		this.assemblyTask = assemblyTask;
	}
	
	

}
