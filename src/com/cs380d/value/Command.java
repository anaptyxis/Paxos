package value;

import java.util.Objects;

/**
 * @author Tian Zhang
 */

public class Command {
  // client identifier
  public int clientID;

  // sequence number 
  public int cid;

  // real message 
  public String text;

  public Command(int k, int c, String t) {
    clientID = k;
    cid = c;
    text = t;
  }


  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Command))
      return false;
    if (obj == this)
      return true;

    Command rhs = (Command) obj;
    return rhs.clientID == clientID  && rhs.cid == cid && rhs.text .equals(text);
  }

  @Override
  public String toString () {
    return "Command is " + clientID + ", " + cid + ", " + text + " ";
  }
}
