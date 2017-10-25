/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import BootstrapServer.BootstrapServer;
import Node.Node;

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
        
        Node node1 = new Node("kmchmk1", "13.58.202.127", 7771, 7772);
        Node node2 = new Node("kmchmk2", "13.58.202.128", 7773, 7774);
        Node node3 = new Node("kmchmk3", "13.58.202.129", 7775, 7776);
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
