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

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        DatagramSocket sock = null;
        int port = 7777;
        String s;

        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

        try {
            sock = new DatagramSocket();

            InetAddress host = InetAddress.getByName("localhost");

//            while (true) {
            //take input and send the packet
            echo("Client:- enter a message to send : ");
            s = (String) cin.readLine();
            byte[] b = s.getBytes();

            echo("Client:- sending message...");
            DatagramPacket dp = new DatagramPacket(b, b.length, host, port);
            sock.send(dp);

            //now receive reply
            //buffer to receive incoming data
            byte[] buffer = new byte[65536];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            sock.receive(reply);

            byte[] data = reply.getData();
            s = new String(data, 0, reply.getLength());

            //echo the details of incoming data - client ip : client port - client message
//                echo(reply.getAddress().getHostAddress() + " : " + reply.getPort() + " - " + s);
            echo("Client:- reply from server: " + s);
//            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

    //simple function to echo data to terminal
    public static void echo(String msg) {
        System.out.println(msg);
    }
}
