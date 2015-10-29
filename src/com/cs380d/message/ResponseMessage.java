package message;

import value.Command;


/**
 * @author Tian Zhang
 * Decision Message for Commander
 */

public class ResponseMessage extends Message {
  public int slotNum;
  public Command prop;

  public ResponseMessage(int pid, int s, Command p) {
    src = pid;
    slotNum = s;
    prop = p;
  }

  @Override
  public String toString() {
    return "ResponseMsg: " + src + " " + slotNum + " " + prop;
  }

  public String format () {
    return slotNum + " " + prop.clientID + ": " + prop.text;
  }

}
