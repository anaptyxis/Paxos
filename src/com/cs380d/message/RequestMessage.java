package message;

import value.Command;
import value.Constant;

/**
 * @author zhangtian
 * Decision Message for Commander
 */

public class RequestMessage extends Message {
  public Command prop;
  /**
   * constructor
   */
  public RequestMessage(int pid, Command p) {
    src = pid;
    prop = p;
  }
  /**
   * constructor
   */
  public RequestMessage (String message) {
	 String[] split = message.split(Constant.DELIMITER);
	 src = Integer.parseInt(split[1]);
	 prop = new Command(split[2]);
  }
  
  
  
  /**
   * convert message to string
   */
  @Override
  public String toString() {
    return "Request Message" + Constant.DELIMITER + src + Constant.DELIMITER + prop.toString();
  }
}
