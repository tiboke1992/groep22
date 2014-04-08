package be.kuleuven.assemassist.domain.options;

import static be.kuleuven.assemassist.util.Util.capitalizeFirstCharacter;

/**
 * 
 * This enum represents the different kind of wheels on a car
 * 
 */

public enum Wheels implements CarOption {

	COMFORT, SPORTS;

	@Override
	public String toString() {
		return capitalizeFirstCharacter(name());
	}
}
