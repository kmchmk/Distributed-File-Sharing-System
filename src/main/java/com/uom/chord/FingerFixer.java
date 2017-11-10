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

    public FingerFixer(Node thisNode) {
        this.thisNode = thisNode;
    }

    @Override
    public void run() {
        while (live) {
            try {
                Thread.sleep(randomIterationTime);
                heartBeatUp(thisNode.getSuccessor());
                hearBeatDown(thisNode.getPredecessor());
            } catch (InterruptedException ex) {
                System.err.println("Error in finger fixer :  " + ex);
            }
        }
    }

    public void kill() {
        this.live = false;
    }


    private void heartBeatUp(Node receiver) {
        if (receiver != null) {
            String heartBeatUp = "HEARTBEAT_UP " + thisNode.getIp() + " " + thisNode.getPort() + " " + thisNode.getUserName() + " 0";
            thisNode.getConnector().send(heartBeatUp, receiver.getIp(), receiver.getPort());
        }
    }

    private void hearBeatDown(Node receiver) {
        if (receiver != null) {
            String heartBeatDown = "HEARTBEAT_DOWN " + thisNode.getIp() + " " + thisNode.getPort() + " "+ thisNode.getUserName() + " 0";
            thisNode.getConnector().send(heartBeatDown, receiver.getIp(), receiver.getPort());
        }
    }
}
