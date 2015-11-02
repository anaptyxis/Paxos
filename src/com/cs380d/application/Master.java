package application;

import java.util.Scanner;

/**
 * @author Tian Zhang
 */
public class Master {

  public static void main(String [] args) {
    Scanner scan = new Scanner(System.in);
    int numNodes = 0, numClients = 0;
    Paxos paxos = null;

    while (scan.hasNextLine()) {
      String [] inputLine = scan.nextLine().split(" ");
      int clientIndex, nodeIndex;
      //System.out.println(inputLine[0]);
      if (inputLine[0].equals("start")) {
        numNodes = Integer.parseInt(inputLine[1]);  // the number of server
        numClients = Integer.parseInt(inputLine[2]);
              /*
               * start up the right number of nodes and clients, and store the
               *  connections to them for sending further commands
               */
        paxos = new Paxos(numNodes, numClients);

      } else if (inputLine[0].equals("sendMessage")) {
        clientIndex = Integer.parseInt(inputLine[1]);
        String message = "";
        for (int i = 2; i < inputLine.length; i++) {
          message += inputLine[i];
          if (i != inputLine.length - 1) {
            message += " ";
          }
        }
        	/*
        	 * Instruct the client specified by clientIndex to send the message
        	 * to the proper paxos node
        	 */
        paxos.clientBroadcast(clientIndex, message);

              

      } else if (inputLine[0].equals("printChatLog")) {
        clientIndex = Integer.parseInt(inputLine[1]);
              /*
               * Print out the client specified by clientIndex's chat history
               * in the format described on the handout.
               */
        System.out.print(paxos.printLog(clientIndex));

      } else if (inputLine[0].equals("allClear")) {/*
               * Ensure that this blocks until all messages that are going to
               * come to consensus in PAXOS do, and that all clients have heard
               * of them
               */
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        paxos.killAll();
      } else if (inputLine[0].equals("crashServer")) {
        nodeIndex = Integer.parseInt(inputLine[1]);
              /*
               * Immediately crash the server specified by nodeIndex
               */

        paxos.crashServer(nodeIndex);

      } else if (inputLine[0].equals("restartServer")) {
        nodeIndex = Integer.parseInt(inputLine[1]);
              /*
               * Restart the server specified by nodeIndex
               */
        paxos.reviveServer(nodeIndex);

      } else if (inputLine[0].equals("timeBombLeader")) {
        int numMessages = Integer.parseInt(inputLine[1]);
              /*
               * Instruct the leader to crash after sending the number of paxos
               * related messages specified by numMessages
               */
        for (int i = 0; i < numNodes; i++) {
             paxos.serverList[i].timeBombLeader(numMessages);
        }
      }
    }
    System.exit(1);
  }
}