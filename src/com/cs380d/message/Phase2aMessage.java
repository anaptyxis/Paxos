package message;

import com.sun.corba.se.impl.resolver.SplitLocalResolverImpl;

import value.Constant;
import value.Pvalue;

/**
 * @author Tian Zhang
 */
public class Phase2aMessage extends Message {
  public Pvalue pv;

  /**
   * constructor
   */
  public Phase2aMessage (int pid, Pvalue p) {
    src = pid;
    pv = p;
  }
  /**
   * constructor
   */
  public Phase2aMessage (String message) {
	   String[] split = message.split(Constant.DELIMITER);
	   src = Integer.parseInt(split[1]);
	   pv= new Pvalue(split[2]);
  }
  
  /**
   * convert message to string
   */
  @Override
  public String toString () {
    return "Phase 2a Message"+Constant.DELIMITER + src + Constant.DELIMITER + pv.toString();
  }

}
