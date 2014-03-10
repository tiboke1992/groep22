package be.kuleuven.assemassist.domain.options;

public enum Wheels {

	COMFORT, SPORTS;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
