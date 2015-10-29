package message;

import value.Command;

/**
 * @author Tian Zhang
 * Decision Message for Commander
 */

public class DecisionMessage extends Message {
  public int slotNum;
  public Command prop;

  public DecisionMessage(int pid, int s, Command p) {
    src = pid;
    slotNum = s;
    prop = p;
  }

  @Override
  public String toString() {
    return "DecisionMsg: " + src + " " + slotNum + " " + prop;
  }
}
