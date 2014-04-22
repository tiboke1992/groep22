package be.kuleuven.assemassist;

import org.joda.time.DateTime;

import be.kuleuven.assemassist.domain.ProductionSchedule;

public class TimeManager {

	private DateTime time;

	public TimeManager() {
		time = new DateTime().withFields(ProductionSchedule.START_OF_DAY);
	}

	public DateTime getTime() {
		return time;
	}

	public void addMinutes(int minutes) {
		time = time.plusMinutes(minutes);
	}

	public void nextDay() {
		time = time.plusDays(1);
	}
}
