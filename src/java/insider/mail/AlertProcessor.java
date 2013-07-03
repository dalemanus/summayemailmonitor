package insider.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import insider.utils.GeneralUtils;
import insider.utils.UrlUtils;

import org.apache.log4j.Logger;

/**  Methods used for processing the URL Results and generating alerts
 *	 when appropriate. 
 */
public class AlertProcessor {
	
	/** the logging object for this class. */
	private static final Logger logger = Logger.getLogger(AlertProcessor.class);
	
	//TODO - Remove this
	private static int lastTotalOfLines = 0;
	
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
			    	GeneralUtils.print("Lines: " + newTot + " Change: " + deltaLines, true) ;
			    }
			
			////- alert(deltaLines);
			
			in.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

