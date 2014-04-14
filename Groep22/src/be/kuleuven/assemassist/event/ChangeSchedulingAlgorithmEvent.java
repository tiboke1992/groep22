package be.kuleuven.assemassist.event;

import be.kuleuven.assemassist.domain.sorting.SupportedSortingAlgorithms;

public class ChangeSchedulingAlgorithmEvent implements Event {
	private final SupportedSortingAlgorithms sorting;
	
	public ChangeSchedulingAlgorithmEvent(SupportedSortingAlgorithms algorithm){
		this.sorting = algorithm;
	}
	
	public SupportedSortingAlgorithms getAlgorithm(){
		return this.sorting;
	}
}
