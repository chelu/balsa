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

package info.joseluismartin.balsa;

import info.joseluismartin.balsa.model.LtiSystem;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scilab.modules.javasci.JavasciException;
import org.scilab.modules.javasci.Scilab;

/**
 * Solver implementation using Scilab.
 * 
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
public class ScilabSolver implements Solver {

	private static final Log log = LogFactory.getLog(ScilabSolver.class);
	private static final String PLOT_TEMPLATE = "/scilab/plotsyslin.template";
	private static final String NISQ_TEMPLATE = "/scilab/nyquist.template";
	private static final String BODE_TEMPLATE = "/scilab/bode.template";

	private String plotUri = "/tmp/plot.svg";

	private Scilab scilab;

	public ScilabSolver() {
		if (!open()) {
			log.error("Can't open scilab");
		}
	}

	private boolean open() {
		try {
			scilab = new Scilab(true);
			return scilab.open();
		} catch (JavasciException e) {
			log.error(e);
			return false;
		}
	}
	
	public void close() {
		scilab.close();
	}


	public Simulation solve(LtiSystem system, Function input) {
		// TODO Auto-generated method stub
		return null;
	}


	public void plot(final LtiSystem system) {
		Runnable runnable = new TemplateRunnable(PLOT_TEMPLATE, system);
		new Thread(runnable).start();
	}
	
	public String getPlotUri() {
		return "file://" + plotUri;
	}

	public void bodePlot(LtiSystem system) {
		Runnable runnable = new TemplateRunnable(BODE_TEMPLATE, system);
		new Thread(runnable).start();
		
	}

	public void nyquistPlot(LtiSystem system) {
		Runnable runnable = new TemplateRunnable(NISQ_TEMPLATE, system);
		new Thread(runnable).start();
		
	}
	
	class TemplateRunnable implements Runnable {
		
		private String template;
		private LtiSystem system;
		
		/**
		 * @param plotTemplate
		 * @param system2
		 */
		public TemplateRunnable(String template, LtiSystem system) {
			this.template = template;
			this.system = system;
		}

		public void run() {
			String currentCommand = null;
			try {
				InputStream is = getClass().getResourceAsStream(template);
				String template = IOUtils.toString(is, "ISO-8859-1");
				template = template.replace("${H}", system.getTransferFunction());
				String[] commands = template.split("\n");
			
				scilab.exec("version=getversion()");
				log.info(scilab.get("version").toString());

				for (String c : commands) {
					currentCommand = c;
					scilab.execException(c);
				}
			} catch (Exception e) {
				log.error("Error in command: [" + currentCommand + "]" );
				log.error(e);
			}
			
		}
	}

	public void evansPlot(LtiSystem system) {
		new Thread(new TemplateRunnable("/scilab/evans.template", system)).start();
		
	}
	
	public void blackPlot(LtiSystem system) {
		templatePlot(system, "/scilab/black.template");
	}
	
	public void templatePlot(LtiSystem system, String template) {
		new Thread(new TemplateRunnable(template, system)).start();
	}

}
