package be.kuleuven.assemassist.domain.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import be.kuleuven.assemassist.domain.task.action.DummyAction;

public class AssemblyTaskTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAddAction_NullAction_ThrowsIllegalArgumentException() {
		AssemblyTask task = new InsertEngine();
		task.addAction(null);
	}

	@Test
	public void testAddAction_addsActionToActionsAndPendingActions() {
		AssemblyTask task = new InsertEngine();
		int sizeAll = task.getAllActions().size();
		int sizePending = task.getPendingActions().size();
		task.addAction(new DummyAction());
		assertEquals(sizeAll + 1, task.getAllActions().size());
		assertEquals(sizePending + 1, task.getPendingActions().size());
	}

	@Test
	public void testResetTask_PendingActionsContainAllActions() {
		AssemblyTask task = new InsertEngine();
		int size = task.getPendingActions().size();
		task.completeAction();
		task.completeAction();
		task.completeAction();
		assertTrue(task.getPendingActions().isEmpty());
		task.resetTask();
		assertEquals(size, task.getPendingActions().size());
	}

	@Test
	public void testCompleteAction_RemovesActionFromPendingActions() {
		AssemblyTask task = new InsertEngine();
		int size = task.getPendingActions().size();
		task.completeAction();
		assertEquals(size - 1, task.getPendingActions().size());
	}
}
