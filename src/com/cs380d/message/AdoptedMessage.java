package message;

import java.util.HashSet;
import java.util.Set;

import javax.print.attribute.HashAttributeSet;

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;

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
	  ballotNum = new BallotNum(split[2]);
	  accepted = str2PvalueSet(split[3]);
  }
  

  /**
   * convert adopted message to string
   * @param message string
   */
  @Override
  public String toString() {
	
    return "Adopted Message"+ Constant.DELIMITER + src + Constant.DELIMITER + ballotNum.toString()+ Constant.DELIMITER+PvalueSet2Str(accepted) ;
  }
}
