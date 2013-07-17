package insider.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import insider.utils.GeneralUtils;

import org.apache.log4j.Logger;

/**
 * This class will check the mail URL and parse a queue length.
 * 
 * It can take a parameter which determines outstanding time duration
 * @author dale.macdonald
 *
 */
public class MailQueueChecker {

	/** the logging object for this class. */
	private static final Logger logger = Logger.getLogger(MailQueueChecker.class);
	
	/** The URL to check, sans parameter */
	public static final String THE_URL = "http://dtc.service.insider.thomsonreuters.com/insider/m0nitor/3mai1_status";
	
	
	/**  A Proxy if required*/
	Proxy proxy;
	
	public MailQueueChecker(Proxy proxy) {
		this.proxy = proxy;
	}
	
	public MailQueueChecker() {
		
	}
	
	public int getQueueAmount(int hoursOld) {
		
		String fullURL = THE_URL;
		String[] lines = null;
		if (hoursOld > 0)
			fullURL += "?delay=" + hoursOld;
		
		try {
			final URL url = new URL(fullURL);
			URLConnection conn;
			
			if (proxy != null)
				conn = url.openConnection(proxy);
			else
				conn = url.openConnection();
			
			BufferedReader in;
			String inputLine;
			lines = new String[5];
			int i = 0;
				
			InputStream inStream = conn.getInputStream();
				
			in = new BufferedReader(
					new InputStreamReader(inStream));
			while ((inputLine = in.readLine()) != null) {
				GeneralUtils.print(new String[]{inputLine}, true);
				lines[i++] = inputLine;
			}
			in.close();
				
			
			
		} catch (MalformedURLException mue) {
			GeneralUtils.print("Problem with the URL!! " + mue.toString(), true);
		} catch (IOException ioe) {
			GeneralUtils.print("Problem opening the connection!! " + ioe.toString(), true);
		}
		
		String result = lines[2].substring(24, lines[2].length());
		return Integer.parseInt(result);
	}
	
	public int getQueueAmount() {
		return getQueueAmount(0);
	}
}
