package be.kuleuven.assemassist.domain.options;

import static be.kuleuven.assemassist.util.Util.capitalizeFirstCharacter;

/**
 * 
 * This enum represents the different kind of engines on a car
 * 
 */

public enum Engine implements CarOption {

	STANDARD(2, 4), PERFORMANCE(2.5, 6), ULTRA(3, 8);

	private double size;
	private int cilinderCount;

	Engine(double size, int cilinderCount) {
		this.size = size;
		this.cilinderCount = cilinderCount;
	}

	public double getSize() {
		return size;
	}

	public int getCilinderCount() {
		return cilinderCount;
	}

	@Override
	public String toString() {
		return capitalizeFirstCharacter(name()) + " " + size + "l v"
				+ cilinderCount;
	}
}
