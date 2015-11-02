package message;

import java.util.Map;

import value.Constant;
import value.Command;

public class RecoveryReplyMessage extends Message{
	  public int slotNum;
	  public int leaderID;
	  public Map<Integer, Command> decisions;
	  /**
	   * constructor
	   */
	  public RecoveryReplyMessage(int pid, int s, Map<Integer, Command> d, int leader) {
		    src = pid;
		    slotNum = s;
		    decisions = d;
		    leaderID = leader;

	  }
	  /**
	   * constructor
	   */
	  public RecoveryReplyMessage (String message) {
		 String[] split = message.split(Constant.DELIMITER);
		 src = Integer.parseInt(split[1]);
		 dst = Integer.parseInt(split[2]);
		 slotNum = Integer.parseInt(split[3]);
		 decisions = str2DecMap(split[4]);
		 leaderID = Integer.parseInt(split[5]);
	  }
	  
	  /**
	   * convert message to string
	   */
	  @Override
	  public String toString() {
	    return "RecoveryReply Message" + Constant.DELIMITER + src + Constant.DELIMITER+ dst + Constant.DELIMITER+ 
	    		slotNum + Constant.DELIMITER + decMap2Str(decisions)+Constant.DELIMITER+leaderID;
	  }

	
}
