package application;

import java.util.List;
import java.io.IOException;

import message.AdoptedMessage;
import message.Message;
import message.MessageFIFO;
import message.ResponseMessage;
import value.Constant;
import framework.Config;
import framework.NetController;

/**
 * @author Tian Zhang
 */
public class Node extends Thread{
  public int pid;
  public int numServers;
  private MessageFIFO msgQueue;
  public Paxos paxos;
  private NetController nc;
  private Config config;
  int[] acceptors;
  int[] replicas;
  public int[] clients;

  /**
	 * Default Constructor
	 * @param paxos, id, number of server, number of client
	 */
  public Node(Paxos p, int id, int numServers, int numClients) {
    paxos = p;
    pid = id;
    this.numServers = numServers;
    msgQueue = new MessageFIFO();

    acceptors = new int[numServers];
    replicas = new int[numServers];
    
    for (int i = 1; i <= numServers; i++) {
      acceptors[i - 1] = bind(i, Constant.ACCEPTOR);
      replicas[i - 1]  = bind(i, Constant.REPLICA);
    }
    
    clients = new int[numClients];
    for (int i = 0; i < numClients; i++) {
      clients[i]  = i;
    }
    
    try {
		config = new Config(id,numServers+numClients);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    nc = new NetController(config);

  }

  /**
   * Send message to paxos
   * @param msg
   */
  public void send(Message msg) {
	 if (msg instanceof ResponseMessage) {
	      /* only message to client */
	      int clientId = Math.abs(msg.dst);
	      this.nc.sendMsg(clientId, msg.toString());
	      
	     // System.out.println("Delivered to client: " + clientId);
	    } else {
	      int serverId = msg.dst / Constant.INTERLEAVE;
	      this.nc.sendMsg(serverId, msg.toString());
	     
	    }
    
  }
  
  /**
   * Send message to paxos
   * @param dest, msg
   */
  public void send(int dst, Message msg) {
    msg.dst = dst;
    send(msg);
  }

  /**
   * Message deliver to the node
   * @param message
   * enqueue the FIFO
   */
  public void deliver (Message msg) {
    msgQueue.enqueue(msg);
  }

  /**
   * receive the message
   * @param none
   * dequeue the FIFO
   */
  public Message receive () {
	List<String> rev = this.nc.getReceivedMsgs();
	return null;
    
  }
  
  /**
   * get the id dynamically
   * @param server id , role id
   * The id is sid* Interleave + role id
   */
  public int bind(int sid, int rid) {
    return sid * Constant.INTERLEAVE + rid;
  }
  
  /**
   * convert string to message
   * @param message string
   */
  
  private Message Str2Msg(String msg){
	  String[] split_input = msg.split(Constant.DELIMITER);
	  if(split_input[0].contains("Adopted")){
		  
	  }else if(split_input[0].contains("1a")){
		  
	  }else if(split_input[0].contains("1b")){
		  
	  }
	  return null;
  }
}
