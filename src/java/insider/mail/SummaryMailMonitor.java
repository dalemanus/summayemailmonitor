package insider.mail;

import insider.utils.GeneralUtils;
import insider.utils.UrlUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import static insider.utils.GeneralUtils.*;




/**
 *
 * A simple java program to run locally and monitor the summary email queue 
 * (via URL) and will alert the user if change thresholds are breached.
 * @author dale.macdonald
 *
 */
public class SummaryMailMonitor {
	
	/** Version Number */
	private static final String VERSION = "0.50";
	
	/** the logging object for this class. */
	private static final Logger logger = Logger.getLogger(SummaryMailMonitor.class);
	
	/** the default name of the application config file */
	private static String configFile = "smm_config.props";
	
	/** log4j configuration file */
	private static String loggingPropsFile = "log4j.properties";
	
	/** Static initialiser setting absolute basic logging */
	static {
		/* Creates console appender with following Patternlayout: "%-4r [%t] %-5p %c %x - %m%n". */
		/* That is: time(ms), threadname, loglevel, loggername, Nested Diagnostic Context, message, line separator */
		BasicConfigurator.configure();
		logEP();
		logger.info("Launching Insider Summary Email Monitor version: " + VERSION);
	}
	
	/** The timer object for counting out intervals*/
	private static Timer timer = new Timer();
	
	/**   */
	private AlertProcessor alertProcessor = new AlertProcessor();;
	
	private static int lastTotalOfLines = 0; //TODO: Comment
	
	// Initialisation Variables - to be read in shortly
	
	/** The URL of interest, which we monitor */
	public static final String TEST_URL = "https://cms.insider.thomsonreuters.com/m0nitor/3mai1_status";
	
	/** If the total drops by LESS than this amount in the basic interval, launch alert */
	public static long alertThresholdDown = -100;
	
	/** If the total increases by this amount or more in one interval, launch alert */
	public static long alertThresholdUp = 1000;
	
	/** The interval of interest*/
	public static long interval = 60000;
	
	/** Can arrange for alert to occur only after x number of failures */
	public static int failsBeforeAlert;
	
	// Proxy Details
	private final SocketAddress addr; 
	private final Proxy proxy;
	
	///static URLConnection conn;
	///static InputStream inStream;
	
	
	/** Default Constructor */
	public SummaryMailMonitor(String[] args) {
		logEP();
		
		if (args.length == 0)
			addr = new InetSocketAddress("mhsproxy.datastream.com", 80);
		else if (args.length == 1)
			addr = new InetSocketAddress(args[0], 80);
		else
			addr = new InetSocketAddress(args[1], 80);
		
		proxy = new Proxy(Proxy.Type.HTTP, addr);	
		initialise();
		
		process();
		
		
	}
	
	public void process() {
		try {
			final URL url = new URL(TEST_URL);
			if (true) UrlUtils.printUrlInfo(url); // If you want this info
			URLConnection conn = url.openConnection(proxy);
			InputStream inStream = conn.getInputStream();
			
			UrlUtils.processURLConn(url, proxy);
			
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					///- System.out.println("Timeoid!! " + System.currentTimeMillis());
					alertProcessor.processURLConnAlert(url, proxy);
				}
			}, interval, interval);
//			
//			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
//		try {
//			Thread.sleep(10000000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.err.println("Dead as a doughnut");
	}

	
	public void initialise(){
		logEP();
		PropertyConfigurator.configure("log4j.properties");
		logger.info("Starting up Insider Summary eMail Monitor, version: " + VERSION);
		
		Properties properties = loadProps();
		
		alertThresholdDown = Integer.parseInt(properties.getProperty("ThresholdDown", "100")); // TODO: Make more robust
		alertThresholdUp = Integer.parseInt(properties.getProperty("ThresholdUp", "1000"));
		interval = Integer.parseInt(properties.getProperty("Interval", "60000"));
		
		logger.info("Down threshold: " + alertThresholdDown + " items");
		logger.info("UP threshold: " + alertThresholdUp + " items");
		logger.info("Interval: " + interval + " items\n");
		
	}
	
	//TODO Perhaps Handle Exception better
	/** Load up any changes from a properties file */
	private Properties loadProps() {
		Properties props = new Properties();
		try {
			props.load(new FileReader("./" + configFile));
		} catch (FileNotFoundException e) {
			logger.error("There is no sign of a configuration file - defaults will be used", e);
		} catch (IOException e) {
			logger.fatal("There was an I/O problem reading configuration file - exiting program", e);
			System.exit(1);
		}
		return props;
	}
	

	public static void main(String[] args) {
		new SummaryMailMonitor(args);//.initialise();
	}
	


}
