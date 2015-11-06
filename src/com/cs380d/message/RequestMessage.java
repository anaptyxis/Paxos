package message;

import value.Command;
import value.Constant;

/**
 * @author zhangtian
 * Response Message to Client from Replica
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
	 dst = Integer.parseInt(split[2]);
	 prop = new Command(split[3]);
  }
  
  
  
  /**
   * convert message to string
   */
  @Override
  public String toString() {
    return "Request Message" + Constant.DELIMITER + src + Constant.DELIMITER+ dst+ Constant.DELIMITER + prop.toString();
  }
}
