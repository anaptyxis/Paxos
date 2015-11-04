package role;

import java.util.Map;

import value.Command;
import value.Constant;
import message.MessageFIFO;
import message.Message;
import application.Server;
public class NodeRole extends Thread{
	int pid;
	Server server;
	MessageFIFO msgQueue;
	 /**
	 * Default constructor for NodeRole, which is super class for commander, leader......
	 * @param Id, server
	 */
	  public NodeRole (int id, Server s) {
	    pid = id;
	    server = s;
	    msgQueue = new MessageFIFO();
	  }
	  
	  
	  /**
		 * server send message
		 * @param dst, message
		 */
	  public void send (int dst, Message msg) {
		    msg.dst = dst;
		    //server send the message
		    try {
				server.send(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
	  
	  /**
		 * server broadcast message
		 * @param  nessage
		 */
	  public void broadcast( Message msg) {
		  for (int i = 1; i <= server.numServers; i++) {
		      send(i * Constant.INTERLEAVE, msg);
		    }
	  }
	  
	  
	  /**
		 * server execute
		 * @param none
		 */
	  public void execute() {
		  
		  
	  }
	  
	  
	  /**
		 * server run
		 * @param none
		 */
	  public void run() {
	    execute();
	    server.remove(pid);
	    
	  }
	  
	 
	  /**
		 * server deliver message
		 * @param message
		 */
	  public void deliver (Message msg) {
	    msgQueue.enqueue(msg);
	  }
	  
	  
	  /**
		 * server receive message
		 * @param none
		 */
	  public Message receive () {
		return msgQueue.dequeue();
	  }


	  /**
	   * convert dicision map to string
	   * @param decision map
	   */
	   public String decMap2Str(Map<Integer, Command> dec){
		   if( dec == null || dec.isEmpty()) return "nothing";
		   String result = "";
		   for (Map.Entry<Integer, Command> entry : dec.entrySet()) {
			    Integer key = entry.getKey();
			    Command value = entry.getValue();
			    String tmpString = Integer.toString(key) + Constant.DECISIONDELIMITER+value.toString();
			    result = result + tmpString + Constant.DECISIONLISTDELIMITER;
			}
		   //System.out.println("result is " + result);
		   if(!result.equals("")){
				  int length = result.length();
				  return result.substring(0,length-1);
			}else{
				  return "nothing";
			}
	   }
}
