package be.kuleuven.assemassist.ui;

import static org.junit.Assert.fail;

import java.util.List;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.controller.OrderController;
import be.kuleuven.assemassist.controller.SystemController;
import be.kuleuven.assemassist.controller.WorkStationController;
import be.kuleuven.assemassist.domain.AssemblyTask;
import be.kuleuven.assemassist.domain.carmodel.CarModelSpecification;
import be.kuleuven.assemassist.domain.options.CarOption;
import be.kuleuven.assemassist.domain.role.CarMechanic;
import be.kuleuven.assemassist.domain.workpost.WorkStation;

public class TestUI extends UI {

	public TestUI(SystemController systemController, OrderController orderController,
			WorkStationController workStationController) {
		addController(systemController);
		addController(orderController);
		addController(workStationController);
	}

	@Override
	public <T extends CarOption> T askCarOption(CarModelSpecification spec, Class<T> carOptionClass) {
		return null;
	}

	@Override
	public void showAssemblyLineAdvanced() {
	}

	@Override
	public void onOrderCompleted(DateTime time) {
	}

	@Override
	public void showError(Throwable t) {
		t.printStackTrace();
		fail(t.getMessage());
	}

	@Override
	public void showGreeting(String role) {
	}

	@Override
	public void showLoginOptions() {
	}

	@Override
	public void showManagerMenu() {
	}

	@Override
	public void showGarageHolderMenu() {
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
	public void showHandleTask(CarMechanic mec) {
	}

	@Override
	public void shutdown() {
	}

	@Override
	public void showWorkPostMenu(List<WorkStation> l) {
	}
}
