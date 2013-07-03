package insider.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JOptionPane;

import insider.utils.GeneralUtils;
import insider.utils.UrlUtils;

import org.apache.log4j.Logger;

import static insider.utils.GeneralUtils.print;

/**  Methods used for processing the URL Results and generating alerts
 *	 when appropriate. 
 */
public class AlertProcessor {
	
	/** the logging object for this class. */
	private static final Logger logger = Logger.getLogger(AlertProcessor.class);
	
	//TODO - Remove this
	private static int lastTotalOfLines = -1;
	
	/**  */
	public void processURLConnAlert(URL url, Proxy proxy) {
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
			
			
			int deltaLines = 0;
			while ((inputLine = in.readLine()) != null)
			    if (inputLine.contains("pending_summary_emails: ")) {
			    	int newTot = Integer.parseInt(inputLine.substring(24).trim());
			    	
			    	if (lastTotalOfLines == -1) {
			    		print("Initialising Counter...", true);
			    		lastTotalOfLines = newTot;
			    		return;
			    	}
			    	if (lastTotalOfLines == 0) {
			    		print("Email queue is currently empty!", true);
			    		lastTotalOfLines = newTot;
			    		return;
			    	}
			    	
			    	deltaLines = newTot - lastTotalOfLines;
			    	lastTotalOfLines = newTot;
			    	GeneralUtils.print("Lines: " + newTot + " Change: " + deltaLines, true) ;
			    }
			
			alert(deltaLines);
			
			in.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("A problem processing the alerts!! ", e);
		}
	}
	
	/**  */
	private void alert(int delta) {
		
		if(delta < 0)
			if (delta * -1 == SummaryMailMonitor.alertThresholdDown)
				JOptionPane.showMessageDialog(null, "Over the last period only "+ Math.abs(delta)+ " emails were processed!", "Slow Processing (Downward)",JOptionPane.INFORMATION_MESSAGE);
		else
			if(delta > SummaryMailMonitor.alertThresholdUp)
				JOptionPane.showMessageDialog(null, "Over the last period "+ Math.abs(delta)+ " emails were addeded!", "New emails added",JOptionPane.INFORMATION_MESSAGE);
		
	}
}

