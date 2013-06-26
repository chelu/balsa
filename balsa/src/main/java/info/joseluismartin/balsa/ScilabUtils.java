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

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import org.scilab.modules.graphic_objects.CallGraphicController;
import org.scilab.modules.graphic_objects.ScilabNativeView;
import org.scilab.modules.graphic_objects.axes.Axes;
import org.scilab.modules.graphic_objects.figure.Figure;
import org.scilab.modules.graphic_objects.graphicController.GraphicController;
import org.scilab.modules.graphic_objects.graphicModel.GraphicModel;
import org.scilab.modules.graphic_objects.graphicObject.GraphicObjectProperties;
import org.scilab.modules.gui.bridge.canvas.SwingScilabCanvas;

/**
 * Utility class for dealing with Scilab.
 * 
 * @author Jose Luis Martin - (jlm@joseluismartin.info)
 */
public class ScilabUtils {
	
	/**
	 * Create a new Figure with Axes and set it the current figure.
	 * @param id the figure id
	 * @return a new Figure
	 */
	public static Figure createFigureWithAxes() {
		return createFigureWithAxes(ScilabNativeView.ScilabNativeView__getValidDefaultFigureId());
	}

	/**
	 * Create a new Figure with Axes and set it the current figure.
	 * @param id the figure id
	 * @return a new Figure
	 */
	public static Figure createFigureWithAxes(Integer id) {

		Figure figure = createFigure(id);
		Axes axes = createAxes();
		figure.addChild(axes.getIdentifier());
		axes.setParent(figure.getIdentifier());
		figure.setSelectedChild(axes.getIdentifier());

		axes.setAutoScale(true);
		figure.setAutoResize(true);
		figure.getAxesSize();
		
		ScilabNativeView.ScilabNativeView__setCurrentObject(axes.getIdentifier());
		ScilabNativeView.ScilabNativeView__setCurrentFigure(figure.getIdentifier());
		ScilabNativeView.ScilabNativeView__setCurrentSubWin(axes.getIdentifier());
		
		return figure;
	}

	/**
	 * Clone the default FigureModel
	 * @return new Figure
	 */
	public static Figure createFigure(int id) {
		String figureModelUID = GraphicModel.getFigureModel().getIdentifier();
		String newFID = GraphicController.getController().createUID().toString();
		GraphicModel.getModel().cloneObject(figureModelUID, newFID);
		Figure figure = (Figure) GraphicModel.getModel().getObjectFromId(newFID);
		figure.setId(id);
		// Update ScilabView
		ScilabNativeView.ScilabNativeView__createObject(newFID);
		ScilabNativeView.ScilabNativeView__updateObject(newFID, GraphicObjectProperties.__GO_ID__);

		return figure;
	}

	/**
	 * Clone the default AxesModel
	 */
	public static Axes createAxes() {
		final int[] props = new int[] {GraphicObjectProperties.__GO_X_AXIS_LABEL__, GraphicObjectProperties.__GO_Y_AXIS_LABEL__, GraphicObjectProperties.__GO_Z_AXIS_LABEL__, GraphicObjectProperties.__GO_TITLE__};
		String newAID = GraphicController.getController().createUID().toString();
		String axesModelUID = GraphicModel.getAxesModel().getIdentifier();
		GraphicModel.getModel().cloneObject(axesModelUID, newAID);

		ScilabNativeView.ScilabNativeView__createObject(newAID);
		ScilabNativeView.ScilabNativeView__updateObject(newAID, GraphicObjectProperties.__GO_ID__);
		
		 for (Integer type : props) {
	            final double[] position = new double[] {1, 1, 1};
	            String modelLabelUID = CallGraphicController.getGraphicObjectPropertyAsString(axesModelUID, type);
	            String pobjUID = GraphicController.getController().createUID().toString();
	            GraphicModel.getModel().cloneObject(modelLabelUID, pobjUID);
	            CallGraphicController.setGraphicObjectProperty(pobjUID, GraphicObjectProperties.__GO_POSITION__, position);

	            int autoPosition = CallGraphicController.getGraphicObjectPropertyAsBoolean(modelLabelUID, GraphicObjectProperties.__GO_AUTO_POSITION__);
	            CallGraphicController.setGraphicObjectProperty(pobjUID, GraphicObjectProperties.__GO_AUTO_POSITION__, autoPosition == 1);

	            CallGraphicController.setGraphicObjectProperty(newAID, type, pobjUID);
	            CallGraphicController.setGraphicObjectRelationship(newAID, pobjUID);
	        }

		Axes axes = (Axes) GraphicModel.getModel().getObjectFromId(newAID);
		
		return axes;
	}
	
	/**
	 * Create a new SwingScilabCanvas for a Figure
	 * @param Figure to use
	 * @return a new created SwingScilabCanvas
	 */
	public static SwingScilabCanvas createPlotCanvas(Figure figure) {
		
		SwingScilabCanvas canvas = new SwingScilabCanvas(figure.getId(),  figure);
		/* Manage figure_size property */
		canvas.addComponentListener(new ComponentAdapter() {
			
	            public void componentResized(ComponentEvent ce) {
	            	
	            	SwingScilabCanvas c = (SwingScilabCanvas) ce.getSource();
	            	Figure f = c.getFigure();
	            	/* Update the figure_size property */
	            	Integer[] newSize = new Integer[] { c.getSize().width, c.getSize().height };
	            	f.setSize(newSize);
	            	if (f.getAutoResize())
	            		c.getFigure().setAxesSize(newSize);
	            	
	            	
	            	if (f.getChildren() != null && f.getChildren().length > 0)
	            		GraphicController.getController().objectUpdate(f.getChildren()[0], 
	            				GraphicObjectProperties.__GO_SIZE__);
	            	
	            }
		});
		
		return canvas;
	}
}
