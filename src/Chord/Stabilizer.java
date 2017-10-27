/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chord;

/**
 *
 * @author Irfad Hussain
 */
public class Stabilizer extends Thread {

    private final Node thisNode;
    private Node newPredessor = null;
    private boolean predMsgSent;
    private boolean waitingForSuccessor;

    public Stabilizer(Node thisNode) {
        this.thisNode = thisNode;
        this.setName("Stabilizer-Thread");
    }

    public void setNewPredessor(Node newPredessor) {
        this.newPredessor = newPredessor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                System.out.println("Stabilizing...");
                
                if (thisNode.getSuccessor() == null) {
                    waitingForSuccessor =  true;
                }
                if (!waitingForSuccessor && !predMsgSent) {
                    String msg = "GET_PRED " + thisNode.getIp() + " " + thisNode.getPort();
                    thisNode.routeMessge(msg, thisNode.getSuccessor().getID());
                    predMsgSent = true;
                    
                }
                Thread.sleep(5 * 60 * 1000);
                
            } catch (InterruptedException ex) {
                if (predMsgSent) {
                    if (newPredessor != null) {
                        System.out.println("GET_PRED Success");
                        int predOfSuccessorID = newPredessor.getID();
                        int currentSuccessorID = thisNode.getSuccessor().getID();
                        // this node and successor are in opposite side of 0
                        if (currentSuccessorID < thisNode.getID()) {
                            if ((thisNode.getID() < predOfSuccessorID && predOfSuccessorID < NodeImpl.MAX_NODES) || (0 <= predOfSuccessorID && predOfSuccessorID < currentSuccessorID)) {
                                thisNode.setSuccessor(newPredessor);
                                System.out.println("Update Successor of" + thisNode.getID() + " to " + predOfSuccessorID);
                            }
                        } else if (thisNode.getID() < predOfSuccessorID && predOfSuccessorID < currentSuccessorID) { // normal successor
                            thisNode.setSuccessor(newPredessor);
                            System.out.println("Update Successor of" + thisNode.getID() + " to " + predOfSuccessorID);
                        }
                        String msg = "NOTIFY_S " + thisNode.getIp() + " " + thisNode.getPort();
                        thisNode.routeMessge(msg, thisNode.getSuccessor().getID());
                        newPredessor = null;
                        predMsgSent = false;
                    }
                }
            }
        }
    }

}
