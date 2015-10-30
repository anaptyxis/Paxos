package value;

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

  /**
   * constructor
   * @param clientid, sequence number, text
   */
  public Command(int k, int c, String t) {
    clientID = k;
    cid = c;
    text = t;
  }

  /**
   * constructor
   * @param message string
   */
  public Command(String message){
	  String[] split = message.split(Constant.COMMANDDELIMITER);
	  clientID = Integer.parseInt(split[1]);
	  cid = Integer.parseInt(split[2]);
	  text = split[3];
  }
  
  /**
   * see whether 2 command are equal
   * @param another command
   */

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Command))
      return false;
    if (obj == this)
      return true;

    Command rhs = (Command) obj;
    return rhs.clientID == clientID  && rhs.cid == cid && rhs.text .equals(text);
  }

  /**
   * convert command to string
   * @param a command
   */
  @Override
  public String toString () {
    return "Command" + Constant.COMMANDDELIMITER+ clientID + Constant.COMMANDDELIMITER + cid + Constant.COMMANDDELIMITER + text ;
  }
}
