package message;

import value.Command;
import value.Constant;


/**
 * @author Tian Zhang
 * Decision Message for Commander
 */

public class ResponseMessage extends Message {
  public int slotNum;
  public Command prop;
  /**
   * constructor
   */
  public ResponseMessage(int pid, int s, Command p) {
    src = pid;
    slotNum = s;
    prop = p;
  }
  /**
   * constructor
   */
  public ResponseMessage (String message){
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
    return "Response Message " + Constant.DELIMITER+ src + Constant.DELIMITER + slotNum + Constant.DELIMITER + prop.toString();
  }
  
  
  /**
   * return the format of response
   * which is to be print for test
   */
  public String format () {
    return slotNum + " " + prop.clientID + ": " + prop.text;
  }

}
