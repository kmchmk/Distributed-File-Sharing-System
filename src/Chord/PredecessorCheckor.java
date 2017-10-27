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
public class PredecessorCheckor extends Thread {

    private Node thisNode;

    private boolean predecessorHBOK;

    public PredecessorCheckor(Node thisNode) {
        this.thisNode = thisNode;
    }

    public void setPredecessorHBOK(boolean predecessorHBOK) {
        this.predecessorHBOK = predecessorHBOK;
    }

    @Override
    public void run() {
        while (true) {
            try {
                predecessorHBOK = false;
                thisNode.redirectMessage("HB", thisNode.getPredeccessor());
                System.out.println("HB Sent from " + thisNode.getID() + " to predecessor" + thisNode.getPredeccessor().getID());
                Thread.sleep(3 * 60 * 1000);
                if (!predecessorHBOK) {
                    System.out.println("No reply for HB");
                    thisNode.setPredecessor(null);
                }
            } catch (InterruptedException ex) {
            }
        }
    }

}
