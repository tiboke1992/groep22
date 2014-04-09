package be.kuleuven.assemassist.domain.task;

import be.kuleuven.assemassist.domain.task.action.DummyAction;

/**
 * 
 * This class represents the insertion of the gear box
 * 
 */
public class InsertGearBox extends AssemblyTask {

	public InsertGearBox(){
		addAction(new DummyAction("Take gear box"));
	}
	
	@Override
	public String toString() {
		return "Insert Gearbox";
	}

}
