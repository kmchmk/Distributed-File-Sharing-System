/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UPDSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Chanaka
 */
public class Client {

    static DatagramSocket sock = null;
    static InetAddress host;
    static int port = 55555;

    //this is used to run only the client
    public static void main(String[] args) {
        start();
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Client:- enter a message to send : ");
            try {
                String s = (String) cin.readLine();
                send(s);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    public static void start() {

        try {
            sock = new DatagramSocket(7778);
            host = InetAddress.getByName("localhost");
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

    public static void send(String message) {
        try {
            byte[] b = message.getBytes();
            DatagramPacket dp = new DatagramPacket(b, b.length, host, port);
            sock.send(dp);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
