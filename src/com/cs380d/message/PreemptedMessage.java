package message;

import value.BallotNum;
import value.Constant;

/**
 * @author zhangtian
 * PreemptedMsg for Commander
 */

public class PreemptedMessage extends Message {
  public BallotNum ballotNum;
  
  /**
   * constructor
   */
  public PreemptedMessage(int pid, BallotNum b) {
    src = pid;
    ballotNum = b;
  }
  
  /**
   * constructor
   */
  public PreemptedMessage(String message) {
	 String[] split  = message.split(Constant.DELIMITER);
	 src = Integer.parseInt(split[1]);
	 dst = Integer.parseInt(split[2]);
	 ballotNum = new BallotNum(split[3]);
  }
  
  
  /**
   * constructor
   */
  @Override
  public String toString() {
    return "Preempted Message"+ Constant.DELIMITER + src +Constant.DELIMITER + dst + Constant.DELIMITER+ ballotNum.toString();
  }
}
