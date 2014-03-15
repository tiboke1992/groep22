package be.kuleuven.assemassist.domain.options;

import static be.kuleuven.assemassist.util.Util.capitalizeFirstCharacter;

/**
 * 
 * This enum represents the different kind of seats on a car
 * 
 */
public enum Seats implements CarOption {

	LEATHER_BLACK, LEATHER_WHITE, VINYL_GREY;

	@Override
	public String toString() {
		return capitalizeFirstCharacter(name().replace("_", " "));
	}

}
