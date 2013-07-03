package insider.utils;

import insider.mail.SummaryMailMonitor;

import org.apache.log4j.Logger;

public class GeneralUtils {

	/** the logging object for this class. */
	private static final Logger logger = Logger.getLogger(GeneralUtils.class);
	
	/**  Set this for the "simple" print to use logger rather than System.out */
	public static boolean loggerByDefault = false;
	
	
	// Methods to either log.info or sysout 
	
	/**
	 * 
	 * @param strings
	 * @param toLogger
	 */
	public static void print(String[] strings, boolean toLogger) {
		for (String string : strings) {
			if (toLogger)
				logger.info(string);
			else
				System.out.println(string);
		}
	}
	
	public static void print(String[] strings) {
		print(strings, loggerByDefault);
	}
	
	public static void print(String string, boolean toLogger) {
		print(new String[]{string}, toLogger);
	}
	
	public static void print(String string) {
		print(new String[]{string}, loggerByDefault);
	}
	
	// Interesting way to log location in program - can actually also configure log4j
	/** Convenience Method to log class and method entry point */
	public static void logEP() {
		logger.debug("Entering " + currentClassName() + " " + GeneralUtils.currentMethodName() + "() method!");
	}
	
	/** Determines the current class name */
	public static String currentClassName() {   
		return Thread.currentThread().getStackTrace()[3].getClassName();
	}
	
	/** Determines the current method name */
	public static String currentMethodName() {   
		return Thread.currentThread().getStackTrace()[3].getMethodName();
	}
}
