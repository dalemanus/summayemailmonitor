package insider.utils;

import insider.mail.SummaryMailMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

/**
 * A utility class to contain helper methods to be used on URL objects
 * 
 * @author dale.macdonald
 *
 */
public class UrlUtils {

	/** the logging object for this class. */
	private static final Logger logger = Logger.getLogger(UrlUtils.class);
	
	//TODO - Remove this
	private static int lastTotalOfLines = 0;
	
	/** An internal method to reveal characteristics of a URL object*/
	public static void printUrlInfo(URL url) {
		if (url != null) {
			print(new String[]{"Protocol: " + url.getProtocol()}, true);
			
			String[] results = {"Authority: " + url.getAuthority(),
					"Host: " + url.getHost(),
					"Port: " + url.getPort(),
					"Path: " + url.getPath(),
					"Query: " + url.getQuery(),
					"File: " + url.getFile(),
					"Ref: " + url.getRef(),
					"\nOptional:\n",
					"Default Port" + url.getDefaultPort(),
					"UserInfo: " + url.getUserInfo() 
			};
					
			print(results, true);
		} else {
			System.out.println("Null URL provided!!");
		}
	}
	
	/**  */
	public static void processURLConnAlert(URL url, Proxy proxy) {
		BufferedReader in;
		String inputLine;
		try {
			URLConnection conn;
			if (proxy != null)
				 conn = url.openConnection(proxy);
			else
				conn = url.openConnection();
			
			
			in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			
			
			int deltaLines = -50;
			while ((inputLine = in.readLine()) != null)
			    if (inputLine.contains("pending_summary_emails: ")) {
			    	int newTot = Integer.parseInt(inputLine.substring(24).trim());
			    	if (lastTotalOfLines == 0) {
			    		System.out.println("Updating from fresh");
			    		lastTotalOfLines = newTot;
			    		return;
			    	}
			    	
			    	deltaLines = newTot - lastTotalOfLines;
			    	lastTotalOfLines = newTot;
			    	print("Lines: " + newTot + " Change: " + deltaLines, true) ;
			    }
			
			////- alert(deltaLines);
			
			in.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Method to read back "results" of a URL */
	public static void processURLConn(URL url, Proxy proxy) {
		
		BufferedReader in;
		String inputLine;
		try {
			URLConnection conn;
			if (proxy != null)
				 conn = url.openConnection(proxy);
			else
				conn = url.openConnection();
			
			InputStream inStream = conn.getInputStream();
			
			in = new BufferedReader(
					new InputStreamReader(inStream));
			while ((inputLine = in.readLine()) != null)
			    print(new String[]{inputLine});
			
			in.close();
			
		} catch (IOException e) {
			logger.error("Problem with URL!! ", e);
		}
	}
	
	private static void print(String[] strings, boolean toLogger) {
		for (String string : strings) {
			if (toLogger)
				logger.info(string);
			else
				System.out.println(string);
		}
	}
	
	private static void print(String[] strings) {
		print(strings, false);
	}
	
	private static void print(String string, boolean toLogger) {
		print(new String[]{string}, toLogger);
	}
	
	private static void print(String string) {
		print(new String[]{string}, false);
	}
}
