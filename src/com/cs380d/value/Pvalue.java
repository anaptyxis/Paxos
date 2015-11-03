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
	  if(Constant.DEBUG){
		  System.out.println("Config the pvalue is " + message);
	  }
	  String[] splitStrings = message.split(Constant.PVALUEDELIMITER);
	  if(Constant.DEBUG){
		  for(int i = 0 ; i < splitStrings.length;i++){
			  System.out.println("Config the pvalue is split value " + splitStrings[i]);
		  }
		  
	  }
	  ballotNum = new BallotNum(splitStrings[0]);
	  slotNum = Integer.parseInt(splitStrings[1]);
	  prop = new Command(splitStrings[2]);
	  
  }
  
  /**
   * convert pvalue to string
   * @param message string
   */
  @Override
  public String toString() {
    String rst = ballotNum.toString() + Constant.PVALUEDELIMITER + slotNum + Constant.PVALUEDELIMITER+prop.toString();
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
