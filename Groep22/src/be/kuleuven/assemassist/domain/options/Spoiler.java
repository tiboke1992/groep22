package be.kuleuven.assemassist.domain.options;

import static be.kuleuven.assemassist.util.Util.capitalizeFirstCharacter;

/**
 * 
 * This enum represents the different kind of engines on a car
 * 
 */

public enum Spoiler implements CarOption {

	NONE, LOW, HIGH;

	@Override
	public String toString() {
		return capitalizeFirstCharacter(name());
	}
}
