package role;

import java.util.HashMap;
import java.util.Map;

import message.DecisionMessage;
import message.Message;
import message.ProposeMessage;
import message.RecoveryReplyMessage;
import message.RequestMessage;
import message.ResponseMessage;
import message.RecoveryRequestMessage;

import application.Server;

import value.Command;
import value.Constant;

/**
 * @author zhangtian
 */
public class Replica extends NodeRole {
  public int slotNum;
  public Map<Integer, Command> proposals;
  public Map<Integer, Command> decisions;

  public Replica(int pid, Server svr, Map<Integer, Command> initial, int
      slotCount) {
    super(pid, svr);
    slotNum = slotCount;
    proposals = new HashMap<Integer, Command>();
    if(initial == null) decisions = new HashMap<Integer,Command>();
    else decisions = initial;
    server.roles.put(pid, this);
  }

  /**
   *  get the max unused slot number
   */
  public int getMaxSlotNum() {
    int s = 0;
    while (proposals.containsKey(s) || decisions.containsKey(s)) {
      s++;
    }
    return s;
  }
  
  /*
   * propose the command with lowest unused slot
   */
  public void propose(Command p) {
	// check whether this is already a decision message 
    if (!decisions.containsValue(p)) {
      int s = getMaxSlotNum();
      proposals.put(s, p);
      send(server.getLeader(), new ProposeMessage(pid, s, p));
    }else{
    	//System.out.println("Note : propose has already been decided");
    }
  }

  /*
   * after receive the decision
   * send response to client
   */

  public void perform(Command p) {
    // if it has already performed the command
	// only send out new command
    for (int s = 0; s < slotNum; s++) {
      if (decisions.get(s).equals(p)) {
         slotNum++;
        return;
      }
    }
    //send the message to client
    for (int cid : server.clients) {
      send(cid, new ResponseMessage(pid, slotNum, p));
    }
    slotNum++;
  }

  @Override
  public void execute () {
	// server is working
    while (!server.shutdown) {
      // if not working , just return
      if(server.shutdown){
    	  return;
      }
      Message msg = receive();
    
      // receive a request from client
      // propose with lowest unused slot
      if (msg instanceof RequestMessage) {
        RequestMessage rqstMsg = (RequestMessage) msg;
        propose(rqstMsg.prop);
      }
      // receive a decision message
      if (msg instanceof DecisionMessage) {
        DecisionMessage dsnMsg = (DecisionMessage) msg;
        decisions.put(dsnMsg.slotNum, dsnMsg.prop);
        while (decisions.containsKey(slotNum)) {
          // if decision and proposal is not match, propose it again
          if (proposals.containsKey(slotNum) && !decisions.get(slotNum).equals(proposals.get(slotNum))) {
            propose(proposals.get(slotNum));
          }
          // perform the decision
          // send to client
          perform(decisions.get(slotNum));
        }
      }
      
      // receive a recovery request message from recovering replica
      if (msg instanceof RecoveryRequestMessage) {
    	  //build replica state to send
    	  //slotnum, decisions
    	  //System.out.print("what I receive is " + msg);
    	  RecoveryReplyMessage srm = new RecoveryReplyMessage(pid, slotNum,
    			  new HashMap<Integer, Command>(decisions), server.leaderID);
    	 // System.out.println("helps "+msg.src+" to recover " + srm);
    	  send(msg.src, srm);
    	  
      }
     
    }
  }
  
  //returns leader ID
  public int recover(int[] replicas) {
	  for (int id = 0; id < replicas.length; id++)
		  if (id != this.pid)
			  send(id, new RecoveryRequestMessage(this.pid));
	  Message msg = receive();
	  if (msg instanceof RecoveryReplyMessage) {
		  RecoveryReplyMessage recRepMsg = (RecoveryReplyMessage) msg;
		  this.slotNum = recRepMsg.slotNum;
		  this.decisions = recRepMsg.decisions;
		  return recRepMsg.leaderID;
	  } else {
		  System.err.println("Recovering Replica received non-recovery message.");
		  return -1;
	  }
		  
  }

 
}
