/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Node;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

/**
 *
 * @author Chanaka
 */
public class Node {

    public String name;
    public ArrayList<String> neighborList;//this should be Neighbor object list
    public void init() {

    }

    public void joinNetwork() {
        System.out.println("connecting to bootstrap server...");
        
        
    }

    public void listen() {
        new Thread() {
            public void run() {

                DatagramSocket sock = null;

                try {
                    //1. creating a server socket, parameter is local port number
                    sock = new DatagramSocket(7777);

                    //buffer to receive incoming data
                    byte[] buffer = new byte[65536];
                    DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

                    //2. Wait for an incoming data
                    //communication loop
                    while (true) {
                        sock.receive(incoming);
                        byte[] data = incoming.getData();
                        String message = new String(data, 0, incoming.getLength());

                        //echo the details of incoming data - client ip : client port - client message
                        System.out.println(message);
                        
                        //handle the incoming query

                    }
                } catch (IOException e) {
                    System.err.println("IOException " + e);
                }
                System.out.println("Listening...");
            }
        }.start();
    }
    
    public void search(){
        //search a file
    }
}
