package info.joseluismartin.balsa.ui;

import info.joseluismartin.balsa.model.PID;
import info.joseluismartin.balsa.model.PID.Type;

import javax.annotation.PostConstruct;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdal.swing.AbstractView;
import org.jdal.swing.form.BoxFormBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PIDView extends AbstractView<PID> implements ChangeListener {
	
	private JTextField kp = new JTextField();
	private JTextField ti = new JTextField();
	private JTextField td = new JTextField();
	private JLabel transferFunctionLabel = new JLabel();
	private JComboBox<PID.Type> type = new JComboBox<PID.Type>(PID.Type.values());
	
	public PIDView() {
		super(new PID());
	}

	public PIDView(PID model) {
		super(model);
	}
	
	@PostConstruct
	public void init() {
		autobind();
	}

	@Override
	protected JComponent buildPanel() {
		BoxFormBuilder fb = new BoxFormBuilder();
		
		fb.row();
		fb.startBox();
		fb.row();
		fb.add("Kp", this.kp);
		fb.add("Ti", this.ti);
		fb.add("Td", this.td);
		fb.add("Type: ", this.type);
		fb.endBox();
		
		return fb.getForm();
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
