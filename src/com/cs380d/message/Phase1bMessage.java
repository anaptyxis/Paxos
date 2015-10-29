package message;

import value.BallotNum;
import value.Pvalue;

import java.util.Set;

/**
 * @author Tian Zhang
 */

public class Phase1bMessage extends Message {
  public BallotNum ballotNum;
  public Set<Pvalue> accepted;

  public Phase1bMessage(int pid, BallotNum b, Set<Pvalue> a) {
    src = pid;
    ballotNum = b;
    accepted = a;
  }

  @Override
  public String toString() {
    return "Phase 1b Message : " + src + " " + ballotNum;
  }
}
