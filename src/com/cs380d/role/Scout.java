package role;


import java.util.HashSet;
import java.util.Set;

import application.Server;

import message.AdoptedMessage;
import message.Message;
import message.Phase1aMessage;
import message.Phase1bMessage;
import message.PreemptedMessage;

import value.BallotNum;
import value.Pvalue;

/**
 * @author zhangtian
 */
public class Scout extends NodeRole {
  int lambda;
  int[] acceptors;
  BallotNum b;
  HashSet<Integer> waitfor = new HashSet<Integer>();
  HashSet<Pvalue> pvalues = new HashSet<Pvalue>();

  public Scout(int pid, Server ctrl, int lambda, int[] acceptors,
               BallotNum b) {
    super(pid, ctrl);
    this.lambda = lambda;
    this.acceptors = acceptors;
    this.b = b;

    ctrl.roles.put(pid, this);
  }

  @Override
  public void execute () {
    for (int acpt : acceptors) {
      waitfor.add(acpt);
      send(acpt, new Phase1aMessage(pid, b));
    }
    // the server is working
    while (true) {
      Message msg = receive();
      
      if (msg instanceof Phase1bMessage) {
        Phase1bMessage p1b = (Phase1bMessage) msg;
        if (b.compareTo(p1b.ballotNum) == 0) {
          if (waitfor.contains(p1b.src)) {
            pvalues.addAll(p1b.accepted);
            waitfor.remove(p1b.src);
          }
          if (waitfor.size() < (acceptors.length + 1) / 2) {
            Message adopted = new AdoptedMessage(pid, b, pvalues);
            send(lambda, adopted);
            return; // exit();
          }
        } else {
          send(lambda, new PreemptedMessage(pid, p1b.ballotNum));
          return; // exit();
        }
      }
    }
  }


  
}
