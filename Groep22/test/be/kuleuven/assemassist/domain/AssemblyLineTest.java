package be.kuleuven.assemassist.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import be.kuleuven.assemassist.domain.carmodel.CarModelSpecification;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.domain.workpost.AccessoriesPost;
import be.kuleuven.assemassist.domain.workpost.DriveTrainPost;
import be.kuleuven.assemassist.domain.workpost.WorkStation;

public class AssemblyLineTest {

	private AssemblyLine assemblyLine;

	@Before
	public void setup() {
		assemblyLine = new AssemblyLine(new Layout(new ConveyorBelt()));
	}

	@Test
	public void testGetLastWorkStation_NoWorkStations_ReturnsNull() {
		assertNull(assemblyLine.getLastWorkStation());
	}

	@Test
	public void testGetLastWorkStation_ReturnsLastWorkStation() {
		WorkStation ws = new DriveTrainPost(new ProductionSchedule());
		assemblyLine.getLayout().addWorkStation(ws);
		assertEquals(ws, assemblyLine.getLastWorkStation());
	}

	@Test
	public void testIsCarLeftAtWorkStation_CarOnWorkStation_ReturnsTrue() {
		WorkStation ws = new DriveTrainPost(new ProductionSchedule());
		ws.setCurrentCarOrder(new CarOrder(new CarModelSpecification(new ArrayList<CarOption>())));
		assemblyLine.getLayout().addWorkStation(ws);
		assertTrue(assemblyLine.isCarLeftAtAWorkStation());
	}

	@Test
	public void testIsCarLeftAtWorkStation_NoCarOnWorkStation_ReturnsFalse() {
		WorkStation ws = new DriveTrainPost(new ProductionSchedule());
		assemblyLine.getLayout().addWorkStation(ws);
		assertFalse(assemblyLine.isCarLeftAtAWorkStation());
	}

	@Test
	public void testCanAdvance_NoCarOrdersAtWorkStations_ReturnsTrue() {
		ProductionSchedule schedule = new ProductionSchedule();
		WorkStation driveTrain = new DriveTrainPost(schedule);
		WorkStation accessories = new AccessoriesPost(schedule);
		assemblyLine.getLayout().addWorkStation(driveTrain);
		assemblyLine.getLayout().addWorkStation(accessories);
		assertTrue(assemblyLine.canAdvance());
	}

	@Test
	public void testCanAdvance_CarOrdersWithUncompletedTasks_ReturnsFalse() {
		ProductionSchedule schedule = new ProductionSchedule();
		WorkStation driveTrain = new DriveTrainPost(schedule);
		WorkStation accessories = new AccessoriesPost(schedule);
		driveTrain.setCurrentCarOrder(new CarOrder(new CarModelSpecification(new ArrayList<CarOption>())));
		assemblyLine.getLayout().addWorkStation(driveTrain);
		assemblyLine.getLayout().addWorkStation(accessories);
		assertFalse(assemblyLine.canAdvance());
	}

	@Test
	public void testCanAdvance_CarOrdersWithCompletedTasks_ReturnsTrue() {
		ProductionSchedule schedule = new ProductionSchedule();
		WorkStation driveTrain = new DriveTrainPost(schedule);
		WorkStation accessories = new AccessoriesPost(schedule);
		driveTrain.setCurrentCarOrder(new CarOrder(new CarModelSpecification(new ArrayList<CarOption>())));
		assemblyLine.getLayout().addWorkStation(driveTrain);
		assemblyLine.getLayout().addWorkStation(accessories);
		for (AssemblyTask task : driveTrain.getAssemblyProcess().getPendingTasks())
			driveTrain.getAssemblyProcess().completeTask(task);
		assertTrue(assemblyLine.canAdvance());
	}
}
