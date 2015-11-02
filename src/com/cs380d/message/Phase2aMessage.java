package message;

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
	   dst = Integer.parseInt(split[2]);
	   pv= new Pvalue(split[3]);
  }
  
  /**
   * convert message to string
   */
  @Override
  public String toString () {
    String tmp =  "Phase 2a Messages"+Constant.DELIMITER + src + Constant.DELIMITER + dst +Constant.DELIMITER+ pv.toString();
    if(Constant.DEBUG){
		System.out.println("||||||||||||||||||||||||||||| " + tmp);
	}
    return tmp;
  }

}
