package application;

/**
 * @author zhangtian
 */

import value.Command;

import message.Message;
import message.RequestMessage;
import message.ResponseMessage;

import java.util.HashMap;


public class Client extends Node {
  public static final boolean GUI_on = false;


  int sequenceNum;
  /* client id, negative num for pid */
  int cid;
  HashMap<Integer, ResponseMessage> log;




  public Client(int cid, int numServers, int numClients, Paxos paxos) {
    super(paxos, cid, numServers, numClients);
    this.sequenceNum = 0;
    this.cid = cid;

    log = new HashMap<Integer, ResponseMessage>();
    
  }

  public void run() {
    while (true){
      Message msg = receive();
      if (msg instanceof ResponseMessage) {
        ResponseMessage rspnMsg = (ResponseMessage) msg;
        if (!log.containsKey(rspnMsg.slotNum)) {
          log.put(rspnMsg.slotNum, rspnMsg);
         
        }
      }
    }
  }

  

  public void broadcast (String s) {
    Command prop = new Command(cid, sequenceNum, s);
    sequenceNum++;
    for (int id : replicas) {
      send(id, new RequestMessage(pid, prop));
    }
  }

  

  public String printChatLog () {
    StringBuilder sb = new StringBuilder();
    int slotNum = 0;
    while (log.containsKey(slotNum)) {
      sb.append(log.get(slotNum).format() + "\n");
      slotNum++;
    }
    return sb.toString();
  }


 
}



