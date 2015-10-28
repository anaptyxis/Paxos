package message;

/**
 * @author Tian Zhang
 */

public abstract class Message {

  public int src;
  public int dst;

  public String print () {
    String rst = "\n" + src + " -> " + dst + "\t" + "\n";
    return rst;
  }
}

