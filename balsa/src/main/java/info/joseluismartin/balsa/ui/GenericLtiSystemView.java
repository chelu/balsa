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

import info.joseluismartin.balsa.model.GenericLtiSystem;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jdal.swing.AbstractView;
import org.jdal.swing.TitledSeparator;
import org.jdal.swing.form.BoxFormBuilder;
import org.springframework.stereotype.Component;


/**
 * View for Generic LTI Systems
 * 
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
@Component
public class GenericLtiSystemView extends AbstractView<GenericLtiSystem> {
	
	private JTextArea transferFunction = new JTextArea();
	
	public GenericLtiSystemView() {
		this(new GenericLtiSystem());
	}

	public GenericLtiSystemView(GenericLtiSystem model) {
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
		fb.add(new TitledSeparator(getMessage("TransferFunction")));
		fb.row(Short.MAX_VALUE);
		fb.add(new JScrollPane(transferFunction));
		
		return fb.getForm();
	}

}
