package be.kuleuven.assemassist.domain.sorting;

public enum SupportedSortingAlgorithms {
	FIFO(1),
	BATCH(2);
	
	private int code;
	
	private SupportedSortingAlgorithms(int code){
		this.code = code;
	}
	
	public int getCode(){
		return this.code;
	}
}
