package application;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.relation.Role;

import message.Message;
import message.Phase1aMessage;
import message.Phase2aMessage;

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
  public static boolean debug = false;

  /**
   * Server identifier
   */
  public int index;

  public int leaderID;

  public boolean shutdown = false;

 
  /**
   * Local server process sequence number
   * Reserved ID :
   * Replica : 0
   * Acceptor : 1
   * Leader : 2
   */
  public int id = 3;


  /**
   * Map from process sequence number to Role
   */
  public HashMap<Integer, NodeRole> roles;

  Replica replica;
  Acceptor acceptor;
  Leader leader;


  public Server(int idx, int numSevers, int numClients, Paxos paxos,
                boolean restart) {
    super(paxos, idx, numSevers, numClients);
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

  public void recover () {
   
  }

  public void initialization () {
      /* initiates acceptor */
    acceptor = new Acceptor(bind(index, Constant.ACCEPTOR), this);

    if (isLeader()) {
      leader = new Leader(bind(index, Constant.LEADER), this,
          acceptors, replicas);
    } else {
      leader = null;
    }

   
  }



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

  public boolean isLeader() {
    return leaderID == index;
  }

  public int getLeader () {
    return bind(leaderID, Constant.LEADER);
  }

  public synchronized int nextId() {
    assert (id < Constant.BASE);
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


  public void remove(int pid) {
    roles.remove(pid);
  }


  /**
   *
   * Send message to Network paxos
   * @param msg
   */
  @Override
  public void send(Message msg) {
    if (msg instanceof Phase1aMessage || msg instanceof Phase2aMessage) {
     
    }
    paxos.send(msg);
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
   * Replica, Acceptor, Leader, etc. associated with this server
   */
  public void cleanShutDown () {
    shutdown = true;
  }


  
}
