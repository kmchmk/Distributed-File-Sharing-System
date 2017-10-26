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
public class FingerFixer implements Runnable {

    private static int fingerToFixNext = -1;

    private Node thisNode;

    public FingerFixer(Node thisNode) {
        this.thisNode = thisNode;
    }

    @Override
    public void run() {
        while (true) {
            try {
                fingerToFixNext++;
                if (fingerToFixNext == NodeImpl.MAX_FINGERS) {
                    fingerToFixNext = 0;
                }
                thisNode.getFingerTable().updateEntry(fingerToFixNext, thisNode.findSuccessorOf(thisNode.getID() + (int) Math.pow(2, fingerToFixNext)));
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }
        }
    }

}
