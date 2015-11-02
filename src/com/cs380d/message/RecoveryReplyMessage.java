package message;

import value.Constant;

public class RecoveryReplyMessage extends Message{

	  /**
	   * constructor
	   */
		public RecoveryReplyMessage(int pid) {
		    src = pid;
		}
	  /**
	   * constructor
	   */
	  public RecoveryReplyMessage (String message) {
		 String[] split = message.split(Constant.DELIMITER);
		 src = Integer.parseInt(split[1]);
		 dst = Integer.parseInt(split[2]);
	  }
	  
	  /**
	   * convert message to string
	   */
	  @Override
	  public String toString() {
	    return "RecoveryReply Message" + Constant.DELIMITER + src + Constant.DELIMITER+ dst;
	  }

	
}
