package be.kuleuven.assemassist.ui;

import static org.junit.Assert.fail;

import java.util.List;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.controller.OrderController;
import be.kuleuven.assemassist.controller.SystemController;
import be.kuleuven.assemassist.controller.WorkStationController;
import be.kuleuven.assemassist.domain.CarModelSpecification;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.role.Role;
import be.kuleuven.assemassist.domain.task.AssemblyTask;
import be.kuleuven.assemassist.domain.task.action.Action;

public class TestUI extends UI {

	public TestUI(SystemController systemController, OrderController orderController,
			WorkStationController workStationController) {
		super(systemController, orderController, workStationController);
	}

	@Override
	public <T extends CarOption> T askCarOption(CarModelSpecification spec, Class<T> carOptionClass) {
		return null;
	}

	@Override
	public void showAssemblyLineAdvanced() {
	}

	@Override
	public void showCanNotAdvanceError() {
	}

	@Override
	public void showDeliveryTime(DateTime time) {
	}

	@Override
	public void showError(Throwable t) {
		t.printStackTrace();
		fail(t.getMessage());
	}

	@Override
	public void showGreeting(Role role) {
	}

	@Override
	public void showLoginOptions() {
	}

	@Override
	public void showManagerMenu() {
	}

	@Override
	public void showMenu() {
	}

	@Override
	public void showOrders() {
	}

	@Override
	public void showOverview(String overview) {
	}

	@Override
	public void showNoCarToWorkOn() {
	}

	@Override
	public void showPendingAssemblyTasks(List<AssemblyTask> tasks) {
	}

	@Override
	public void showTaskCompleted(AssemblyTask task) {
	}

	@Override
	public void showSequence(List<Action> actions) {
	}

	@Override
	public void shutdown() {
	}

	@Override
	public void showWorkPostMenu() {
	}
}
