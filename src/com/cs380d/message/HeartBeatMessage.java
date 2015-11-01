package message;

import value.Constant;

/*
 *  heart beat message
 *  @author Tian Zhang
 */
public class HeartBeatMessage extends Message {
	      
	
		  /*
	       *  Default constructor
	       *   
	       */

		  public HeartBeatMessage(int pid) {
			// TODO Auto-generated constructor stub
		    src = pid;
		  }
		  
		  /*
		   *  constructor
		   *  @param string message
		   *   
		   */
		  
		  public HeartBeatMessage (String message){
			   String[] split = message.split(Constant.DELIMITER);
			   src = Integer.parseInt(split[1]);
		  }

		  @Override
		  public String toString() {
		    return "HeartBeat message "+ Constant.DELIMITER + src;
		  }
}
