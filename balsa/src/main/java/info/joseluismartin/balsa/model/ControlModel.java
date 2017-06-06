package info.joseluismartin.balsa.model;

public class ControlModel implements LtiSystem {
	
	private LtiSystem control = new PID();
	private LtiSystem g;
	private boolean closedBucle = true;

	@Override
	public String getTransferFunction() {
		if (closedBucle) {
			return getKG() + "/(1 + " + getKG() + ")";
		}
		else {
			return getKG();
		}
	}

	private String getKG() {
		return "(" + control.getTransferFunction() + ") * (" + 
				g.getTransferFunction() + ")";
	}

	@Override
	public String getName() {
		return "Control Model";
	}
	
	public LtiSystem getControl() {
		return control;
	}

	public void setControl(LtiSystem control) {
		this.control = control;
	}

	public LtiSystem getG() {
		return g;
	}

	public void setG(LtiSystem g) {
		this.g = g;
	}

	public boolean isClosedBucle() {
		return closedBucle;
	}

	public void setClosedBucle(boolean closedBucle) {
		this.closedBucle = closedBucle;
	}
}
