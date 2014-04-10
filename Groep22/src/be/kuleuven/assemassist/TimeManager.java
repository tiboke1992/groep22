package be.kuleuven.assemassist;

import org.joda.time.DateTime;

public class TimeManager {

	private DateTime time;

	public TimeManager() {
		time = new DateTime().withHourOfDay(6).withMinuteOfHour(0).withSecondOfMinute(0);
	}

	public DateTime getTime() {
		return time;
	}

	public void addMinutes(int minutes) {
		time = time.plusMinutes(minutes);
	}
}
