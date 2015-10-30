/**
 * This code may be modified and used for non-commercial 
 * purposes as long as attribution is maintained.
 * 
 * @author: Isaac Levy
 */

package framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;
import java.util.logging.Logger;


public class Config {

	public static final int PORT = 10000;
	public static final String LOCALHOST = "127.0.0.1";
	

	 /**
	   * config the socket with  
	   * @param numServers + numClients, index
	   * @throws IOException
	   */
	public Config(int index, int num) throws IOException {

	        logger = Logger.getLogger("NetFramework");
			numProcesses = num;
	        procNum = index;
			addresses = new InetAddress[numProcesses];
			ports = new int[numProcesses];

			for (int i = 0; i < numProcesses; i++) {
				ports[i] = PORT + i;
				addresses[i] = InetAddress.getByName(LOCALHOST);
				System.out.printf("%d: %d @ %s\n", i, ports[i], addresses[i]);
			}
	}
		
	
	private int loadInt(Properties prop, String s) {
		return Integer.parseInt(prop.getProperty(s.trim()));
	}
	
	/**
	 * Default constructor for those who want to populate config file manually
	 */
	public Config() {
	}

	/**
	 * Array of addresses of other hosts.  All hosts should have identical info here.
	 */
	public InetAddress[] addresses;
	

	/**
	 * Array of listening port of other hosts.  All hosts should have identical info here.
	 */
	public int[] ports;
	
	/**
	 * Total number of hosts
	 */
	public int numProcesses;
	
	/**
	 * This hosts number (should correspond to array above).  Each host should have a different number.
	 */
	public int procNum;
	
	/**
	 * Logger.  Mainly used for console printing, though be diverted to a file.
	 * Verbosity can be restricted by raising level to WARN
	 */
	public Logger logger;
}
