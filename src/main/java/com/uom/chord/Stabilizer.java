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
public class Stabilizer {

    private final Node thisNode;
    private boolean live = true;
    private final int waitingTime = 1000;

    public Stabilizer(Node thisNode) {
        this.thisNode = thisNode;
    }

    Thread up = new Thread() {
        public void run() {
            thisNode.echo("Stabilizer up started!");
            while (live) {
                try {
                    if (thisNode.getSuccessor() != null) {
                        thisNode.setSuccessorExists(false);

                        pingUp(thisNode.getSuccessor());

                        Thread.sleep(waitingTime);

                        if (!thisNode.isSuccessorExists()) {
//                    unregister that node from BS server.
                            thisNode.unregisterFromNetwork(thisNode.getSuccessor());
                            thisNode.setSuccessor(null);
                            if (thisNode.getGreateSuccessor() != null) {
                                thisNode.askToUpdateSuccessor(thisNode, thisNode.getGreateSuccessor());
                                thisNode.askToUpdatePredeccessor(thisNode.getGreateSuccessor(), thisNode);
                            }
                        }
                    }
                    Thread.sleep(waitingTime);
                } catch (InterruptedException ex) {
                    System.err.println("Error in stabilizer up: " + ex);
                }
            }
        }
    };

    Thread down = new Thread() {
        public void run() {
            thisNode.echo("Stabilizer down started!");
            while (live) {

                try {
                    if (thisNode.getPredecessor() != null) {
                        thisNode.setPredecessorExists(false);

                        pingDown(thisNode.getPredecessor());

                        Thread.sleep(waitingTime);

                        if (!thisNode.isPredecessorExists()) {
                            thisNode.unregisterFromNetwork(thisNode.getPredecessor());
                            thisNode.setPredecessor(null);
                            if (thisNode.getGreatePredecessor() != null) {
                                thisNode.askToUpdateSuccessor(thisNode.getGreatePredecessor(), thisNode);
                                thisNode.askToUpdatePredeccessor(thisNode, thisNode.getGreatePredecessor());
                            }
                        }
                    }
                    Thread.sleep(waitingTime);
                } catch (InterruptedException ex) {
                    System.err.println("Error in stabilizer down: " + ex);
                }
            }
        }
    };

    public void start() {
        up.start();
        down.start();
    }

    public void kill() {
        this.live = false;
    }

    private void pingUp(Node receiver) {
        if (receiver != null) {
            String ping = "PING_UP " + thisNode.getIp() + " " + thisNode.getPort() + " " + thisNode.getID() + " " + thisNode.getUserName();
            thisNode.getConnector().send(ping, receiver.getIp(), receiver.getPort());
        }
    }

    private void pingDown(Node receiver) {
        if (receiver != null) {
            String ping = "PING_DOWN " + thisNode.getIp() + " " + thisNode.getPort() + " " + thisNode.getID() + " " + thisNode.getUserName();
            thisNode.getConnector().send(ping, receiver.getIp(), receiver.getPort());
        }
    }

}
