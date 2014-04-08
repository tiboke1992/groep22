package be.kuleuven.assemassist.domain.task.action;
/**
 * 
 * A dummy class for the creation of actions
 *
 */
public class DummyAction extends Action {

	private String desciption;

	public DummyAction() {
		this.setDescription("Dummy action");
	}

	public DummyAction(String description) {
		this.setDescription(description);
	}

	@Override
	public String toString() {
		return this.getDescription();
	}

	private void setDescription(String description) {
		this.desciption = description;
	}

	private String getDescription() {
		return this.desciption;
	}

}
