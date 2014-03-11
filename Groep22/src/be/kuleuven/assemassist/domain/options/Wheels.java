package be.kuleuven.assemassist.domain.options;

public enum Wheels implements CarOption {

	COMFORT, SPORTS;

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
