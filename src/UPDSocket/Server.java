/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UPDSocket;

import Chord.NodeImpl;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author Chanaka
 */
public class Server {

    private DatagramSocket sock = null;
    private DatagramPacket incoming;
    private NodeImpl myNode;

    //This is used to run only the server
//    public static void main(String[] args) {
//        start(7778, null);
//        while (true) {
//            listen();
//        }
//    }

    public void start(int myQueryListeningPort, NodeImpl myNode) {
        this.myNode = myNode;
        try {
            sock = new DatagramSocket(myQueryListeningPort);
            byte[] buffer = new byte[65536];
            incoming = new DatagramPacket(buffer, buffer.length);
//            System.out.println("Server:- socket created. Waiting for incoming data...");

        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

    public void listen() {

        new Thread() {
            public void run() {
                System.out.println(myNode.username + " Listening...");
                try {
                    sock.receive(incoming);
                    byte[] data = incoming.getData();
                    String message = new String(data, 0, incoming.getLength());
                    System.out.println("Search query received: " + message);

                    //handle the incoming query
                    String reply = myNode.executeSearch(message);
                    
                    DatagramPacket dp = new DatagramPacket(reply.getBytes() , reply.getBytes().length , incoming.getAddress() , incoming.getPort());
                    sock.send(dp);
                } catch (IOException e) {
                    System.err.println("IOException " + e);
                }

            }
        }.start();

    }
}
