package be.kuleuven.assemassist.domain;

import java.util.ArrayList;
import java.util.List;

public class WorkStation {

	public static List<WorkStation> getWorkStations() {
		List<WorkStation> workStations = new ArrayList<>();
		workStations.add(new DriveTrainPost());
		workStations.add(new AccessoriesPost());
		return workStations;
	}

	public Car getCurrentCar() {
		return null;// TODO
	}
}
