/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UPDSocket;

import Node.Node;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author Chanaka
 */
public class Server {

    static DatagramSocket sock = null;
    static DatagramPacket incoming;
    static Node myNode;

    //This is used to run only the server
    public static void main(String[] args) {
        start(7778, null);
        while (true) {
            listen();
        }
    }

    public static void start(int myQueryListeningPort, Node myNode) {
        Server.myNode = myNode;
        try {
            sock = new DatagramSocket(myQueryListeningPort);
            byte[] buffer = new byte[65536];
            incoming = new DatagramPacket(buffer, buffer.length);
            System.out.println("Server:- socket created. Waiting for incoming data...");

        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

    public static void listen() {

        new Thread() {
            public void run() {
                System.out.println("Listening...");
                try {
                    sock.receive(incoming);
                    byte[] data = incoming.getData();
                    String message = new String(data, 0, incoming.getLength());
                    System.out.println(message);

                    //handle the incoming query
                    myNode.executeSearch(message);
                } catch (IOException e) {
                    System.err.println("IOException " + e);
                }

            }
        }.start();

    }
}
