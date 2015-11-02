package message;

import value.BallotNum;
import value.Constant;
import value.Pvalue;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tian Zhang
 */

public class Phase1bMessage extends Message {
  public BallotNum ballotNum;
  public HashSet<Pvalue> accepted;
  /**
   * constructor
   */
  public Phase1bMessage(int pid, BallotNum b, HashSet<Pvalue> a) {
    src = pid;
    ballotNum = b;
    accepted = a;
  }
  /**
   * constructor
   */
  public Phase1bMessage(String message){
	  String[] split = message.split(Constant.DELIMITER);
	  src = Integer.parseInt(split[1]);
	  dst = Integer.parseInt(split[2]);
	  ballotNum = new BallotNum(split[3]);
	  accepted = str2PvalueSet(split[4]);
  }
  
  /**
   * convert message to string
   */
  @Override
  public String toString() {
    return "Phase 1b Message" + Constant.DELIMITER+src + Constant.DELIMITER + dst +  Constant.DELIMITER+ ballotNum.toString()+ Constant.DELIMITER+ PvalueSet2Str(accepted);
  }
}
