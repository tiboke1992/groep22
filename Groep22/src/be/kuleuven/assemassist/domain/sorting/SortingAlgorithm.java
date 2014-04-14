package be.kuleuven.assemassist.domain.sorting;

import java.util.List;

import be.kuleuven.assemassist.domain.CarOrder;

public abstract class SortingAlgorithm {
	
	private List<CarOrder> pending;
	
	public SortingAlgorithm(){
		
	}

	public SortingAlgorithm(List<CarOrder> pending){
		this.setPending(pending);
	}

	public List<CarOrder> getPending() {
		return pending;
	}

	public void setPending(List<CarOrder> pending) {
		this.pending = pending;
	}
	
	public abstract List<CarOrder> sortOrders();
	
}
