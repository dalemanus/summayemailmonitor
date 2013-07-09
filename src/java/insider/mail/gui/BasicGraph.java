package insider.mail.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.DateFormat;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.Plot;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;

public class BasicGraph extends JPanel {

	private DataTable dataTable;
	private XYPlot plot;
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
		
		dataTable = new DataTable(Double.class, Double.class);

		//Sine wave
//		for (double x = -5.0; x <= 5.0; x+=0.25) {
//		    double y = 5.0*Math.sin(x);
//		    dataTable.add(x, y);
//		}
		
		// Draw a tick mark and a grid line every 10 units along x axis
		/// - plot.getAxisRenderer(XYPlot.AXIS_X).setSetting(AxisRenderer.TICKS_SPACING,  10.0);
		// Draw a tick mark and a grid line every 20 units along y axis
		/// - plot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(AxisRenderer.TICKS_SPACING,  20.0);
		
		plot = new XYPlot(dataTable);
		
		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderer(dataTable, lines);
		
		Color color = new Color(0.0f, 0.3f, 1.0f);
		plot.getPointRenderer(dataTable).setSetting(PointRenderer.COLOR, color);
		plot.getLineRenderer(dataTable).setSetting(LineRenderer.COLOR, color);
		
		plot.setInsets(new Insets2D.Double(20.0, 90.0, 40.0, 20.0));
		plot.setSetting(Plot.TITLE, "EMails in Queue");
		
		// Format axes (set scale and spacings)
		plot.getAxis(XYPlot.AXIS_Y).setRange(0.0, 1.0);
		AxisRenderer axisRendererX = plot.getAxisRenderer(XYPlot.AXIS_X);
		axisRendererX.setSetting(AxisRenderer.TICKS_SPACING, 50);
		axisRendererX.setSetting(AxisRenderer.TICK_LABELS_FORMAT, DateFormat.getTimeInstance());
		AxisRenderer axisRendererY = plot.getAxisRenderer(XYPlot.AXIS_Y);
		axisRendererY.setSetting(AxisRenderer.TICKS_MINOR_COUNT, 40);
		axisRendererY.setSetting(AxisRenderer.TICK_LABELS_FORMAT, new DecimalFormat("0 Mails"));
		
		panel = new InteractivePanel(plot);
		this.add(panel, BorderLayout.CENTER);
			
	}	
}
