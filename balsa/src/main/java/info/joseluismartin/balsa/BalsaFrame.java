/* 
 * Copyright (C) 2012 Jose Luis Martin
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

package info.joseluismartin.balsa;

import info.joseluismartin.balsa.model.LtiSystem;
import info.joseluismartin.balsa.ui.FirstOrderView;
import info.joseluismartin.balsa.ui.SecondOrderView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdal.beans.AppCtx;
import org.jdal.beans.MessageSourceWrapper;
import org.jdal.swing.ApplicationContextGuiFactory;
import org.jdal.swing.View;
import org.jdal.swing.action.BeanAction;
import org.scilab.modules.graphic_objects.figure.Figure;
import org.scilab.modules.gui.bridge.canvas.SwingScilabCanvas;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
@Component
public class BalsaFrame extends JFrame {

	private static final Log log = LogFactory.getLog(BalsaFrame.class);
	private JSplitPane split;
	private SwingScilabCanvas plot;
	private JTabbedPane systemTab = new JTabbedPane();
	private MessageSourceWrapper messageSource = new MessageSourceWrapper();
	private FirstOrderView fov = new FirstOrderView();
	private SecondOrderView sov = new SecondOrderView();
	private Solver solver = new ScilabSolver();
	@Resource
	private List<View<LtiSystem>> systems;
	private JToolBar toolbar = new JToolBar();

	public BalsaFrame() {

	}

	@PostConstruct
	public void init() {
		build();
	}

	protected void build() {
		Figure figure = ScilabUtils.createFigureWithAxes(1);

		log.info("Figure: [" + figure.getId() + "]");

		plot = ScilabUtils.createPlotCanvas(figure);
		plot.setBackground(Color.WHITE);

		initSystemTab();
		split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, plot, systemTab);
		split.setDividerLocation(0.8d);
		split.repaint();
		this.add(split);

		toolbar.add(new PlotAction());
		toolbar.add(new BodePlotAction());
		toolbar.add(new NisqPlotAction());
		toolbar.add(new EvansPlotAction());
		toolbar.add(new BlackPlotAction());
		this.add(toolbar, BorderLayout.PAGE_START);
	}

	/**
	 * 
	 */
	private void initSystemTab() {
		for (View<LtiSystem> s : systems) {
			systemTab.add(getMessage(s.getModel().getName()), s.getPanel());
		}

	}

	/**
	 * @param name
	 * @return
	 */
	private String getMessage(String code) {
		return messageSource.getMessage(code);
	}


	public void plot() {
		View<LtiSystem> view = getActiveView();
		view.update();
		solver.plot(view.getModel());
	}

	/**
	 * 
	 */
	public void bodePlot() {
		View<LtiSystem> view = getActiveView();
		view.update();
		solver.bodePlot(view.getModel());
	}


	/**
	 * 
	 */
	public void nyquistPlot() {
		View<LtiSystem> view = getActiveView();
		view.update();
		solver.nyquistPlot(view.getModel());
	}

	/**
	 * 
	 */
	private void evansPlot() {
		View<LtiSystem> view = getActiveView();
		view.update();
		solver.evansPlot(view.getModel());

	}

	/**
	 * 
	 */
	public void blackPlot() {
		View<LtiSystem> view = getActiveView();
		view.update();
		solver.blackPlot(view.getModel());
	}


	public MessageSource getMessageSource() {
		return messageSource.getMessageSource();
	}


	public void setMessageSource(MessageSource messageSource) {
		this.messageSource.setMessageSource(messageSource);
	}


	/**
	 * @return active system
	 */
	private View<LtiSystem> getActiveView() {
		return systems.get((systemTab.getSelectedIndex()));
	}

	public static void main(String[] args) {
		ApplicationContextGuiFactory.setPlasticLookAndFeel();
		AppCtx.setConfigPackage("conf");
		BalsaFrame b = AppCtx.getInstance().getBean(BalsaFrame.class);
		b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		b.setSize(1024,768);
		b.setVisible(true);
	}

	class PlotAction extends BeanAction {

		public PlotAction() {
			setName("Time");
		}

		public void actionPerformed(ActionEvent e) {
			plot();
		}

	}

	class BodePlotAction extends BeanAction {

		public BodePlotAction() {
			setName("Bode");
		}

		public void actionPerformed(ActionEvent e) {
			bodePlot();
		}
	}

	class NisqPlotAction extends BeanAction {

		public NisqPlotAction() {
			setName("Nyquist");
		}

		public void actionPerformed(ActionEvent e) {
			nyquistPlot();
		}
	}

	class EvansPlotAction extends BeanAction {

		public EvansPlotAction() {
			setName("Evans");
		}

		public void actionPerformed(ActionEvent e) {
			evansPlot();
		}
	}

	class BlackPlotAction extends BeanAction {

		public BlackPlotAction() {
			setName("Black");
		}

		public void actionPerformed(ActionEvent e) {
			blackPlot();
		}
	}

}
