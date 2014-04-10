package be.kuleuven.assemassist.domain.sorting;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.domain.CarOrder;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Gearbox;
import be.kuleuven.assemassist.domain.options.Seats;
import be.kuleuven.assemassist.domain.options.Spoiler;
import be.kuleuven.assemassist.domain.options.Wheels;

public class BatchSort extends SortingAlgorithm {

	private List<CarOption> options;

	public BatchSort(List<CarOrder> pending) {
		super(pending);
	}

	public BatchSort(List<CarOrder> pending, List<CarOption> options) {
		super(pending);
		setOptions(options);
	}

	@Override
	public List<CarOrder> sortOrders() {
		if (options == null)
			return super.getPending();

		List<CarOrder> front = getSuitableCarOrders();

		for (CarOrder o : front)
			super.getPending().remove(o);

		front.addAll(super.getPending());
		return front;
	}

	private List<CarOrder> getSuitableCarOrders() {
		List<CarOrder> front = new ArrayList<CarOrder>();

		for (CarOrder order : super.getPending()) {
			boolean hasAllOptions = true;
			for (int i = 0; i < this.getOptions().size() && hasAllOptions; i++) {
				CarOption option = this.getOptions().get(i);
				if (option instanceof Engine)
					hasAllOptions = order.getEngine() == option;
				else if (option instanceof Gearbox)
					hasAllOptions = order.getGearbox() == option;
				else if (option instanceof Seats)
					hasAllOptions = order.getSeats() == option;
				else if (option instanceof Spoiler)
					hasAllOptions = order.getSpoiler() == option;
				else if (option instanceof Wheels)
					hasAllOptions = order.getWheels() == option;
			}
			if (hasAllOptions) {
				front.add(order);
			}

		}
		return front;
	}

	public List<CarOption> getOptions() {
		return options;
	}

	private void setOptions(List<CarOption> options) {
		this.options = options;
	}

}
