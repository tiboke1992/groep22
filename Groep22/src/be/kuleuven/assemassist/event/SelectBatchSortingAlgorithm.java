package be.kuleuven.assemassist.event;

import java.util.List;

import be.kuleuven.assemassist.domain.options.CarOption;

public class SelectBatchSortingAlgorithm implements Event {
	private final List<CarOption> carOptions;

	public SelectBatchSortingAlgorithm(List<CarOption> carOptions) {
		this.carOptions = carOptions;
	}

	public List<CarOption> getCarOptions() {
		return this.carOptions;
	}
}
