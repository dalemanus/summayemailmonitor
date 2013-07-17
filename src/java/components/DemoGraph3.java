package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.Plot;
import de.erichseifert.gral.plots.PlotArea;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.legends.Legend;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;
import de.erichseifert.gral.util.Orientation;

/**
 * Attempt to demo an auto updating graph and all the bits that surround it
 * @author dale.macdonald
 *
 */
public class DemoGraph3 extends JPanel {
	
	private DataTable dataTable1;
	private XYPlot plot;
	private InteractivePanel panel;
	
	public DemoGraph3() {
		setBackground(Color.YELLOW);
		setLayout(new BorderLayout());
		
		// 1) The Data Source 
		dataTable1 = new DataTable(Double.class, Double.class, Double.class);
		
		
		//Sine & Cos wave
		for (double x = -5.0; x <= 5.0; x+=0.25) {
		    double y = 5.0*Math.sin(x);
		    double z = 5.0*Math.cos(x);
		    dataTable1.add(x, y, z);
		    //System.out.println(dataTable.getRow(dataTable.getRowCount()).);
		}
		System.out.println("Row Count: " + dataTable1.getRowCount());
		System.out.println("Col Count: " + dataTable1.getColumnCount());
		
		// 2) DataSeries (Views on an underlying DataSource)
		DataSeries ds1 = new DataSeries("Sine", dataTable1, 0, 1);
		DataSeries ds2 = new DataSeries("Cos", dataTable1, 0, 2);
		
		//2) The Plot Type
		plot = new XYPlot(ds1, ds2);
		
		// Format plot
		plot.setInsets(new Insets2D.Double(20.0, 40.0, 40.0, 40.0));
		plot.setSetting(XYPlot.BACKGROUND, Color.WHITE);
		plot.setSetting(XYPlot.TITLE, "Goop");
		plot.setSetting(Plot.LEGEND, true);
		
		// Format legend
		plot.getLegend().setSetting(Legend.ORIENTATION, Orientation.HORIZONTAL);
		plot.getLegend().setSetting(Legend.ALIGNMENT_Y, 1.0);
		
		// Format plot area
		plot.getPlotArea().setSetting(PlotArea.BACKGROUND, new RadialGradientPaint(
			new Point2D.Double(0.5, 0.5),
			0.75f,
			new float[] { 0.6f, 0.8f, 1.0f },
			new Color[] { new Color(0, 0, 0, 0), new Color(0, 0, 0, 32), new Color(0, 0, 0, 128) }
		));
		
		
		//Line and Plot Renderers
		LineRenderer lines1 = new DefaultLineRenderer2D();
		plot.setLineRenderer(ds1, lines1);
		
		Color color = new Color(0.0f, 0.3f, 1.0f);
		plot.getPointRenderer(ds1).setSetting(PointRenderer.COLOR, color);
		Color color2 = new Color(1.0f, 0.3f, 0.0f);
		plot.getLineRenderer(ds1).setSetting(LineRenderer.COLOR, color2);
		
		LineRenderer lines2 = new DefaultLineRenderer2D();
		plot.setLineRenderer(ds2, lines2);
		
		Color color3 = new Color(0.0f, 0.0f, 0.0f);
		plot.getPointRenderer(ds2).setSetting(PointRenderer.COLOR, color3);
		Color color4 = new Color(0.3f, 1.0f, 0.0f);
		plot.getLineRenderer(ds2).setSetting(LineRenderer.COLOR, color4);
		
		
		// Draw a tick mark and a grid line every 10 units along x axis
		plot.getAxisRenderer(XYPlot.AXIS_X).setSetting(AxisRenderer.TICKS_SPACING,  Math.PI/4);
		// Draw a tick mark and a grid line every 20 units along y axis
		///plot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(AxisRenderer.TICKS_SPACING,  20.0);
		
		// Format Axes
		AxisRenderer axisRendererX = plot.getAxisRenderer(XYPlot.AXIS_X);
		axisRendererX.setSetting(AxisRenderer.LABEL, "Radians axis");
		
		panel = new InteractivePanel(plot);
		this.add(panel, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(800, 600));
	}

	
	
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Live Graph Demo");
		frame.setContentPane(new DemoGraph3());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
}

