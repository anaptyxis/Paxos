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
	 dst = Integer.parseInt(split[2]);
	 slotNum = Integer.parseInt(split[3]);
	 prop = new Command(split[4]);
			 
}
  
  /**
   * convert message to string
   */
  @Override
  public String toString() {
    return "Propose Message " + Constant.DELIMITER+ src +  Constant.DELIMITER + dst+ Constant.DELIMITER + slotNum + Constant.DELIMITER + prop.toString();
  }
}
