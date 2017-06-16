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
import info.joseluismartin.balsa.model.SecondOrderSystem;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdal.swing.AbstractView;
import org.jdal.swing.form.BoxFormBuilder;
import org.jdal.swing.form.FormUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * View for Second Order LTI Systems.
 * 
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
@Component
@Scope("prototype")
public class SecondOrderView extends  AbstractView<SecondOrderSystem> implements ChangeListener {
	
	private JTextField wn = new JTextField();
	private JTextField k = new JTextField();
	private JTextField delta = new JTextField();
	private JLabel transfer = new JLabel();
	private JSlider wnSlider = new JSlider(-100, 100, 1);
	private JSlider kSlider = new JSlider(-100, 100, 1);
	private JSlider deltaSlider = new JSlider(-100, 100, 1);
	
	
	
	public SecondOrderView() {
		this(new SecondOrderSystem());
	}

	/**
	 * @param secondOrderSystem
	 */
	public SecondOrderView(SecondOrderSystem secondOrderSystem) {
		super(secondOrderSystem);
	}
	
	@PostConstruct
	public void init() {
		autobind();
		bind(transfer, "transferFunction", true);
	}

	@Override
	protected JComponent buildPanel() {
		this.wnSlider.addChangeListener(this);
		this.kSlider.addChangeListener(this);
		this.deltaSlider.addChangeListener(this);
		
		FormUtils.setBold(this.transfer);
		BoxFormBuilder fb = new BoxFormBuilder();
		fb.setDebug(false);
		fb.startBox();
		fb.row();
		fb.add(getMessage("TransferFunction"), transfer);
		fb.endBox();
		fb.row();
		fb.startBox();
		fb.row();
		fb.add(getMessage("StaticGain"), k);
		fb.add(kSlider);
		fb.row();
		fb.add(getMessage("Delta"), delta);
		fb.add(deltaSlider);
		fb.row();
		fb.add(getMessage("NormalFrecuency"), wn);
		fb.add(wnSlider);
		fb.endBox();
		
		return fb.getForm();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider) e.getSource();
		
		if (slider == wnSlider) 
			wn.setText(String.valueOf(wnSlider.getValue()));
		else if (slider == kSlider) 
			k.setText(String.valueOf(slider.getValue()));
		else if (slider == deltaSlider)
			delta.setText(String.valueOf(slider.getValue()));
		
		if (slider.getValueIsAdjusting()) {
			update();
			refresh();
		}
	}
	
}
