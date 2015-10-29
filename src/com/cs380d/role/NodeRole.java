package role;

import java.io.IOException;

import message.MessageFIFO;
import message.Message;
import application.Server;
public class NodeRole {
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
		    server.send(msg);
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
