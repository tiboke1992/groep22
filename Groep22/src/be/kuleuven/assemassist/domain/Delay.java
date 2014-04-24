package be.kuleuven.assemassist.domain;

import org.joda.time.DateTime;

public class Delay {

	private int minutesDelay;
	private DateTime time;
	private CarOrder order;

	public Delay(int minutesDelay, DateTime time, CarOrder order) {
		this.minutesDelay = minutesDelay;
		this.time = time;
		this.order = order;
	}

	public int getMinutesDelay() {
		return minutesDelay;
	}

	public DateTime getTime() {
		return time;
	}

	public CarOrder getOrder() {
		return order;
	}
}
