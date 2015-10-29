package role;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import message.AdoptedMessage;
import message.Message;
import message.PreemptedMessage;
import message.ProposeMessage;

import application.Server;

import value.BallotNum;
import value.Command;
import value.Pvalue;

/**
 * @author zhangtian
 */
public class Leader extends NodeRole {
  public BallotNum ballotNum;
  public boolean active;
  public HashMap<Integer, Command> proposals;
  public int[] acceptors;
  public int[] replicas;

  public Leader(int pid, Server s, int[] acceptors, int[] replicas) {
    super(pid, s);
    this.acceptors = acceptors;
    this.replicas = replicas;
    ballotNum = new BallotNum(0, pid);
    active = false;
    proposals = new HashMap<Integer, Command>();
    server.roles.put(pid, this);
  }

  public void execute () {
   
    new Scout(server.nextId(), server, pid, acceptors, ballotNum).start();
    // server is working
    while (true) {
      Message msg = receive();
      
      if (msg instanceof ProposeMessage) {
        ProposeMessage propMsg = (ProposeMessage) msg;
        if (!proposals.containsKey(propMsg.slotNum)) {
          int s = propMsg.slotNum;
          Command p = propMsg.prop;
          proposals.put(s, p);
          if (active) {
            new Commander(server.nextId(), server, pid, acceptors, replicas, new
                Pvalue(ballotNum, s, p)).start();
          }
        }
      }
      if (msg instanceof AdoptedMessage) {
        AdoptedMessage adptMsg = (AdoptedMessage) msg;
        update(adptMsg.accepted);
        for (int s : proposals.keySet()) {
          new Commander(server.nextId(), server, pid, acceptors, replicas, new
              Pvalue(ballotNum, s, proposals.get(s))).start();
        }
        active = true;
      }
      if (msg instanceof PreemptedMessage) {
        PreemptedMessage pmptMsg = (PreemptedMessage) msg;
        if (pmptMsg.ballotNum.compareTo(ballotNum) > 0) {
          active = false;
          ballotNum = new BallotNum(pmptMsg.ballotNum.round, pid);
          new Scout(server.nextId(), server, pid, acceptors, ballotNum).start();
        }
      }
    }
  }

  /*
   * proposals := proposals + pmax(pvals);
   */
  public void update(Set<Pvalue> accepted) {
    Map<Integer, Pvalue> pmax = new HashMap<Integer, Pvalue>();
    for (Pvalue pv : accepted) {
      int s = pv.slotNum;
      if (!pmax.containsKey(s)) {
        pmax.put(s, pv);
      } else {
        if (pv.compareTo(pmax.get(s)) >= 0) {
          pmax.put(s, pv);
        }
      }
    }
    for (int s : pmax.keySet()) {
      proposals.put(s, pmax.get(s).prop);
    }
  }

 


  
}
