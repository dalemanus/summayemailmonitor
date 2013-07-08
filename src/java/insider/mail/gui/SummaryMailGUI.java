package insider.mail.gui;

import insider.mail.SummaryMailMonitor;
import insider.utils.GeneralUtils;
import insider.utils.Observable;
import insider.utils.Observer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

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


/**
 * A first GUI to incorporate the services provided by  the core summary mail monitor
 * classes.
 * 
 * @author dale.macdonald
 *
 */
public class SummaryMailGUI extends JPanel implements Observer{
	
	/** Version Number */
	private static final String VERSION = "0.50";

	/** the logging object for this class. */
	private static final Logger logger = Logger.getLogger(SummaryMailGUI.class);
	
	/** The component which displays results of the mail queue check */
	private final JTextArea textArea = new JTextArea("Summary Email Results:");
	
	private final Observable model = new SummaryMailMonitor(new String[]{"mhsproxy.datastream.com", "80"});
	//private final Observable model = new SummaryMailMonitor(new String[]{"null", "80"});
	
	/**
	 * Default Constructor
	 */
	public SummaryMailGUI() {
		GeneralUtils.logEP();
		logger.info("Launching version " + VERSION + " of the Summary email GUI Wrapper");
		setSize(new Dimension(600, 400));
		setPreferredSize(new Dimension(600, 400));
		setBackground(Color.GRAY);
		model.registerObserver(this);
		textArea.setEditable(false);
		initialise();
	}
	
	public void initialise() {
		this.setLayout(new BorderLayout());
		this.add(makeButtonPanel(), BorderLayout.PAGE_END);
		this.add(makeTextPanel(), BorderLayout.CENTER);
		//makeLabels();
		//makeTextFields();
		//makeCheckBoxes();
		//makeDateChoosers();
		//makeButtons();
		//makeMainPanel();
		//setState();
	}
	
	private JPanel makeButtonPanel() {
		JPanel panel = new JPanel(true);
		JButton startButton = new JButton("Start");
		startButton.setMnemonic(KeyEvent.VK_S);
		startButton.addActionListener(new ActionListener(){
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
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		
		tabbedPane.add("Mail Log", scrollPane);
		tabbedPane.add("Mail Graph", new BasicGraph());
		//tabbedPane.add("Mail Graph", new JPanel());
		
		panel.add(tabbedPane);
		
		return panel;
	}
	
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("Insider Summary eMail Monitor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new SummaryMailGUI());
		frame.pack();
		//frame.setResizable(false);
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

	@Override
	public void update(String result) {
		//textArea.setText(textArea.getText() + result + "\n");
		textArea.append(result + "\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
		
	}
}
