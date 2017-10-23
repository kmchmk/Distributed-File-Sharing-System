/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

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

        Node node1 = new Node("kmchmk1", "13.58.202.127", 7771, 7772);
        node1.registerToNetwork();
//        node1.unregisterFromNetwork();

        Node node2 = new Node("kmchmk2", "13.58.202.128", 7773, 7774);
        node2.registerToNetwork();
//        node2.unregisterFromNetwork();

        Node node3 = new Node("kmchmk3", "13.58.202.129", 7775, 7776);
        node3.registerToNetwork();
//        node3.unregisterFromNetwork();

//        new Thread() {
//            public void run() {
//                Server.start();
//                Server.listen();
//            }
//        }.start();
//        
//        new Thread() {
//            public void run() {
//                Client.start();
//                Client.send("This is the message");
//            }
//        }.start();
    }

}
