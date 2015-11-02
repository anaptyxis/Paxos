package value;

/**
 * @author Tian Zhang
 */

public class BallotNum implements Comparable<BallotNum> {
  public int round;
  public int pid;
  
  /**
   * Default constructor
   * @param round, pid 
   */
  public BallotNum (int r, int p) {
    round = r;
    pid = p;
  }

  /**
   * constructor
   * @param message string
   */
  public BallotNum(String message) {
	  if(Constant.DEBUG){
		  System.out.println("Config the ballot is " + message);
	  }
	  String[] splits = message.split(Constant.BALLOTDELIMITER);
	  round = Integer.parseInt(splits[1]);
	  pid = Integer.parseInt(splits[2]);
  }

  /**
   * set the ballot
   * @param ballot
   */
  
  public void set (BallotNum ballot) {
    round = ballot.round;
    pid = ballot.pid;
  }

  /**
   * compare 2 ballot
   * @param ballot
   */
  public int compareTo (BallotNum ballot) {
    if (ballot.round != ballot.round) {
      return this.round - ballot.round;
    } else {
      return this.pid - ballot.pid;
    }
  }

  /**
   * convert ballot to string
   * @param message string
   */
  @Override
  public String toString() {
    return "Ballot" + Constant.BALLOTDELIMITER+round + Constant.BALLOTDELIMITER + pid ;
  }
}
