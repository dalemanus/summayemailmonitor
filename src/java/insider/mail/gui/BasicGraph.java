package insider.mail.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

public class BasicGraph extends JPanel {

	private DataTable dataTable;
	
	public BasicGraph() {
		this.setLayout(new BorderLayout());
		
		dataTable = new DataTable(Double.class, Double.class);
		
		for (double x = -5.0; x <= 5.0; x+=0.25) {
		    double y = 5.0*Math.sin(x);
		    dataTable.add(x, y);
		}
		
		XYPlot plot = new XYPlot(dataTable);
		
		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderer(dataTable, lines);
		
		this.add(new InteractivePanel(plot), BorderLayout.CENTER);
	}
		
}
