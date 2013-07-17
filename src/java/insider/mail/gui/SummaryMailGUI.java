package insider.mail.gui;

import insider.mail.MailQueueChecker;
import insider.mail.SummaryMailMonitor;
import insider.utils.GeneralUtils;
import insider.utils.Observable;
import insider.utils.Observer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import de.erichseifert.gral.data.Column;
import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.data.statistics.Statistics;
import de.erichseifert.gral.plots.Plot;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;

/**
 * A first GUI to incorporate the services provided by the core summary mail
 * monitor classes.
 * 
 * @author dale.macdonald
 * 
 */
public class SummaryMailGUI extends JPanel implements Observer {

	static double GLOOG = 1.0;

	/** Version Number */
	private static final String VERSION = "0.50";

	/** the logging object for this class. */
	private static final Logger logger = Logger.getLogger(SummaryMailGUI.class);

	/** The component which displays results of the mail queue check */
	private final JTextArea textArea = new JTextArea("Summary Email Results:");

	/** The component which produces a simple line-graph of mail queue */
	private final BasicGraph basicGraph;

	/** The component which produces a simple line-graph of mail queue */
	private final MultipleTimeGraph multiGraph;
	private final Observable model = new SummaryMailMonitor(new String[] {
			"mhsproxy.datastream.com", "80" });

	// private final Observable model = new SummaryMailMonitor(new
	// String[]{"null", "80"});

	/**
	 * Default Constructor
	 */
	public SummaryMailGUI() {
		GeneralUtils.logEP();
		logger.info("Launching version " + VERSION
				+ " of the Summary email GUI Wrapper");
		setSize(new Dimension(600, 400));
		setPreferredSize(new Dimension(600, 400));
		setBackground(Color.GRAY);
		model.registerObserver(this);
		textArea.setEditable(false);
		basicGraph = new BasicGraph();
		multiGraph = new MultipleTimeGraph();
		initialise();
	}

	public void initialise() {
		this.setLayout(new BorderLayout());
		this.add(makeButtonPanel(), BorderLayout.PAGE_END);
		this.add(makeTextPanel(), BorderLayout.CENTER);
		// makeLabels();
		// makeTextFields();
		// makeCheckBoxes();
		// makeDateChoosers();
		// makeButtons();
		// makeMainPanel();
		// setState();
	}

	private JPanel makeButtonPanel() {
		JPanel panel = new JPanel(true);
		JButton startButton = new JButton("Start");
		startButton.setMnemonic(KeyEvent.VK_S);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(SummaryMailGUI.this, "Start!!");
			}
		});

		Action stopAction = new AbstractAction("stop") {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(SummaryMailGUI.this, "Stop!!");
			}
		};
		JButton stopButton = new JButton(stopAction);

		panel.add(startButton);
		panel.add(stopButton);

		return panel;
	}

	private JPanel makeTextPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		textArea.setFont(new Font("Serif", Font.ITALIC, 16));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		JScrollPane scrollPane = new JScrollPane(textArea);
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP,
				JTabbedPane.WRAP_TAB_LAYOUT);

		tabbedPane.add("Mail Log", scrollPane);
		// tabbedPane.add("Mail Graph", basicGraph);
		tabbedPane.add("Mail Graph", multiGraph);

		panel.add(tabbedPane);

		return panel;
	}

	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Insider Summary eMail Monitor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new SummaryMailGUI());
		frame.pack();
		// frame.setResizable(false);
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
	
	class QueueChecker extends Thread {
		
		private int value;
		public int getValue() {
			return value;
		}
		private MailQueueChecker checker; 
		
		public QueueChecker(MailQueueChecker checker, int value) {
			this.checker = checker;
			this.value = value;
		}
		public void run() {
			value = checker.getQueueAmount(value);
		}
	}
	
	
	

	@Override
	public void update(String result) {
		
		textArea.append(result + "\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
		
		MailQueueChecker checker = new MailQueueChecker(
				((SummaryMailMonitor) model).getProxy());
		System.out.println("### Starting checks");
		long before = System.currentTimeMillis();
//		double w = checker.getQueueAmount();
//		double x = checker.getQueueAmount(60);
//		double y = checker.getQueueAmount(120);
//		double z = checker.getQueueAmount(180);
		
		
		// This threaded version is much quicker than the sequential version commented out above
		// TODO - Possibly replace with an Executor, and Future(V).get()
		java.util.List<QueueChecker> threads = new ArrayList<QueueChecker>();
		threads.add(new QueueChecker(checker, 0));
		threads.add(new QueueChecker(checker, 60));
		threads.add(new QueueChecker(checker, 120));
		threads.add(new QueueChecker(checker, 180));
		
		for (Thread t: threads) {
			t.start();
			
		}
		
		for (Thread t : threads) {
			try {
				t.join();
				System.out.println(t.toString() + " joining");
			} catch (InterruptedException e) {
				logger.error("Boo boo!", e);
			}
		}
		
		double w = threads.get(0).getValue();
		double x = threads.get(1).getValue();
		double y = threads.get(2).getValue();
		double z = threads.get(3).getValue();
		
		System.out.println("### Checks took: " + (System.currentTimeMillis() - before));
		
		double time = System.currentTimeMillis();
		GraphUpdater grapher = new GraphUpdater(time, w, x, y, z);
		System.out.println("Grapher thread kickoff");
		grapher.start();
		try {
			grapher.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Grapher thread complete");		
	}
	
	class GraphUpdater extends Thread {
		
		private double[] points = new double[5];
		
		public GraphUpdater(double... points ) {
			this.points = points;
		}
		public void run() {
			
			System.out.println("Grapher RUN method");
			
			// 1) Retrieve Underlying DataSource
			DataTable data = multiGraph.getDataTable();
			
			if (data.getRowCount() >= 40)
				data.remove(0);
			
			data.add(points[0], points[1], points[2], points[3], points[4]);
			
			// 2a) Retrieve Actual Plot
			XYPlot plot = multiGraph.getPlot();
			
			// 2b) Format Axes
			AxisRenderer axisRendererX = plot.getAxisRenderer(XYPlot.AXIS_X);
			////axisRendererX.setSetting(AxisRenderer.LABEL, "Time");
			int widthFactor = GeneralUtils.getScreenSize().width / 100;
			Column col0 = data.getColumn(0);
			double timeMin = col0.getStatistics(Statistics.MIN);
			double timeMax = col0.getStatistics(Statistics.MAX);
			int ticks = (int)(timeMax - timeMin) / widthFactor;
			
			System.out.println("Ticks :" + ticks);
			
			axisRendererX.setSetting(AxisRenderer.TICKS_SPACING, ticks);
			axisRendererX.setSetting(AxisRenderer.TICK_LABELS_FORMAT,
					DateFormat.getTimeInstance());
			axisRendererX.setSetting(AxisRenderer.TICKS_MINOR_COUNT, 10);
			///-plot.getAxis(XYPlot.AXIS_Y).setRange(0.0, 1.0);
			Column col1 = data.getColumn(1);
			plot.getAxis(XYPlot.AXIS_Y)
					.setRange(
							0,
							Math.max(points[1] + points[1] / 10,
									col1.getStatistics(Statistics.MAX)));
			
			
			AxisRenderer axisRendererY = plot.getAxisRenderer(XYPlot.AXIS_Y);
			// /-AxisRenderer axisRendererY = new LogarithmicRenderer2D();
			axisRendererY.setSetting(AxisRenderer.LABEL, "eMails");
			axisRendererY.setSetting(AxisRenderer.TICKS_SPACING, points[1]/8);
			axisRendererY.setSetting(AxisRenderer.TICKS_MINOR_COUNT, 10);
			axisRendererY.setSetting(AxisRenderer.TICK_LABELS_FORMAT, new DecimalFormat("0 Mails"));
				
			// 3) Repaint the display component
			multiGraph.getPanel().repaint();
			System.out.println("Repaint complete");
		}
	}

	// @Override
	public void update(String result, String old) {
		String lines = null;
		int i = 7;
		if (result.contains("Lines:")) {
			lines = "";
			while (Character.isDigit(result.charAt(i)))
				lines += result.charAt(i++);
		}
		double total = 0.0;
		if (lines != null) {
			total = Double.parseDouble(lines);
			System.out.println("Total: " + total);
		}
		// textArea.setText(textArea.getText() + result + "\n");
		textArea.append(result + "\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
		DataTable data = basicGraph.getDataTable();
		// data.add(GLOOG++,10.0 + GLOOG);
		double time = System.currentTimeMillis();
		if (data.getRowCount() >= 30)
			data.remove(0);
		if (total >= 0)
			data.add(time, total);

		XYPlot plot = basicGraph.getPlot();

		// /data.remove(0);

		// Column col1 = data.getColumn(0);
		// plot.getAxis(XYPlot.AXIS_X).setRange(
		// col1.getStatistics(Statistics.MIN),
		// col1.getStatistics(Statistics.MAX)
		// );

		// plot.getAxis(XYPlot.AXIS_Y).setRange(
		// 0, GLOOG + 15);

		// plot.getAxis(XYPlot.AXIS_Y).setRange(
		// 0, total + 10);

		Column col1 = data.getColumn(1);
		plot.getAxis(XYPlot.AXIS_Y)
				.setRange(
						0,
						Math.max(total + total / 10,
								col1.getStatistics(Statistics.MAX)));

		basicGraph.getPanel().repaint();
	}
}
