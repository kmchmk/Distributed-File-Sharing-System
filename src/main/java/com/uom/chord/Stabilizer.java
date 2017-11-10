/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uom.chord;

/**
 *
 * @author Chanaka
 */
public class Stabilizer extends Thread {

    private final Node thisNode;
    private boolean live = true;
    private long waitingTime;

    public Stabilizer(Node thisNode) {
        this.thisNode = thisNode;
    }

    @Override
    public void run() {

//        try {
//            Thread.sleep(waitingTime);
//            if (!thisNode.isSuccessorExists()) {
//                if (thisNode.getGreateSuccessor() != null) {
//                    thisNode.askToUpdateSuccessor(thisNode, thisNode.getGreateSuccessor());
//                    thisNode.askToUpdatePredeccessor(thisNode.getGreateSuccessor(), thisNode);
//                }
//            }
//            if (!thisNode.isPredecessorExists()) {
//                if (thisNode.getGreatePredecessor() != null) {
//                    thisNode.askToUpdateSuccessor(thisNode.getGreatePredecessor(), thisNode);
//                    thisNode.askToUpdatePredeccessor(thisNode, thisNode.getGreatePredecessor());
//                }
//            }
//                for (int i = 1; i < 3; i++) {
//                    if (temporarySuccessors[i] == null) {
//                        if (temporarySuccessors[i - 1] != null && thisNode.getSuccessors()[i + 1] != null) {
//                            //send correct network request to langama ekaata.
//                            thisNode.askToUpdateSuccessor(temporarySuccessors[i - 1], temporarySuccessors[i + 1]);
//                            thisNode.askToUpdatePredeccessor(temporarySuccessors[i + 1], temporarySuccessors[i - 1]);
//                        } else {
//                            thisNode.updateExistingSuccessor(i, null);
//                        }
//                    }
//                    if (temporaryPredeccessors[i] == null) {
//                        if (temporaryPredeccessors[i - 1] != null && thisNode.getPredeccessors()[i + 1] != null) {
//                            //send correct network request to langama ekaata.
//
//                            thisNode.askToUpdateSuccessor(temporarySuccessors[i - 1], temporarySuccessors[i + 1]);
//                            thisNode.askToUpdatePredeccessor(temporarySuccessors[i + 1], temporarySuccessors[i - 1]);
//                        } else {
//                            thisNode.updateExistingPredecessor(i, null);
//                        }
//                    }
//                }
//        } catch (InterruptedException ex) {
//            System.err.println("Error in stabilizer: " + ex);
//        }
    }

    public void kill() {
        this.live = false;
    }

    private void pingUp(Node receiver) {
        String ping = "PING_UP " + thisNode.getIp() + " " + thisNode.getPort() + " " + thisNode.getID() + " " + thisNode.getUserName();
        thisNode.getConnector().send(ping, receiver.getIp(), receiver.getPort());
    }

    private void pingDown(Node receiver) {
        String ping = "PING_DOWN " + thisNode.getIp() + " " + thisNode.getPort() + " " + thisNode.getID() + " " + thisNode.getUserName();
        thisNode.getConnector().send(ping, receiver.getIp(), receiver.getPort());
    }

}
