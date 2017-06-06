/* 
 * Copyright (C) 2013 Jose Luis Martin
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package info.joseluismartin.balsa.ui;

import info.joseluismartin.balsa.model.FirstOrderSystem;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdal.swing.AbstractView;
import org.jdal.swing.ApplicationContextGuiFactory;
import org.jdal.swing.form.BoxFormBuilder;
import org.jdal.swing.form.FormUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FirstOrderView extends AbstractView<FirstOrderSystem> implements ChangeListener {

	private JTextField k = new JTextField();
	private JTextField tao = new JTextField();
	private JSlider  gainSlider = new JSlider();
	private JSlider taoSlider = new JSlider();
	private JLabel transfer = new JLabel(" ");
	
	public FirstOrderView() {
		this(new FirstOrderSystem());
	}
	
	public FirstOrderView(FirstOrderSystem model) {
		setModel(model);
		refresh();
	}
	
	@PostConstruct
	public void init() {
		gainSlider.addChangeListener(this);
		taoSlider.addChangeListener(this);
		autobind();
		bind(transfer, "transferFunction", true);
	}
	
	@Override
	protected JComponent buildPanel() {
		FormUtils.setBold(transfer);
		BoxFormBuilder fb = new BoxFormBuilder(FormUtils.createTitledBorder(getMessage("FirstOrderSystem")));
		
		fb.row();
		fb.startBox();
		fb.row();
		fb.add(getMessage("TransferFunction"), transfer);
		fb.endBox();
		fb.row();
		fb.startBox();
		fb.row();
		fb.add(getMessage("StaticGain"), k);
		fb.add(getMessage("TimeConstant"), tao);
		fb.endBox();
		fb.row();
		fb.startBox();
		fb.row();
		fb.add(gainSlider);
		fb.add(taoSlider);
		fb.endBox();
		
		return fb.getForm();
	}
	
	public static void main(String[] args) {
		FirstOrderView view = new FirstOrderView();
		
		JFrame f = ApplicationContextGuiFactory.getJFrame();
		view.init();
		f.add(view.getPanel());
		
		f.setVisible(true);
		
	}

	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider) e.getSource();
		
		JTextField field = slider == gainSlider ? k : tao;
		field.setText(String.valueOf(slider.getValue()));
		
		if (!slider.getValueIsAdjusting()) {
			update(); 
			refresh();
				
		}
	}
	
	protected void doRefresh() {
		gainSlider.setValue((int) getModel().getK());
		taoSlider.setValue((int) getModel().getTao());
	}

}
