package value;

/**
 * @author Tian Zhang
 */

public class BallotNum implements Comparable<BallotNum> {
  public int round;
  public int pid;

  public BallotNum (int r, int p) {
    round = r;
    pid = p;
  }


  public void set (BallotNum ballot) {
    round = ballot.round;
    pid = ballot.pid;
  }

  public int compareTo (BallotNum ballot) {
    if (ballot.round != ballot.round) {
      return this.round - ballot.round;
    } else {
      return this.pid - ballot.pid;
    }
  }

  @Override
  public String toString() {
    return "b(" + round + ", " + pid + ") ";
  }
}
