package be.kuleuven.assemassist.domain.task;

import be.kuleuven.assemassist.domain.task.action.DummyAction;

/**
 * 
 * This class represents the insertion of the gear box
 * 
 */
public class InsertGearBox extends AssemblyTask {

	public InsertGearBox(){
		add(new DummyAction("Take gear box"));
		add(new DummyAction("Put gear box in car"));
	}
	
	@Override
	public String toString() {
		return "Insert Gearbox";
	}

}
