package application;

import message.HeartBeatMessage;
import role.Leader;
import value.Constant;

/*
 * Heart beat message sender 
 * send out heart beat message
 * @author Tian Zhang
 */
public class HeartBeatSender extends Thread {
	
	 public Server server;
	 public Leader leader;
	 /*
	  * Constructor
	  * 
	  */
	 public HeartBeatSender(Server s, Leader l) {
		  server = s;
		  leader = l;
	 }
	 
	 
	 
	 /*
	  * run function for thread start
	  *  @param
	  */
	 public void run(){
		 // while leader is working , send out heart beat message 
		 while(!server.shutdown){
			  HeartBeatMessage msg = new HeartBeatMessage(leader.getLeaderID());
			  leader.broadcast(msg);
			  // then wait some time to send another heart beat message
			  try {
				Thread.sleep(Constant.HEARTBEATMESSAGEGAP);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 }
	 
	 
	 
}
