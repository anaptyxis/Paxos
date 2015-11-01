package role;

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
		    if (Constant.DEBUG){
		    	System.out.println("I am sendint to" + Integer.toString(dst)+ " " + msg);
		    }
		    server.send(msg);
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



}
