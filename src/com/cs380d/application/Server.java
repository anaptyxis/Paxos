package application;

import java.util.HashMap;
import java.util.Map;
import message.Message;
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
   * Map from process id to NodeRole
   */
  public HashMap<Integer, NodeRole> roles;

  Replica replica;
  Acceptor acceptor;
  Leader leader;
   
  /**
 	 * Default constructor for Server
 	 * @param Id, number of server, number of client, the paxos environment, whether it is recover thread
 	 */

  public Server(int idx, int numSevers, int numClients, Paxos paxos,boolean restart) {
    super(paxos, idx, numSevers, numClients,false);
    index = idx;
    roles = new HashMap<Integer, NodeRole>();

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
	 */
  public void recover () {
   
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
    


    while (!shutdown) {
      Message msg = receive();
      if (shutdown) {
        return;
      }
      if (msg != null) {
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
      roles.get(msg.dst).deliver(msg);
      //System.out.println(msg.print());
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
      leader = new Leader(bind(index, Constant.LEADER), this,
          acceptors, replicas);
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
  }


  
}
