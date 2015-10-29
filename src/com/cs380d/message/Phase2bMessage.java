package message;

import value.BallotNum;

public class Phase2bMessage extends Message {
	 public BallotNum ballotNum;

	  public Phase2bMessage (int pid, BallotNum b) {
	    src = pid;
	    ballotNum = b;
	  }

	  @Override
	  public String toString () {
	    return "Phase 2b message: " + src + " " + ballotNum;
	  }
}
