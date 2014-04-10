package be.kuleuven.assemassist.domain;

import be.kuleuven.assemassist.util.Util;

/**
 * 
 * This class serves as a super class for all possible assembly tasks. It has a
 * list of actions that need to be performed for the task to be completed.
 * Classes that require these lists will receive a copy of the list. A queue is
 * used to represent these actions because they need to be performed in a
 * certain order.
 * 
 */

public enum AssemblyTask {

	INSERT_ENGINE, INSERT_GEARBOX, INSTALL_SEATS, MOUNT_WHEELS, INSTALL_SPOILER;

	public String toString() {
		return Util.capitalizeFirstCharacter(name().replaceAll("_", " "));
	}
}
