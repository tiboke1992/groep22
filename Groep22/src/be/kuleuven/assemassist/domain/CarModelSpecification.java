package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.domain.options.CarOption;

/**
 * 
 * This class represents the specification of the car model. this class contains
 * a list of options that are allowed for that model
 * 
 */

public class CarModelSpecification {

	private List<CarOption> allowedOptions;

	public CarModelSpecification(List<CarOption> allowedOptions) {
		this.allowedOptions = allowedOptions;
	}

	public CarModelSpecification(CarModelSpecification modelSpecification) {
		this.allowedOptions = new ArrayList<>();
		for (CarOption option : modelSpecification.allowedOptions)
			this.allowedOptions.add(option);
	}

	public boolean canHaveAsOption(CarOption option) {
		return allowedOptions.contains(option);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> filterOutInvalidOptions(CarOption[] options, Class<T> c) {
		List<T> caroptions = new ArrayList<>();
		for (CarOption co : options)
			if (canHaveAsOption(co))
				caroptions.add((T) co);
		return caroptions;
	}
}
