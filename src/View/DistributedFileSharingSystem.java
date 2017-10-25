/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import BootstrapServer.BootstrapServer;
import Chord.NodeImpl;

/**
 *
 * @author Chanaka
 */
public class DistributedFileSharingSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Thread() {
            public void run() {
                BootstrapServer.runBootstrapServer();
            }
        }.start();
        
        NodeImpl node1 = new NodeImpl("kmchmk1", "13.58.202.127", 7771, 7772);
        NodeImpl node2 = new NodeImpl("kmchmk2", "13.58.202.128", 7773, 7774);
        NodeImpl node3 = new NodeImpl("kmchmk3", "13.58.202.129", 7775, 7776);
        node1.unregisterFromNetwork();
        node2.unregisterFromNetwork();
        node3.unregisterFromNetwork();

        node1.registerToNetwork();
        node2.registerToNetwork();
        node3.registerToNetwork();

        new Thread() {
            public void run() {
                try {
                    sleep(2000);
                    System.out.println("Searching for (Glee)");
                    node3.search("Glee");
                } catch (Exception ex) {
                    System.err.println(ex);
                }
            }
        }.start();
    }
}
