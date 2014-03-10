package be.kuleuven.assemassist.domain;

public enum Gearbox {

	MANUAL(6), AUTOMATIC(5);
	
	private int amount;
	
	Gearbox(int amount){
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	@Override
	public String toString() {
		return amount+" speed "+name().toLowerCase();
	}
}
