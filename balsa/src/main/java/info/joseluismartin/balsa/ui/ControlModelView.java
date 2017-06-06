package info.joseluismartin.balsa.ui;

import info.joseluismartin.balsa.model.ControlModel;

import javax.annotation.PostConstruct;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdal.swing.AbstractView;
import org.jdal.swing.form.BoxFormBuilder;

public class ControlModelView extends AbstractView<ControlModel> implements ChangeListener {
	
	private PIDView control;
	private GenericLtiSystemView g;
	private JCheckBox closedBucle = new JCheckBox();
	
	
	public ControlModelView() {
		super(new ControlModel());
	}

	public ControlModelView(ControlModel model) {
		super(model);
	}

	@PostConstruct
	public void init() {
		autobind();
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected JComponent buildPanel() {
		BoxFormBuilder fb = new BoxFormBuilder();
		fb.row(150);
		fb.add(this.control.getPanel());
		fb.row(100);
		fb.add(this.g.getPanel());
		fb.row();
		fb.startBox();
		fb.row();
		fb.add("Closed Bucle", this.closedBucle);
		fb.endBox();
		
		return fb.getForm();
	}
	
	@Override
	protected void doUpdate() {
		this.control.update();
		this.g.update();
	}
	
	public PIDView getControl() {
		return control;
	}

	public void setControl(PIDView control) {
		this.control = control;
	}

	public GenericLtiSystemView getG() {
		return g;
	}

	public void setG(GenericLtiSystemView g) {
		this.g = g;
	}

}
