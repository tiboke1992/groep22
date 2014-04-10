package be.kuleuven.assemassist.domain.sorting;

import java.util.List;

import be.kuleuven.assemassist.domain.CarOrder;

public class FifoSort extends SortingAlgorithm{

	public FifoSort(List<CarOrder> pending) {
		super(pending);
	}

	@Override
	public List<CarOrder> sortOrders() {
		return super.getPending();
	}

}
