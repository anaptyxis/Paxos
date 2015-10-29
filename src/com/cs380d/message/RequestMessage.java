package message;

import value.Command;

/**
 * @author zhangtian
 * Decision Message for Commander
 */

public class RequestMessage extends Message {
  public Command prop;

  public RequestMessage(int pid, Command p) {
    src = pid;
    prop = p;
  }

  @Override
  public String toString() {
    return "Request Message: " + src + " " + prop;
  }
}
