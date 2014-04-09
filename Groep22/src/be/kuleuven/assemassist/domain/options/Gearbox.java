package be.kuleuven.assemassist.domain.options;

/**
 * 
 * This enum represents the different kind of gear boxes on a car
 * 
 */
public enum Gearbox implements CarOption {

	MANUAL_6(6), AUTOMATIC_5(5), MANUAL_5(5);

	private int amount;

	Gearbox(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		String name = name().toLowerCase();
		int idx = name.indexOf('_');
		return amount + " speed " + name.substring(0, idx);
	}
}
