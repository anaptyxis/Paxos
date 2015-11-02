package message;

import java.util.HashSet;
import value.BallotNum;
import value.Constant;
import value.Pvalue;

/**
 * @author Tian Zhang
 * prepare message reply
 */

public class AdoptedMessage extends Message {
  public BallotNum ballotNum;
  public HashSet<Pvalue> accepted = new HashSet<Pvalue>();
  /**
   * constructor
   * @param message string
   */
  public AdoptedMessage(int pid, BallotNum b, HashSet<Pvalue> a) {
    src = pid;
    ballotNum = b;
    accepted = a;
  }
 
  /**
   * constructor
   * @param message string
   */
  public AdoptedMessage(String message){
	  String[] split = message.split(Constant.DELIMITER);
	  src = Integer.parseInt(split[1]);
	  dst = Integer.parseInt(split[2]);
	  ballotNum = new BallotNum(split[3]);
	  accepted = str2PvalueSet(split[4]);
  }
  

  /**
   * convert adopted message to string
   * @param message string
   */
  @Override
  public String toString() {
	
    return "Adopted Message"+ Constant.DELIMITER + src + Constant.DELIMITER+ dst + Constant.DELIMITER + ballotNum.toString()+ Constant.DELIMITER+PvalueSet2Str(accepted) ;
  }
}
