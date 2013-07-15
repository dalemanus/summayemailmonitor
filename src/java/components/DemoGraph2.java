package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.text.DateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.PlotArea;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;

/**
 * Attempt to demo an auto updating graph and all the bits that surround it
 * @author dale.macdonald
 *
 */
public class DemoGraph2 extends JPanel {
	
	private DataTable dataTable;
	private XYPlot plot;
	private InteractivePanel panel;
	
	public DemoGraph2() {
		setBackground(Color.YELLOW);
		setLayout(new BorderLayout());
		
		//1) The Data Source
		dataTable = new DataTable(Double.class, Double.class);
		
		//Growing Wave
		for (int i = 0; i < 20; i++) {
			double x = System.currentTimeMillis();
		    double y = 5.0 * i;
		    dataTable.add(x, y);
		    try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    //System.out.println(dataTable.getRow(dataTable.getRowCount()).);
		}
		System.out.println("Row Count: " + dataTable.getRowCount());
		System.out.println("Col Count: " + dataTable.getColumnCount());
		//2) The Plot Type
		plot = new XYPlot(dataTable);
		
		// Format plot
		plot.setInsets(new Insets2D.Double(20.0, 40.0, 40.0, 40.0));
		plot.setSetting(XYPlot.BACKGROUND, Color.WHITE);
		plot.setSetting(XYPlot.TITLE, "Plot against Time");
		
		// Format plot area
		plot.getPlotArea().setSetting(PlotArea.BACKGROUND, new RadialGradientPaint(
			new Point2D.Double(0.5, 0.5),
			0.75f,
			new float[] { 0.6f, 0.8f, 1.0f },
			new Color[] { new Color(0, 0, 0, 0), new Color(0, 0, 0, 32), new Color(0, 0, 0, 128) }
		));
		
		
		//Line and Plot Renderers
		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderer(dataTable, lines);
		
		Color color = new Color(0.0f, 0.3f, 1.0f);
		plot.getPointRenderer(dataTable).setSetting(PointRenderer.COLOR, color);
		Color color2 = new Color(1.0f, 0.3f, 0.0f);
		plot.getLineRenderer(dataTable).setSetting(LineRenderer.COLOR, color2);
		
		
		// Draw a tick mark and a grid line every 10 units along x axis
		///plot.getAxisRenderer(XYPlot.AXIS_X).setSetting(AxisRenderer.TICKS_SPACING,  10.0);
		// Draw a tick mark and a grid line every 20 units along y axis
		///plot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(AxisRenderer.TICKS_SPACING,  20.0);
		
		// Format Axes
		AxisRenderer axisRendererX = plot.getAxisRenderer(XYPlot.AXIS_X);
		axisRendererX.setSetting(AxisRenderer.LABEL, "Time");
		axisRendererX.setSetting(AxisRenderer.TICK_LABELS_FORMAT, DateFormat.getTimeInstance());
		
		panel = new InteractivePanel(plot);
		this.add(panel, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(800, 600));
	}

	
	
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Live Graph Demo");
		frame.setContentPane(new DemoGraph2());
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

