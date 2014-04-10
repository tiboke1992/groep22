package be.kuleuven.assemassist.ui;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.assemassist.controller.Controller;
import be.kuleuven.assemassist.event.Event;

public abstract class AbstractUI {

	private List<Controller> controllers;

	public AbstractUI() {
		controllers = new ArrayList<>();
	}

	public void addController(Controller c) {
		if (c == null)
			throw new IllegalArgumentException("Null controller");
		controllers.add(c);
	}

	public void pushEvent(Event e) {
		try {
			for (Controller c : controllers)
				c.handleEvent(e);
		} catch (Exception ex) {
			showError(ex);
		}
	}

	public void showError(Throwable t) {
		t.printStackTrace();
		System.out.println("Something went wrong: " + t.getMessage());
		System.out.println("Logging out..");
	}
}
