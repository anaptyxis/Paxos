package value;


/**
 * @author Tian Zhang
 */
public class Pvalue implements Comparable<Pvalue> {
  public BallotNum ballotNum;
  public int slotNum;
  public Command prop;

  public Pvalue (BallotNum b, int s, Command p) {
    ballotNum = b;
    slotNum = s;
    prop = p;
  }

  public Pvalue (Pvalue pv) {
    ballotNum = pv.ballotNum;
    slotNum = pv.slotNum;
    prop = pv.prop;
  }

  @Override
  public String toString() {
    String rst = "pvalue => " + ballotNum + "slot(" + slotNum + ") " +prop;
    return rst;
  }

  @Override
  public int compareTo(Pvalue pvalue) {
    return ballotNum.compareTo(pvalue.ballotNum);
  }
}
