package role;

import java.util.HashSet;

import message.Message;
import message.Phase1aMessage;
import message.Phase1bMessage;
import message.Phase2aMessage;
import message.Phase2bMessage;

import application.Server;

import value.BallotNum;
import value.Pvalue;

public class Acceptor extends NodeRole{
	  private BallotNum ballotNum = new BallotNum(-1, 0);
	  private HashSet<Pvalue> accepted = new HashSet<Pvalue>();

	  public Acceptor(int id, Server s) {
	    super(id, s);
	    //put the role into application Node
	    s.roles.put(pid, this);
	  }
	  
	 
	  public void execute () {
	 
	      Message msg = receive();
	     // if the system is not working 
	      
	      
	      
	     // if the system is working 
	      if (msg instanceof Phase1aMessage) {
	        Phase1aMessage p1a = (Phase1aMessage) msg;
	        BallotNum b = p1a.ballotNum;
	        if (b.compareTo(ballotNum) > 0) {
	          ballotNum.set(b);
	        }
	        send(p1a.src, getMsg(1));
	      } else if (msg instanceof Phase2aMessage) {
	        Phase2aMessage p2a = (Phase2aMessage) msg;
	        BallotNum b = p2a.pv.ballotNum;
	        if (b.compareTo(ballotNum) >= 0) {
	          ballotNum.set(b);
	          accepted.add(new Pvalue(p2a.pv));
	        }
	        send(p2a.src, getMsg(2));
	      }
	    
	  }

	  public Message getMsg (int phase) {
	    switch (phase) {
	      case 1:
	        return new Phase1bMessage(pid, ballotNum, new HashSet<Pvalue> (accepted));
	      case 2:
	        return new Phase2bMessage(pid, ballotNum);
	      default:
	        return null;
	    }
	  }
}
