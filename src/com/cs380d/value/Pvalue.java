package value;


/**
 * @author Tian Zhang
 */
public class Pvalue implements Comparable<Pvalue> {
  public BallotNum ballotNum;
  public int slotNum;
  public Command prop;
  
  /**
   * constructor
   * @param ballot, slot number command
   */
  public Pvalue (BallotNum b, int s, Command p) {
    ballotNum = b;
    slotNum = s;
    prop = p;
  }

  /**
   * copy constructor
   * @param Pvalue
   */
  public Pvalue (Pvalue pv) {
    ballotNum = pv.ballotNum;
    slotNum = pv.slotNum;
    prop = pv.prop;
  }

  /**
   * constructor
   * @param message string
   */
  public Pvalue(String message){
	  String[] splitStrings = message.split(Constant.PVALUEDELIMITER);
	  ballotNum = new BallotNum(splitStrings[1]);
	  slotNum = Integer.parseInt(splitStrings[2]);
	  prop = new Command(splitStrings[3]);
	  
  }
  
  /**
   * convert pvalue to string
   * @param message string
   */
  @Override
  public String toString() {
    String rst = "pvalue" + Constant.PVALUEDELIMITER + ballotNum.toString() + Constant.PVALUEDELIMITER + slotNum + Constant.PVALUEDELIMITER+prop.toString();
    return rst;
  }

  /**
   * compare 2 pvalue based on ballot comparison
   * @param message string
   */
  @Override
  public int compareTo(Pvalue pvalue) {
    return ballotNum.compareTo(pvalue.ballotNum);
  }
}
