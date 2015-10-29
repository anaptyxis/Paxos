package application;

import value.Constant;
import message.Message;
import message.MessageFIFO;

/**
 * @author Tian Zhang
 */
public class Node  {
  public int pid;
  public int numServers;
  private MessageFIFO msgQueue;
  public Paxos paxos;

  int[] acceptors;
  int[] replicas;
  public int[] clients;

  public Node(Paxos p, int id, int numServers, int numClients) {
    paxos = p;
    pid = id;
    this.numServers = numServers;
    msgQueue = new MessageFIFO();

    acceptors = new int[numServers];
    replicas = new int[numServers];
    for (int i = 1; i <= numServers; i++) {
      acceptors[i - 1] = bind(i, Constant.ACCEPTOR);
      replicas[i - 1]  = bind(i, Constant.REPLICA);
    }
    clients = new int[numClients];
    for (int i = 0; i < numClients; i++) {
      clients[i]  = i;
    }

  }

  /**
   * Send message to paxos
   * @param msg
   */
  public void send(Message msg) {
    paxos.send(msg);
  }

  public void send(int dst, Message msg) {
    msg.dst = dst;
    send(msg);
  }

  public void deliver (Message msg) {
    msgQueue.enqueue(msg);
  }

  public Message receive () {
    return msgQueue.dequeue();
  }

  public int bind(int sid, int pid) {
    return sid * Constant.BASE + pid;
  }
}
