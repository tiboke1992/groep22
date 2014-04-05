package be.kuleuven.assemassist.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import be.kuleuven.assemassist.domain.workpost.AccessoriesPost;
import be.kuleuven.assemassist.domain.workpost.WorkStation;

public class LayoutTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAddWorkStation_nullThrowsIllegalArgumentException() {
		Layout layout = new Layout(new ConveyorBelt());
		layout.addWorkStation(null);
	}

	@Test
	public void testAddWorkStation_addsWorkStation() {
		Layout layout = new Layout(new ConveyorBelt());
		WorkStation workStation = new AccessoriesPost(new ProductionSchedule());
		layout.addWorkStation(workStation);
		assertTrue(layout.getWorkStations().contains(workStation));
	}
}
