package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.ui.InteractivePanel;

/**
 * Attempt to demo an auto updating graph and all the bits that surround it
 * @author dale.macdonald
 *
 */
public class DemoGraph extends JPanel {
	
	private DataTable dataTable;
	private XYPlot plot;
	private InteractivePanel panel;
	
	public DemoGraph() {
		setBackground(Color.YELLOW);
		setLayout(new BorderLayout());
		
		//1) The Data Source
		dataTable = new DataTable(Double.class, Double.class);
		
		//Sine wave
		for (double x = -5.0; x <= 5.0; x+=0.25) {
		    double y = 5.0*Math.sin(x);
		    dataTable.add(x, y);
		    //System.out.println(dataTable.getRow(dataTable.getRowCount()).);
		}
		System.out.println(dataTable.getRowCount());
		
		//2) The Plot Type
		plot = new XYPlot(dataTable);
		
		panel = new InteractivePanel(plot);
		this.add(panel, BorderLayout.CENTER);
	}

	
	
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Live Graph Demo");
		frame.setContentPane(new DemoGraph());
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
