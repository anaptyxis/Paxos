package message;

import value.Command;
import value.Constant;


/**
 * @author zhangtian
 * Decision Message for Commander
 */

public class ProposeMessage extends Message {
  public int slotNum;
  public Command prop;
  /**
   * constructor
   */
  public ProposeMessage(int pid, int s, Command p) {
    src = pid;
    slotNum = s;
    prop = p;
  }
  /**
   * constructor
   */
  public ProposeMessage (String message ) {
	 String[] split = message.split(Constant.DELIMITER);
	 src = Integer.parseInt(split[1]);
	 slotNum = Integer.parseInt(split[2]);
	 prop = new Command(split[3]);
			 
}
  
  /**
   * convert message to string
   */
  @Override
  public String toString() {
    return "Propose Message " + Constant.DELIMITER+ src + Constant.DELIMITER + slotNum + Constant.DELIMITER + prop.toString();
  }
}
