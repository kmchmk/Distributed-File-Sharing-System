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
    private final int randomIterationTime = 2000;
    private final int waitingTime = 1000;

    public FingerFixer(Node thisNode) {
        this.thisNode = thisNode;
    }

    @Override
    public void run() {
        while (live) {
            try {
                Thread.sleep(randomIterationTime);
                thisNode.clearSuccessors();
                if (thisNode.getSuccessor() != null) {
                    String heartBeatUp = "HEARTBEAT_UP " + thisNode.getIp() + " " + thisNode.getPort() + " " + thisNode.getID() + " " + thisNode.getUserName() + " 0";
                    thisNode.getConnector().send(heartBeatUp, thisNode.getSuccessor().getIp(), thisNode.getSuccessor().getPort());
                }

                thisNode.clearPredecessors();
                if (thisNode.getPredecessor() != null) {
                    String heartBeatDown = "HEARTBEAT_DOWN " + thisNode.getIp() + " " + thisNode.getPort() + " " + thisNode.getID() + " " + thisNode.getUserName() + " 0";
                    thisNode.getConnector().send(heartBeatDown, thisNode.getPredecessor().getIp(), thisNode.getPredecessor().getPort());
                }
                Thread.sleep(waitingTime);

                for (int i = 1; i < 4; i++) {
                    if (thisNode.getSuccessors()[i] == null) {
                        if (thisNode.getSuccessors()[i - 1] != null) {
                            //send correct network request to langama ekaata.
                            String correctNetworkUp = "CORRECT_NETWORK_UP";
                            thisNode.getConnector().send(correctNetworkUp, thisNode.getSuccessors()[i - 1].getIp(), thisNode.getSuccessors()[i - 1].getPort());
                        }
                    }
                    if (thisNode.getPredeccessors()[i] == null) {
                        if (thisNode.getPredeccessors()[i - 1] != null) {
                            //send correct network request to langama ekaata.
                            String correctNetworkDown = "CORRECT_NETWORK_DOWN";
                            thisNode.getConnector().send(correctNetworkDown, thisNode.getPredeccessors()[i - 1].getIp(), thisNode.getPredeccessors()[i - 1].getPort());
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

}
