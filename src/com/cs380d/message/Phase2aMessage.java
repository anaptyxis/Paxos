package message;

import value.Pvalue;

/**
 * @author Tian Zhang
 */
public class Phase2aMessage extends Message {
  public Pvalue pv;

  public Phase2aMessage (int pid, Pvalue p) {
    src = pid;
    pv = p;
  }

  @Override
  public String toString () {
    return "Phase 2a Message: " + src + " " + pv;
  }

}
