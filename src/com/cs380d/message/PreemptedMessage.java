package message;

import value.BallotNum;

/**
 * @author zhangtian
 * PreemptedMsg for Commander
 */

public class PreemptedMessage extends Message {
  public BallotNum ballotNum;

  public PreemptedMessage(int pid, BallotNum b) {
    src = pid;
    ballotNum = b;
  }

  @Override
  public String toString() {
    return "Preempted Message: " + src + " " + ballotNum;
  }
}
