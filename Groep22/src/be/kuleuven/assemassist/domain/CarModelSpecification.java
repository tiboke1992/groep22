package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.domain.options.CarOption;

public class CarModelSpecification {

	/*
	 * / instantiates CarOrder
	 */

	private Car car;

	public CarModelSpecification(Car car) {
		setCar(car);
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public boolean canHaveAsOption(CarOption option) {
		return true;
	}

	public <T> List<T> filterOutInvalidOptions(CarOption[] options, Class<T> c) {
		List<T> caroptions = new ArrayList<>();
		for (CarOption co : options)
			if (canHaveAsOption(co))
				caroptions.add((T) co);
		return caroptions;
	}
}
