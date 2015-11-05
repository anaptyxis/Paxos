package application;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import message.HeartBeatMessage;
import message.Message;
import message.Phase1aMessage;
import message.Phase2aMessage;
import message.RecoveryReplyMessage;
import message.RecoveryRequestMessage;
import message.RequestMessage;
import message.ResponseMessage;
import role.Acceptor;
import role.Leader;
import role.NodeRole;
import role.Replica;
import value.Command;
import value.Constant;

/**
 * @author zhangtian
 */



public class Server extends Node {
  
  // Server identifier
  public int index;
  // the id of current leader
  public int leaderID;
  // whether the server is working
  public boolean shutdown = false;

 
  /**
   * Local server process id
   * Reserved ID :
   * Replica : 0
   * Acceptor : 1
   * Leader : 2
   */
  public int id = 3;

  /**
   * For time bomb leader
   */
  ReentrantLock lock ;
  public int messageCount = -1;

  /**
   * Map from process id to NodeRole
   */
  public HashMap<Integer, NodeRole> roles;

  public Replica replica;
  Acceptor acceptor;
  Leader leader;
  
  /*Hear beat message handler*/
  private HeartBeatMessageHandler  hbmHandler ;
  
  
  /*active client list , and server only send to active client*/
  
  private HashSet<Integer> activeClientList;
  
  /**
 	 * Default constructor for Server
 	 * @param Id, number of server, number of client, the paxos environment, whether it is recover thread
 	 */

  public Server(int idx, int numSevers, int numClients, Paxos paxos,boolean restart) {
    super(paxos, idx, numSevers, numClients,false);
    index = idx;
    roles = new HashMap<Integer, NodeRole>();
    lock = new ReentrantLock();
    if (!restart) {
      leaderID = 1;
      /* initiates replica */
      Map<Integer, Command> committed = new HashMap<Integer, Command>();
      int rpid = bind(index, Constant.REPLICA);
      replica = new Replica(rpid, this, committed, 0);
      
      initialization();
    }
  }
  
  /**
	 * recover the server
	 * @param none
 * @throws InterruptedException 
	 */
  public void recover () throws InterruptedException {
	  // recover as replica
	  int newPid = bind(index, Constant.REPLICA);
	  if (Constant.DEBUG) {
	        System.out.println("Receive replica reply message");
	       // System.out.println(msg);
	      }
	  // ask others for help
	  RecoveryReplyMessage msg = getReplicaReply(newPid);
	  if (Constant.DEBUG) {
	        System.out.println("Receive replica reply message " + msg);
	       // System.out.println(msg);
	      }
	  if (msg != null) {
	     
	      //know the current leader
	      leaderID = msg.leaderID;
	      
	      // Initialize the replica
	      replica = new Replica(newPid, this, msg.decisions, msg.slotNum);
	      // initialize the server
	      initialization();
	    }
  }
  
  /*
   *  recover inquiry function
   *  When server recover, it send to others ask for its 
   */
  public RecoveryReplyMessage getReplicaReply (int src) throws InterruptedException {
	    // send to all replicas with the recover request
	    // System.out.println("ask others for help");
	  while(!shutdown){
		 if (shutdown) {
		       return null;
		 }
	    for (int pid : replicas) {
	      if(pid != src)
	    	  //System.out.println("I am " + src + " ask " + pid +" for help");
	    	  send(pid, new RecoveryRequestMessage(src));
	    }
	    
	    // blocking until receive response
	   
	      Message msg = receive();
	      
	      //System.out.println("I am receiveing recover message ");
	      if (msg instanceof RecoveryReplyMessage) {
	    	  //System.out.println("I am receiveing recover message " + msg);
	        return (RecoveryReplyMessage) msg;
	      }
	    
	    
	  }
	  return null;
  }

  /**
 	 * initialize the server
 	 * @param none
 	 */
  public void initialization () {
      /* initiates acceptor */
    acceptor = new Acceptor(bind(index, Constant.ACCEPTOR), this);

    if (isLeader()) {	 
    	  leader = new Leader(bind(index, Constant.LEADER), this, acceptors, replicas);
    } else {
      leader = null;
    }
    activeClientList = new HashSet<Integer>();
    hbmHandler = new HeartBeatMessageHandler(this);
   
 }

  /**
	 * start the server
	 * @param none
	 */

  public void run () {
    acceptor.start();
    replica.start();
    if (isLeader()) {
      leader.start();
    }
    // start heart beat handler
    hbmHandler.start();
    // while I am working  
    while (!shutdown) {
      Message msg = receive();
      
      
      if (shutdown) {
        return;
      }
      if (msg instanceof HeartBeatMessage) {
          hbmHandler.reset();
          if (Constant.DEBUG) {
             System.out.println ("I am server "+index + ": leader election timer refreshed");
          }
          // learn the new leader from new leader's heart beat message
          int newLeader = msg.src / Constant.INTERLEAVE;
          if (newLeader != leaderID) {
            leaderID = newLeader;
            if (Constant.DEBUG) {
              System.out.println ("New leader detected from heart beat message: " + leaderID);
            }
          }
       } else if (msg != null) {
    	  // update the active list of client
    	  if (msg instanceof RequestMessage){
    		  int source = Math.abs(msg.src);
    		  activeClientList.add(source);
    	  }
          relay(msg);
       }
    }
  }

  /**
	 * return whether is leader
	 * @param none
	 */
  public boolean isLeader() {
    return leaderID == index;
  }

  /**
	 * get the lead id
	 * @param none
	 */
  public int getLeader () {
    return bind(leaderID, Constant.LEADER);
  }

  /**
	 * return the id of changing role
	 * @param none
	 */
  public synchronized int nextId() {
    
    return bind(index, id++);
  }

  /**
   *  Relay message to local process
   */
  public void relay(Message msg) {
	  
    if (roles.containsKey(msg.dst)) {
      if(msg.dst!=-1)
    	  roles.get(msg.dst).deliver(msg);
    } 
  }

  /**
   *  remove the role
   */

  public void remove(int pid) {
    roles.remove(pid);
  }


  

  /**
   * Leader election with basic round robin fashion
   */
  public void leaderElection () {
    leaderID++;
    if (leaderID > numServers) {
      leaderID = 1;
    }
    if (isLeader()) {
      if(Constant.DEBUG){
    	  System.out.println("running leader election " + leaderID + " is new leader");
      }
      
      leader = new Leader(bind(index, Constant.LEADER), this, acceptors, replicas);
       
    	  
		
      leader.start();
    } else {
      leader = null;
    }
  }


  /**
   * Clean shutdown the server, including all the
   * Replica, Acceptor, Leader
   */
  public void cleanShutDown () {
    shutdown = true;
    nc.shutdown();
  }


  /**
   * time bomber message
   * @param number of messages
   */
  public void timeBombLeader (int msgCount) {
	  lock.lock();
	    if (isLeader()) {
	      if (msgCount == 0) {
	        cleanShutDown();
	        
	      } else {
	        messageCount = msgCount;
	      }
	    }
	    
	 
  	 lock.unlock();
  }
  
  /**
   * Send message 
   * to support time bomb leader function from paxos
   * @param msg
 * @throws InterruptedException 
   */
   @Override
   public void send(Message msg) throws InterruptedException {
	   // count the number which is sent by leader
	   // count down only for phase 1a message and phase 2a message
	   if (msg instanceof Phase1aMessage || msg instanceof Phase2aMessage) {
		   lock.lock();
			// should shut down now
		   if (messageCount == 0) {
			   if(Constant.DEBUG)
				   System.out.println("Shutdown Now!");
			   cleanShutDown();
			   return;
		   }
		   // count down message
		   else if (messageCount > 0) {
			   if (msg.src/10 == msg.dst/10) //do not decrement for intraserver messages
				   messageCount--;
			   if(Constant.DEBUG)
				   System.out.println("Shutdown timer: " + messageCount + " message is " + msg);
		   }
		   lock.unlock();
	   }
   // Normal send function 
   // paxos.send(msg);
   
   if (msg instanceof ResponseMessage) {
	      //only message to client 
	      int clientId = Math.abs(msg.dst);
	     
	      // only send to active client
	      // if(activeClientList.contains(clientId)){
	      	  Thread.sleep(delaySending(channel, clientId+numServers));
	    	  nc.sendMsg(clientId+numServers, msg.toString());
	          //paxos.redirectMessage(clientId+numServers, msg);
	    	  if(Constant.DEBUG){
	    		  System.out.println("Delivered to client: " + clientId);
	    	  }
	      //}
	    } else {
	      int serverId = msg.dst / Constant.INTERLEAVE;
	      if(Constant.DEBUG){
	    	  System.out.println("Deliver to server " + serverId + msg.toString());
	      }
	      if(serverId != pid){
	    	 
	    	  Thread.sleep(delaySending(channel, serverId-1));
	    	  nc.sendMsg(serverId -1 , msg.toString());
	    	  //paxos.redirectMessage(serverId-1, msg);
	      }else{
	    	  deliver(msg);
	      }
	      
	    
	     
	    }
 }

  
}
