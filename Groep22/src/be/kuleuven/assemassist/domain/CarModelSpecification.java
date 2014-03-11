package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.domain.options.CarOption;

public abstract class CarModelSpecification {

	public CarModelSpecification() {
	}

	public abstract boolean canHaveAsOption(CarOption option);

	public <T> List<T> filterOutInvalidOptions(CarOption[] options, Class<T> c) {
		List<T> caroptions = new ArrayList<>();
		for (CarOption co : options)
			if (canHaveAsOption(co))
				caroptions.add((T) co);
		return caroptions;
	}
}
