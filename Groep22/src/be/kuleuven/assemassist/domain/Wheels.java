package be.kuleuven.assemassist.domain;

public enum Wheels {

	COMFORT, SPORTS;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
