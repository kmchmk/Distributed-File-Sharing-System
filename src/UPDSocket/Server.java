/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UPDSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author Chanaka
 */
public class Server{

    static DatagramSocket sock = null;
    static DatagramPacket incoming;

    //This is used to run only the server
    public static void main(String[] args) {
        start(55555);
        while (true) {
            listen();
        }
    }

    public static void start(int port) {

        try {
            sock = new DatagramSocket(port);

            byte[] buffer = new byte[65536];
            incoming = new DatagramPacket(buffer, buffer.length);

            System.out.println("Server:- socket created. Waiting for incoming data...");

        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

    public static String listen() {
        String message;
                try {
                    sock.receive(incoming);
                    byte[] data = incoming.getData();
                    message = new String(data, 0, incoming.getLength());
                    System.out.println("Server:- received from Client: " + message);
                } catch (IOException ex) {
                    System.err.println(ex);
                    message = null;
                }
        return message;
    }
}
