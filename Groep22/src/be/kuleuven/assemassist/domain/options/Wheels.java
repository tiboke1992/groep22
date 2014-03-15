package be.kuleuven.assemassist.domain.options;

import static be.kuleuven.assemassist.util.Util.capitalizeFirstCharacter;

public enum Wheels implements CarOption {

	COMFORT, SPORTS;

	@Override
	public String toString() {
		return capitalizeFirstCharacter(name());
	}
}
