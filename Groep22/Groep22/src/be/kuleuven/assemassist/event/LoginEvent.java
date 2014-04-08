package be.kuleuven.assemassist.event;

public class LoginEvent implements Event {

	private int roleId;

	public LoginEvent(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleId() {
		return roleId;
	}
}
