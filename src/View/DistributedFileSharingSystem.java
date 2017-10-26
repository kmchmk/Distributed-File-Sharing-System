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
 * @author erang
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

        NodeImpl node1 = new NodeImpl("One", 3001);
        NodeImpl node2 = new NodeImpl("Two", 3002);
        NodeImpl node3 = new NodeImpl("Three", 3003);
        
        node1.registerToNetwork();
        node2.registerToNetwork();
        node3.registerToNetwork();

        node1.unregisterFromNetwork();
        node2.unregisterFromNetwork();
        node3.unregisterFromNetwork();
    }
}
