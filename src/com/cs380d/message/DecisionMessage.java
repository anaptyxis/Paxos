package message;

import value.Command;
import value.Constant;

/**
 * @author Tian Zhang
 * Decision Message for Commander
 */

public class DecisionMessage extends Message {
  public int slotNum;
  public Command prop;
  
  /**
   * Default constructor
   */
  public DecisionMessage(int pid, int s, Command p) {
    src = pid;
    slotNum = s;
    prop = p;
  }

  /**
   * Constructor 
   * @param message string
   */
  
  public DecisionMessage(String message) {
	 String[] split = message.split(Constant.DELIMITER);
	 src = Integer.parseInt(split[1]);
	 dst = Integer.parseInt(split[2]);
	 slotNum = Integer.parseInt(split[3]);
	 prop= new Command(split[4]);
  }
  
  /**
   * Convert message to string
   */
  @Override
  public String toString() {
    return "Decision message " +Constant.DELIMITER+ src + Constant.DELIMITER+ dst+ Constant.DELIMITER + slotNum + Constant.DELIMITER + prop.toString();
  }
}
