package message;

import value.BallotNum;
import value.Constant;

public class Phase2bMessage extends Message {
	 public BallotNum ballotNum;
	 /**
	   * constructor
	   */
	  public Phase2bMessage (int pid, BallotNum b) {
	    src = pid;
	    ballotNum = b;
	  }
	  
	  /**
	   * constructor
	   */
	  public Phase2bMessage (String message) {
		  String[] splitStrings = message.split(Constant.DELIMITER);
		  src = Integer.parseInt(splitStrings[1]);
		  ballotNum = new BallotNum(splitStrings[2]);
	  }
	  
	  
	  
	  /**
	   * convert message to string
	   */
	  @Override
	  public String toString () {
	    return "Phase 2b message" +Constant.DELIMITER+ src + Constant.DELIMITER + ballotNum.toString();
	  }
}
