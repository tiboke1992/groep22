package be.kuleuven.assemassist.domain.options;

import static be.kuleuven.assemassist.util.Util.capitalizeFirstCharacter;

public enum Seats implements CarOption {

	LEATHER_BLACK, LEATHER_WHITE, VINYL_GREY;

	@Override
	public String toString() {
		return capitalizeFirstCharacter(name().replace("_", " "));
	}

}
