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
            @Override
            public void run() {
                BootstrapServer.runBootstrapServer();
            }
        }.start();

        NodeImpl node1 = new NodeImpl("One", "localhost", 3001, "localhost", 55555);
        NodeImpl node2 = new NodeImpl("Two", "localhost", 3002, "localhost", 55555);
        NodeImpl node3 = new NodeImpl("Three", "localhost", 3003, "localhost", 55555);

        node1.registerToNetwork();
        node2.registerToNetwork();
        node3.registerToNetwork();

        node1.unregisterFromNetwork();
        node2.unregisterFromNetwork();
        node3.unregisterFromNetwork();
    }
}
