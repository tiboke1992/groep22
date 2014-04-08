package be.kuleuven.assemassist.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.options.Engine;
import be.kuleuven.assemassist.domain.options.Wheels;

public class CarModelSpecificationTest {

	@Test
	public void testCanHaveAsOption_OptionNotInList_ReturnsFalse() {
		CarModelSpecification spec = new CarModelSpecification(new ArrayList<CarOption>());
		assertFalse(spec.canHaveAsOption(Engine.PERFORMANCE));
	}

	@Test
	public void testCanHaveAsOption_OptionInList_ReturnsTrue() {
		List<CarOption> list = new ArrayList<CarOption>();
		list.add(Engine.PERFORMANCE);
		CarModelSpecification spec = new CarModelSpecification(list);
		assertTrue(spec.canHaveAsOption(Engine.PERFORMANCE));
	}

	@Test
	public void testFilterOutInvalidOptions_EmptyArray_ReturnsEmptyList() {
		CarModelSpecification spec = new CarModelSpecification(new ArrayList<CarOption>());
		assertTrue(spec.filterOutInvalidOptions(new CarOption[0], Engine.class).isEmpty());
	}

	@Test
	public void testFilterOutInvalidOptions_FiltersOutInvalidOptions() {
		List<CarOption> list = new ArrayList<CarOption>();
		list.add(Engine.PERFORMANCE);
		CarModelSpecification spec = new CarModelSpecification(list);
		assertTrue(spec.filterOutInvalidOptions(new CarOption[] { Engine.STANDARD, Wheels.COMFORT }, Engine.class)
				.isEmpty());
	}
}
