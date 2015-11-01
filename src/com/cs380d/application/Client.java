package application;

/**
 * @author zhangtian
 * The class for Client 
 * extends Node
 */

import value.Command;

import message.Message;
import message.RequestMessage;
import message.ResponseMessage;

import java.util.HashMap;


public class Client extends Node {
  //sequence number for message
  int sequenceNum;
  // client id
  int cid;
  HashMap<Integer, ResponseMessage> log;
  /**
   * Default constructor
   * @param none
   * dequeue the FIFO
   */
  public Client(int cid, int numServers, int numClients, Paxos paxos) {
    super(paxos, cid, numServers, numClients,true);
    this.sequenceNum = 0;
    this.cid = cid;

    log = new HashMap<Integer, ResponseMessage>();
    
  }
  
  /**
   * Start the thread
   * @param none
   * just receive response message 
   * update the log
   */
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

  /**
   * Broadcast the message
   * @param string text
   * Configured Command with id sequenceNumber
   * send out request to every replicas
   */
  public void broadcast (String s) {
    Command prop = new Command(cid, sequenceNum, s);
    sequenceNum++;
    for (int id : replicas) {
      send(id, new RequestMessage(pid, prop));
    }
  }

  
  /**
   * print the chat log
   * @param none
   * return the log
   * 
   */
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



