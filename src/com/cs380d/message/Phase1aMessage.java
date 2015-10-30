package message;

import value.BallotNum;
import value.Constant;


/**
 * @author Tian Zhang
 */

public class Phase1aMessage extends Message {
  public BallotNum ballotNum;
  /**
   * constructor
   */
  public Phase1aMessage(int pid, BallotNum b) {
    src = pid;
    ballotNum = b;
  }
  /**
   * constructor
   * @param message string
   */
  public Phase1aMessage (String message) {
	  String[] split = message.split(Constant.DELIMITER);
	  src  = Integer.parseInt(split[1]);
	  ballotNum  = new BallotNum(split[2]);
  }
  
  /**
   * convert message to string
   */
  @Override
  public String toString() {
    return "Phase 1a message"+Constant.DELIMITER + src + Constant.DELIMITER + ballotNum.toString();
  }
}
