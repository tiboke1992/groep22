package be.kuleuven.assemassist.domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.role.Manager;

public class CarManufacturingCompanyTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAddCarModel_NullThrowsIllegalArgumentException() {
		CarManufacturingCompany company = new CarManufacturingCompany("test", new Manager());
		company.addCarModel(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddCarModel_ModelFromOtherCompany_ThrowsIllegalArgumentException() {
		CarManufacturingCompany company = new CarManufacturingCompany("test", new Manager());
		CarModel model = new CarModel(new CarManufacturingCompany("test2", new Manager()), "bla",
				new CarModelSpecification(new ArrayList<CarOption>()));
		company.addCarModel(model);
	}

	@Test
	public void testAddCarModel_addsCarModel() {
		CarManufacturingCompany company = new CarManufacturingCompany("test", new Manager());
		CarModel model = new CarModel(company, "bla", new CarModelSpecification(new ArrayList<CarOption>()));
		company.addCarModel(model);
		assertTrue(company.getAvailableCarModels().contains(model));
	}

}
