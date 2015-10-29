package message;

import java.util.Set;

import value.BallotNum;
import value.Pvalue;

/**
 * @author Tian Zhang
 * prepare message reply
 */

public class AdoptedMessage extends Message {
  public BallotNum ballotNum;
  public Set<Pvalue> accepted;

  public AdoptedMessage(int pid, BallotNum b, Set<Pvalue> a) {
    src = pid;
    ballotNum = b;
    accepted = a;
  }

  @Override
  public String toString() {
    return "Adopted Message: " + src + " " + ballotNum;
  }
}
