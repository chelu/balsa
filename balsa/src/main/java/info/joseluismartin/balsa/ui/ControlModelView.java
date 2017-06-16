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
