package be.kuleuven.assemassist.domain.options;

import static be.kuleuven.assemassist.util.Util.capitalizeFirstCharacter;

public enum Gearbox implements CarOption {

	MANUAL(6), AUTOMATIC(5);

	private int amount;

	Gearbox(int amount) {
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return amount + " speed " + capitalizeFirstCharacter(name());
	}
}
