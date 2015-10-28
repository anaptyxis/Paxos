package message;


/**
 * @author Tian Zhang
 */

public class Phase1aMessage extends Message {
  public BallotNum ballotNum;

  public Phase1aMessage(int pid, BallotNum b) {
    src = pid;
    ballotNum = b;
  }

  @Override
  public String toString() {
    return "Phase 1a message  : " + src + " " + ballotNum;
  }
}
