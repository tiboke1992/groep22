package be.kuleuven.assemassist.domain.carmodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.domain.CarManufacturingCompany;
import be.kuleuven.assemassist.domain.options.CarOption;

public class TextCarModelReader implements CarModelReader {

	private CarManufacturingCompany company;
	private InputStream in;

	public TextCarModelReader(CarManufacturingCompany company, InputStream in) {
		this.in = in;
		this.company = company;
	}

	@Override
	public List<CarModel> readCarModels() throws IOException {
		List<CarModel> models = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] parts = line.split(";");
			List<CarOption> allowedOptions = new ArrayList<>();
			for (int i = 2; i < parts.length; i++) {
				String[] options = parts[i].split("\\|");
				for (int j = 1; j < options.length; j++) {
					try {
						Class<?> enumClass = Class.forName(options[0]);
						Object[] enums = enumClass.getEnumConstants();
						for (Object o : enums) {
							if (((Enum<?>) o).name().equals(options[j]))
								allowedOptions.add((CarOption) o);
						}
					} catch (ClassNotFoundException e) {
						throw new IOException("CarOption type not found: " + options[j]);
					}
				}
			}
			models.add(new CarModel(company, parts[0], Integer.parseInt(parts[1]), new CarModelSpecification(
					allowedOptions)));
		}
		return models;
	}
}
