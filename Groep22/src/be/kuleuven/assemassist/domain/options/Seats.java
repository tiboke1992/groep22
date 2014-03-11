package be.kuleuven.assemassist.domain.options;

public enum Seats implements CarOption {

	LEATHER_BLACK, LEATHER_WHITE, VINYL_GREY;

	@Override
	public String toString() {
		return name().toLowerCase().replace("_", " ");
	}

}
