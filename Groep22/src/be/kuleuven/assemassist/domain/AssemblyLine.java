package be.kuleuven.assemassist.domain;


public class AssemblyLine {

	private Layout layout;

	public AssemblyLine(Layout layout) {
		this.layout = layout;
	}

	public Layout getLayout() {
		return layout;
	}
}
