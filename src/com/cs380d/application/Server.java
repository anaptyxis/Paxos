package application;

import java.util.HashMap;

import role.NodeRole;

public class Server extends Node{


	  
	  //Map from process sequence number to Role 
	  public HashMap<Integer, NodeRole> roles;
	  
	  public Server(Paxos p, int id, int numServers, int numClients) {
		super(p, id, numServers, numClients);
		// TODO Auto-generated constructor stub
	}

}
