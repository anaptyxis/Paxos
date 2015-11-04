package application;

import java.util.List;
import java.io.IOException;

import message.AdoptedMessage;
import message.DecisionMessage;
import message.HeartBeatMessage;
import message.Message;
import message.MessageFIFO;
import message.Phase1aMessage;
import message.Phase1bMessage;
import message.Phase2aMessage;
import message.Phase2bMessage;
import message.PreemptedMessage;
import message.ProposeMessage;
import message.RecoveryReplyMessage;
import message.RecoveryRequestMessage;
import message.RequestMessage;
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
  public int numClients;
  private MessageFIFO msgQueue;
  public Paxos paxos;
  protected NetController nc;
  protected Config config;
  int[] acceptors;
  int[] replicas;
  public int[] clients;
  protected int channel ;

  /**
	 * Default Constructor
	 * @param paxos, id, number of server, number of client
	 */
  public Node(Paxos p, int id, int numServers, int numClients, boolean isClient) {
    paxos = p;
    pid = id;
    this.numServers = numServers;
    this.numClients = numClients;
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
    	if(isClient){
    		config = new Config(id+numServers,numServers+numClients+1);
    		channel = id+ numServers;
    	}
    	else{
    		config = new Config(id-1,numServers+numClients+1);
    		channel = id-1;
    	}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    nc = new NetController(config);

  }

  /**
   * Send message to paxos
   * @param msg
   * port 0 - numServer + numClient-1 
   * client 0 - numClient-1
   * server numClient - numServer + numClient - 1
   * @throws InterruptedException 
   */
  public void send(Message msg) throws InterruptedException {
	 
	 if (msg instanceof ResponseMessage) {
	      //only message to client 
	      int clientId = Math.abs(msg.dst);
	      Thread.sleep(delaySending(channel, clientId+numServers));
	      this.nc.sendMsg(clientId+numServers, msg.toString());
	      //paxos.redirectMessage(clientId+numServers, msg);
	      if(Constant.DEBUG){
	        System.out.println("Delivered to client: " + clientId);
	      }
	    } else {
	      
	      int serverId = msg.dst / Constant.INTERLEAVE;
	      Thread.sleep(delaySending(channel, serverId+numServers));
	      this.nc.sendMsg(serverId -1 , msg.toString());
	      //paxos.redirectMessage(serverId-1, msg);
	      if(Constant.DEBUG){
	    	  System.out.println("Deliver to server " + serverId + " " + msg.toString());
	      }
	     
	    }
     
	 //paxos.send(msg);
  }
  
  /**
   * Send message to paxos
   * @param dest, msg
 * @throws InterruptedException 
   */
  public void send(int dst, Message msg) throws InterruptedException {
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
	for(String tmp : rev){
		if(Constant.DEBUG){
    		System.out.println("I am "+pid+" receive message " + tmp );
    	}
		Message msg = Str2Msg(tmp);
		msgQueue.enqueue(msg);
	}
	
    Message result = msgQueue.dequeue();
    return result;
  }
  
  
  
  /*
   * 
   * 
   */
   public int delaySending(int src, int dest){
	    int i = Math.min(src, dest);
	    int j = Math.min(src, dest);
	    int delay = Paxos.delayMatrix.get(i).get(j);
	    return delay;
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
		   AdoptedMessage result = new AdoptedMessage(msg);
		   return result;
	  }else if(split_input[0].contains("Decision")){
		   DecisionMessage result = new DecisionMessage(msg);
		   return result;
	  }else if(split_input[0].contains("1a")){
		  Phase1aMessage result = new Phase1aMessage(msg);
		   return result;
	  }
	  else if(split_input[0].contains("1b")){
		  Phase1bMessage result = new Phase1bMessage(msg);
		   return result;
	  }
	  else if(split_input[0].contains("2a")){
		 
		  Phase2aMessage result = new Phase2aMessage(msg);
		 
		   return result;
	  }
	  else if(split_input[0].contains("2b")){
		  Phase2bMessage result = new Phase2bMessage(msg);
		   return result;
	  }
	  else if(split_input[0].contains("Preempt")){
		  PreemptedMessage result = new PreemptedMessage(msg);
		   return result;
	  }
	  else if(split_input[0].contains("Propose")){
		  ProposeMessage result = new ProposeMessage(msg);
		   return result;
	  }
	  else if(split_input[0].contains("Request")){
		  RequestMessage result = new RequestMessage(msg);
		   return result;
	  }
	  else if(split_input[0].contains("Response")){
		  ResponseMessage result = new ResponseMessage(msg);
		   return result;
	  }else if (split_input[0].contains("HeartBeat")){
		    HeartBeatMessage result = new HeartBeatMessage(msg);
		    return result;
	  }else if (split_input[0].contains("RecoveryReq")){
		    RecoveryRequestMessage result = new RecoveryRequestMessage(msg);
		    return result;
	  }else if (split_input[0].contains("RecoveryReply")){
		    RecoveryReplyMessage result = new RecoveryReplyMessage(msg);
		    return result;
	  }else{
		  System.out.println("receive wrong message");
		  return null;
	  }
	
	  
  }
}
