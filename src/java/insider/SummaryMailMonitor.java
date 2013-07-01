package insider;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;



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
	private static String configFile = "smm_config.xml";
	
	/** log4j configuration file */
	private static String loggingPropsFile = "log4j.properties";
	
	/** Static initialiser setting absolute basic logging */
	static {
		/* Creates console appender with following Patternlayout: "%-4r [%t] %-5p %c %x - %m%n". */
		/* That is: time(ms), threadname, loglevel, loggername, Nested Diagnostic Context, message, line separator */
		BasicConfigurator.configure();
		logger.info("Launching Insider Summary Email Monitor version: " + VERSION);
	}
	
	// Initialisation Variables - to be read in shortly
	
	/** The URL of interest, which we monitor */
	public static final String TEST_URL = "https://cms.insider.thomsonreuters.com/m0nitor/3mai1_status";
	
	/** If the total drops by LESS than this amount in the basic interval, launch alert */
	public static long alertThresholdDown = -100;
	
	/** If the total increases by this amount or more in one interval, launch alert */
	public static long alertThresholdUp = 1000;
	
	/** The interval of interest*/
	public static long interval = 60000;
	
	public static int failsBeforeAlert;

}
