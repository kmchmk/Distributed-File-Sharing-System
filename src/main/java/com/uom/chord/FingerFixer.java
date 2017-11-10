/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uom.chord;

/**
 *
 * @author Irfad Hussain
 */
public class FingerFixer extends Thread {

    private final Node thisNode;
    private boolean live = true;
    private final int randomIterationTime = 5000;
    private final int waitingTime = 2000;
    public Node[] temporaryPredeccessors;
    public Node[] temporarySuccessors;

    public FingerFixer(Node thisNode) {
        this.thisNode = thisNode;
        temporaryPredeccessors = new Node[4];
        temporarySuccessors = new Node[4];
    }

    @Override
    public void run() {
        while (live) {
            try {
                Thread.sleep(randomIterationTime);
                clearTemporarySuccessorList();
                if (thisNode.getSuccessor() != null) {
                    String heartBeatUp = "HEARTBEAT_UP " + thisNode.getIp() + " " + thisNode.getPort() + " " + thisNode.getID() + " " + thisNode.getUserName() + " 0";
                    thisNode.getConnector().send(heartBeatUp, thisNode.getSuccessor().getIp(), thisNode.getSuccessor().getPort());
                }

                clearTemporaryPredecessorList();
                if (thisNode.getPredecessor() != null) {
                    String heartBeatDown = "HEARTBEAT_DOWN " + thisNode.getIp() + " " + thisNode.getPort() + " " + thisNode.getID() + " " + thisNode.getUserName() + " 0";
                    thisNode.getConnector().send(heartBeatDown, thisNode.getPredecessor().getIp(), thisNode.getPredecessor().getPort());
                }
                
                Thread.sleep(waitingTime);

                for (int i = 1; i < 3; i++) {
                    if (temporarySuccessors[i] == null) {
                        if (temporarySuccessors[i - 1] != null && thisNode.getSuccessors()[i + 1] != null) {
                            //send correct network request to langama ekaata.
                            thisNode.askToUpdateSuccessor(temporarySuccessors[i - 1], temporarySuccessors[i + 1]);
                            thisNode.askToUpdatePredeccessor(temporarySuccessors[i + 1], temporarySuccessors[i - 1]);
                       }
                        else{
                            thisNode.updateExistingSuccessor(i, null);
                        }
                    }
                    if (temporaryPredeccessors[i] == null) {
                        if (temporaryPredeccessors[i - 1] != null && thisNode.getPredeccessors()[i + 1] != null) {
                            //send correct network request to langama ekaata.

                            thisNode.askToUpdateSuccessor(temporarySuccessors[i - 1], temporarySuccessors[i + 1]);
                            thisNode.askToUpdatePredeccessor(temporarySuccessors[i + 1], temporarySuccessors[i - 1]);
                       }
                        else{
                            thisNode.updateExistingPredecessor(i, null);
                        }
                    }
                }
            } catch (InterruptedException ex) {
                System.err.println("Error in finger fixer :  " + ex);
            }
        }
    }

    public void kill() {
        this.live = false;
    }

    private void clearTemporaryPredecessorList() {
        for (int i = 0; i < 4; i++) {
            temporaryPredeccessors[i] = null;
        }
    }

    private void clearTemporarySuccessorList() {
        for (int i = 0; i < 4; i++) {
            temporarySuccessors[i] = null;
        }
    }

}
