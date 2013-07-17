package insider.mail.gui;

import insider.utils.GeneralUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.text.DateFormat;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import components.DemoGraph3;

import de.erichseifert.gral.data.Column;
import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.data.statistics.Statistics;
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
 * A graph which plots various outstanding mail totals, in time intervals.
 * 
 * @author dale.macdonald
 *
 */
public class MultipleTimeGraph extends JPanel {{
	
}
	/** the logging object for this class. */
	private static final Logger logger = Logger.getLogger(MultipleTimeGraph.class);
	
	/**  The DataSource implementation holding the values from which the graph can be plotted */
	private DataTable dataTable;
	
	/**  A plot instance, in this case, X-Y */
	private XYPlot plot;
	
	/**  Smart JPanel subclass which bundles useful productivity features */
	private InteractivePanel panel;
	
	public DataSeries ds1;
	
	
	/**
	 * Accessor for the data table.
	 * @return the complete DataTable
	 */
	public DataTable getDataTable() {
		return dataTable;
	}

	/**
	 * Accessor for the plot instance.
	 * @return the complete DataTable
	 */
	public XYPlot getPlot() {
		return plot;
	}
	
	/**
	 * Accessor for the entire interactive panel.
	 * @return the complete DataTable
	 */
	public InteractivePanel getPanel() {
		return panel;
	}
	
	public MultipleTimeGraph() {
		
		this.setLayout(new BorderLayout());
		
		// 1) The Underlying Data Source
		dataTable = new DataTable(Double.class, Double.class, Double.class, Double.class, Double.class);
		
		// 2) DataSeries (Views on an underlying DataSource)
		ds1 = new DataSeries("Total", dataTable, 0, 1);
		DataSeries ds2 = new DataSeries("One Hour", dataTable, 0, 2);
		DataSeries ds3 = new DataSeries("Two Hour", dataTable, 0, 3);
		DataSeries ds4 = new DataSeries("Three Hour", dataTable, 0, 4);
		
		// 3) The Plot Type
		plot = new XYPlot(ds1, ds2, ds3, ds4);
		
		// 4) Format plot
		plot.setInsets(new Insets2D.Double(20.0, 80.0, 60.0, 40.0));
		plot.setSetting(XYPlot.BACKGROUND, Color.WHITE);
		plot.setSetting(XYPlot.TITLE, "eMails in Queue");
		plot.setSetting(Plot.LEGEND, true);
		
		// 5) Format legend
		plot.getLegend().setSetting(Legend.ORIENTATION, Orientation.HORIZONTAL);
		plot.getLegend().setSetting(Legend.ALIGNMENT_Y, 1.0);
		
		// 6) Format plot area
		plot.getPlotArea().setSetting(PlotArea.BACKGROUND, new RadialGradientPaint(
			new Point2D.Double(0.5, 0.5),
			0.75f,
			new float[] { 0.6f, 0.8f, 1.0f },
			new Color[] { new Color(0, 0, 0, 0), new Color(0, 0, 0, 32), new Color(0, 0, 0, 128) }
		));
		
		// 7) Line and Plot Renderers
		LineRenderer lines1 = new DefaultLineRenderer2D();
		plot.setLineRenderer(ds1, lines1);
		Color color1 = new Color(0.0f, 0.3f, 1.0f);
		plot.getPointRenderer(ds1).setSetting(PointRenderer.COLOR, color1);
		plot.getLineRenderer(ds1).setSetting(LineRenderer.COLOR, color1);
		
		LineRenderer lines2 = new DefaultLineRenderer2D();
		plot.setLineRenderer(ds2, lines2);
		Color color2 = new Color(0.3f, 1.0f, 0.0f);
		plot.getPointRenderer(ds2).setSetting(PointRenderer.COLOR, color2);
		plot.getLineRenderer(ds2).setSetting(LineRenderer.COLOR, color2);
		
		LineRenderer lines3 = new DefaultLineRenderer2D();
		plot.setLineRenderer(ds3, lines3);
		Color color3 = new Color(1.0f, 0.0f, 0.3f);
		plot.getPointRenderer(ds3).setSetting(PointRenderer.COLOR, color3);
		plot.getLineRenderer(ds3).setSetting(LineRenderer.COLOR, color3);
		
		LineRenderer lines4 = new DefaultLineRenderer2D();
		plot.setLineRenderer(ds4, lines4);
		Color color4 = new Color(0.3f, 0.3f, 0.0f);
		plot.getPointRenderer(ds4).setSetting(PointRenderer.COLOR, color4);
		plot.getLineRenderer(ds4).setSetting(LineRenderer.COLOR, color4);
		

		// 8) Format Axes
		AxisRenderer axisRendererX = plot.getAxisRenderer(XYPlot.AXIS_X);
		axisRendererX.setSetting(AxisRenderer.LABEL, "Time");
		int widthFactor = GeneralUtils.getScreenSize().width / 100;
		
	
		axisRendererX.setSetting(AxisRenderer.TICKS_SPACING,widthFactor);
		axisRendererX.setSetting(AxisRenderer.TICK_LABELS_FORMAT, DateFormat.getTimeInstance());
		axisRendererX.setSetting(AxisRenderer.TICKS_MINOR_COUNT, 10);
		plot.getAxis(XYPlot.AXIS_Y).setRange(0.0, 1.0);
		
		
		AxisRenderer axisRendererY = plot.getAxisRenderer(XYPlot.AXIS_Y);
		///-AxisRenderer axisRendererY = new LogarithmicRenderer2D();
		axisRendererY.setSetting(AxisRenderer.LABEL, "eMails");
		axisRendererY.setSetting(AxisRenderer.TICKS_SPACING, 500);
		axisRendererY.setSetting(AxisRenderer.TICKS_MINOR_COUNT, 10);
		axisRendererY.setSetting(AxisRenderer.TICK_LABELS_FORMAT, new DecimalFormat("0 Mails"));
		
		
	
		// 9) Display the plot on a panel
		panel = new InteractivePanel(plot);
		this.add(panel, BorderLayout.CENTER);
		this.setPreferredSize(GeneralUtils.getScreenSize());
		
	}
	
	public void setSample() {
		
		DataTable table = dataTable;
		for (int i = 0; i < 40; i++) {
			double x = 30 * i;
			double y = x/2;
			double z = y/2;
			double w = x/3;
			table.add(i + 0.0, x, y, z, w);
		}
	}
	

	
	class Updater extends Thread {
		public void run() {
			DataTable table = dataTable;
			
			double w = 300;
			double x = w / 2;
			double y = w/3;
			double z = w/4;
			
			
			while (true) {
				if (table.getRowCount() >= 40)
					table.remove(0);
				
				table.add(System.currentTimeMillis() + 0.0, w, x, y, 0.0);
				
				w *= 2;
				x = w / 2;
				y = w/3;
				z = w/4;
				
				panel.repaint();
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					logger.error("Oops! ", e);
				}
				
				// 8) Format Axes
				AxisRenderer axisRendererX = plot.getAxisRenderer(XYPlot.AXIS_X);
				int widthFactor = GeneralUtils.getScreenSize().width / 100;
				Column col0 = table.getColumn(0);
				double timeMin = col0.getStatistics(Statistics.MIN);
				double timeMax = col0.getStatistics(Statistics.MAX);
				int ticks = (int)(timeMax - timeMin) / widthFactor;
				
				System.out.println("Ticks :" + ticks);
				
				axisRendererX.setSetting(AxisRenderer.TICKS_SPACING, ticks);
				axisRendererX.setSetting(AxisRenderer.TICK_LABELS_FORMAT,
						DateFormat.getTimeInstance());
				axisRendererX.setSetting(AxisRenderer.TICKS_MINOR_COUNT, 10);
				
				plot.getAxis(XYPlot.AXIS_Y).setRange(0.0, 1.0);

				AxisRenderer axisRendererY = plot.getAxisRenderer(XYPlot.AXIS_Y);
				// /-AxisRenderer axisRendererY = new LogarithmicRenderer2D();
				axisRendererY.setSetting(AxisRenderer.LABEL, "eMails");
				axisRendererY.setSetting(AxisRenderer.TICKS_SPACING, w /5);
				axisRendererY.setSetting(AxisRenderer.TICKS_MINOR_COUNT, 10);
				axisRendererY.setSetting(AxisRenderer.TICK_LABELS_FORMAT, new DecimalFormat("0 Mails"));
			}
		}
	}
	
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Multi Graph Demo");
		final MultipleTimeGraph multi = new MultipleTimeGraph();
		//multi.setSample();
		frame.setContentPane(multi);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		multi.new Updater().start();

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
