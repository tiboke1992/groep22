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

	public boolean canAdvance() {
		return true;// TODO REQS
	}

	public WorkStation getLastWorkStation() {
		if (layout.getWorkStations().isEmpty())
			return null;
		return layout.getWorkStations().get(layout.getWorkStations().size() - 1);
	}
}
