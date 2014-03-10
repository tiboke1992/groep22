package be.kuleuven.assemassist.domain;

public enum Engine {

	STANDARD(21, 4), PERFORMANCE(2.51, 6);
	
	private double size;
	private int cilinderCount;
	
	Engine(double size, int cilinderCount){
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
		return name().toLowerCase()+" "+size+" "+cilinderCount+" cilinders";
	}
}
