package be.kuleuven.assemassist.domain;

public enum Seats {

	LEATHER_BLACK, LEATHER_WHITE, VINYL_GREY;
	
	@Override
	public String toString() {
		return name().toLowerCase().replace("_", " ");
	}
	
}
