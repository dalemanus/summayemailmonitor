package insider.utils;


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
		
	/** An internal method to reveal characteristics of a URL object*/
	public static void printUrlInfo(URL url) {
		if (url != null) {
			GeneralUtils.print(new String[]{"Protocol: " + url.getProtocol()}, true);
			
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
					
			GeneralUtils.print(results, true);
		} else {
			System.out.println("Null URL provided!!");
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
				GeneralUtils.print(new String[]{inputLine});
			
			in.close();
			
		} catch (IOException e) {
			logger.error("Problem with URL!! ", e);
		}
	}
	

}
