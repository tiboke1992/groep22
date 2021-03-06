package be.kuleuven.assemassist.domain;

import be.kuleuven.assemassist.domain.workpost.WorkStation;

public class AssemblyLine {

	private Layout layout;

	public AssemblyLine(Layout layout) {
		this.layout = layout;
	}

	public Layout getLayout() {
		return layout;
	}

	/**
	 * We can advance when all tasks are finished on all workstations
	 * 
	 * @return
	 */
	public boolean canAdvance() {
		boolean result = true;
		for (int i = 0; i < layout.getWorkStations().size() && result; i++) {
			WorkStation workStation = layout.getWorkStations().get(i);
			if (workStation.getCurrentCarOrder() != null
					&& !workStation.getAssemblyProcess().getPendingTasks().isEmpty()) {
				result = false;
			}
		}
		return result;
	}

	public boolean isCarLeftAtAWorkStation() {
		boolean result = false;
		for (int i = 0; i < layout.getWorkStations().size() && !result; i++) {
			WorkStation workstation = layout.getWorkStations().get(i);
			result = workstation.getCurrentCarOrder() != null;
		}
		return result;
	}

	public WorkStation getLastWorkStation() {
		if (layout.getWorkStations().isEmpty())
			return null;
		return layout.getWorkStations().get(layout.getWorkStations().size() - 1);
	}

	public WorkStation getFirstWorkStation() {
		if (layout.getWorkStations().isEmpty())
			return null;
		return layout.getWorkStations().get(0);
	}
}
