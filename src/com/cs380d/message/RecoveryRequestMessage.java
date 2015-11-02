package message;


import value.Command;
import value.Constant;

/**
 * @author zhangtian
 * RecoveryRequestMessage for Replica
 */

public class RecoveryRequestMessage extends Message {


	  /**
	   * constructor
	   */
		public RecoveryRequestMessage(int pid) {
		    src = pid;
		}
	  /**
	   * constructor
	   */
	  public RecoveryRequestMessage (String message) {
		 String[] split = message.split(Constant.DELIMITER);
		 src = Integer.parseInt(split[1]);
		 dst = Integer.parseInt(split[2]);
	  }
		  
	  	  
	  /**
	   * convert message to string
	   */
	  @Override
	  public String toString() {
	    return "RecoveryReq Message" + Constant.DELIMITER + src + Constant.DELIMITER+ dst;
	  }


}
