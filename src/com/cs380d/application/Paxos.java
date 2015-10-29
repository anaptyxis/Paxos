package application;

import message.Message;

public class Paxos {
		public Server[] serverList;
		public Client[] clientList;
		
		
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
		 */
		public Paxos(int server, int client) {
			// TODO Auto-generated constructor stub
		}
		
		/**
		 * print the log of client
		 * @param id of client
		 */
		
		public String printLog(int clientID){
			  
			  return null;
		}
		
		
		/**
		 * crash the server
		 * @param id of server
		 */
		
		public void crashServer(int serverID){
			  
			  
		}
		
		
		/**
		 * restart the server
		 * @param id of server
		 */
		
		public void reviveServer(int serverID){
			  
			  
		}
		
		/**
		 * a client broadcast the message
		 * @param id of client
		 */
		
		public void clientBroadcast(int client, String Msg ){
			  
			  
		}
		
		
		/**
		 * send message in Async system
		 * @param id of client
		 */
		
		public void send(Message msg ){
			  
			  
		}
		
}
