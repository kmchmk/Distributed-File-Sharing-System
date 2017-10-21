/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import UPDSocket.Client;
import UPDSocket.Server;

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
                Server.start();
                Server.listen();
            }
        }.start();
        
        new Thread() {
            public void run() {
                Client.start();
                Client.send("This is the message");
            }
        }.start();
    }

}
