/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Chord.NodeImpl;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erang
 */
public class DistributedFileSharingSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        new Thread("BS Thread"){
//            @Override
//            public void run() {
//                BootstrapServer.runBootstrapServer();
//            }
//        }.start();

        new Thread("DHT") {
            @Override
            public void run() {
                try {
                    
                    Thread.sleep(2000);
                    
                    NodeImpl node1 = new NodeImpl("Four", 3004);
//                    System.out.println("My ip is: " + node1.getIp());
//                    NodeImpl node2 = new NodeImpl("Two", 3002);
                    //NodeImpl node3 = new NodeImpl("Three", 3003);
                    
                    node1.registerToNetwork();
//                    Thread.sleep(2000);
                    
//                    node2.registerToNetwork();
//                    Thread.sleep(2000);
                    
//                    node3.registerToNetwork();
//                    Thread.sleep(2000);
                    
                    //node1.unregisterFromNetwork();
                    //Thread.sleep(2000);
                    
                    //node2.unregisterFromNetwork();
                    //node3.unregisterFromNetwork();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DistributedFileSharingSystem.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();

//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    sleep(60000);
//                } catch (InterruptedException ex) {
//                    System.err.println(ex);
//                }
//                System.exit(0);
//            }
//        }.start();
    }
}
