package insider.mail.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.text.DateFormat;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.Plot;
import de.erichseifert.gral.plots.PlotArea;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.axes.LogarithmicRenderer2D;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;

public class BasicGraph extends JPanel {

	/**  The DataSource implementation holding the values from which the graph can be plotted */
	private DataTable dataTable;
	
	/**  A plot instance, in this case, X-Y */
	private XYPlot plot;
	
	/**  Smart JPanel subclass which bundles useful productivity features */
	private InteractivePanel panel;
	
	/**
	 * Accessor for the data table.
	 * @return
	 */
	public DataTable getDataTable() {
		return dataTable;
	}

	public XYPlot getPlot() {
		return plot;
	}
	
	public InteractivePanel getPanel() {
		return panel;
	}
	
	public BasicGraph() {
		this.setLayout(new BorderLayout());
		
		//1) The Data Source
		dataTable = new DataTable(Double.class, Double.class);
		
		//2) The Plot Type
		plot = new XYPlot(dataTable);
		
		//3) Format plot
		plot.setInsets(new Insets2D.Double(20.0, 90.0, 40.0, 20.0));
		plot.setSetting(XYPlot.BACKGROUND, Color.WHITE);
		plot.setSetting(Plot.TITLE, "Total eMails in Queue");
		
		// Draw a tick mark and a grid line every 10 units along x axis
		plot.getAxisRenderer(XYPlot.AXIS_X).setSetting(AxisRenderer.TICKS_SPACING,  250.0);
		// Draw a tick mark and a grid line every 20 units along y axis
		///-plot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(AxisRenderer.TICKS_SPACING,  50.0);
		
		// Format plot area
		plot.getPlotArea().setSetting(PlotArea.BACKGROUND, new RadialGradientPaint(
			new Point2D.Double(0.5, 0.5),
			0.75f,
			new float[] { 0.6f, 0.8f, 1.0f },
			new Color[] { new Color(0, 0, 0, 0), new Color(0, 0, 0, 32), new Color(0, 0, 0, 128) }
		));
		
		
		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderer(dataTable, lines);
		
		Color color = new Color(0.0f, 0.3f, 1.0f);
		plot.getPointRenderer(dataTable).setSetting(PointRenderer.COLOR, color);
		plot.getLineRenderer(dataTable).setSetting(LineRenderer.COLOR, color);
		
		
		
		// Format axes (set scale and spacings)
		plot.getAxis(XYPlot.AXIS_Y).setRange(0.0, 1.0);
		AxisRenderer axisRendererX = plot.getAxisRenderer(XYPlot.AXIS_X);
		axisRendererX.setSetting(AxisRenderer.TICKS_SPACING, 5000);
		axisRendererX.setSetting(AxisRenderer.TICK_LABELS_FORMAT, DateFormat.getTimeInstance());
		AxisRenderer axisRendererY = plot.getAxisRenderer(XYPlot.AXIS_Y);
		///-AxisRenderer axisRendererY = new LogarithmicRenderer2D();
		///axisRendererY.setSetting(AxisRenderer.TICKS_MINOR_COUNT, 100);
		axisRendererY.setSetting(AxisRenderer.TICK_LABELS_FORMAT, new DecimalFormat("0 Mails"));
		///-plot.setAxisRenderer(XYPlot.AXIS_Y, axisRendererY);
		
		panel = new InteractivePanel(plot);
		this.add(panel, BorderLayout.CENTER);
			
	}	
}
