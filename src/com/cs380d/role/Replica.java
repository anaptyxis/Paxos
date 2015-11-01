package role;

import java.util.HashMap;
import java.util.Map;

import message.DecisionMessage;
import message.Message;
import message.ProposeMessage;
import message.RequestMessage;
import message.ResponseMessage;

import application.Server;

import value.Command;

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
    decisions = initial;
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
    while (true) {
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
     
    }
  }

 
}
