package message;

import value.Command;


/**
 * @author zhangtian
 * Decision Message for Commander
 */

public class ProposeMessage extends Message {
  public int slotNum;
  public Command prop;

  public ProposeMessage(int pid, int s, Command p) {
    src = pid;
    slotNum = s;
    prop = p;
  }

  @Override
  public String toString() {
    return "Propose Message: " + src + " " + slotNum + " " + prop;
  }
}
