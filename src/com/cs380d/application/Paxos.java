package application;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;

import value.Constant;
import framework.Config;
import framework.NetController;
import message.Message;
import message.ResponseMessage;
/**
 * The class for paxos environment
 * @author Tian Zhang
 */
public class Paxos {
		public Server[] serverList;
		public Client[] clientList;
		public Config config;
		public NetController ncController;
		private static Paxos instance = null;
		private boolean[] activeList;
		public static ArrayList<ArrayList<Integer>> delayMatrix = null;
		
		
		/**
		 * Default Constructor
		 * 
		 */
		public Paxos() {
			// TODO Auto-generated constructor stub
		}
		
		
		/**
		 * Config the paxos environment
		 * @param numServers, number od client
		 * @throws IOException 
		 */
		private Paxos(int numServers, int numClients) throws IOException {
			
			    /*
			     * Configured the delay matrix 
			     */
			    delayMatrix = new ArrayList<ArrayList<Integer>>();
			    for(int i = 0 ; i < numClients+numServers ; i++){
			    	ArrayList<Integer> rowArrayList = new ArrayList<Integer>();
			    	for(int j = 0 ; j < numClients+numServers; j++){
			    		int randomNum =  (int)(Math.random()* Constant.MAXVALUE);
			    		if(i<j){
			    			rowArrayList.add(randomNum);
			    		}else{
			    			rowArrayList.add(0);
			    		}
			    	}
			    	delayMatrix.add(rowArrayList);
			    }
			    // Configured active server list
			    activeList = new boolean[numServers];
			    for(int i = 0 ; i < activeList.length; i++){
			    	activeList[i] = true;
			    }
			    
			    // config the channel
				config = new Config(numClients+numServers,numClients+numServers+1);
				ncController = new NetController(config);
				serverList = new Server[numServers];
				for (int i = 1; i <= numServers; i++) {
					serverList[i - 1] = new Server(i, numServers, numClients, this, false);
				}

				clientList = new Client[numClients];
				for (int i = 0; i < numClients; i++) {
					clientList[i] = new Client(i, numServers, numClients, this);
					clientList[i].start();
				}
				//
				for (int i = numServers - 1; i >= 0; i--) {
					serverList[i].start();
				}
					
		}
		
		public static Paxos getInstance(int numServers, int numClients) {
		      if(instance == null) {
		         try {
					instance = new Paxos(numServers, numClients);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      }
		      return instance;
		}
		
		/**
		 * print the log of client
		 * @param id of client
		 */
		
		public String printLog(int clientID){
			if(clientID >= clientList.length){
				 System.err.println("Invalid client number");
				 System.exit(0);
			}
			return clientList[clientID].printChatLog();
		}
		
		
		/**
		 * crash the server
		 * @param id of server
		 */
		
		public void crashServer(int serverID){
			  if(serverID >= serverList.length){
				 System.err.println("Invalid server number");
				 System.exit(0);
			  } 
			  serverList[serverID].cleanShutDown();
		}
		
		
		/**
		 * restart the server
		 * @param id of server
		 * @throws InterruptedException 
		 */
		
		public void reviveServer(int serverID) throws InterruptedException{
			//serverList[serverID] = null;
			if (serverList[serverID].shutdown) {
				serverList[serverID] = new Server(serverID + 1, serverList.length, clientList.length, this,true);
				serverList[serverID].recover();
			    serverList[serverID].start();
			}
		}
		
		/**
		 * a client broadcast the message
		 * @param id of client
		 * @throws InterruptedException 
		 */
		
		public void clientBroadcast(int client, String Msg ) throws InterruptedException{
			
			if(client >= clientList.length){
				 System.err.println("Invalid client number");
				 System.exit(0);
			}

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  clientList[client].broadcast(Msg);
			  
		}
		
		// redirect the message to its destination
		
		public synchronized void redirectMessage(int dest ,Message msg){
				this.ncController.sendMsg(dest, msg.toString());
		} 
		
		
		
}
