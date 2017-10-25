/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UPDSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 *
 * @author Chanaka
 */
public class Client {

    private DatagramSocket sock = null;
//    static InetAddress host;

    //this is used to run only the client
//    public static void main(String[] args) {
//        start(7777);
//        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
//        while (true) {
//            System.out.println("Client:- enter a message to send : ");
//            try {
//                String s = (String) cin.readLine();
//                System.out.println(send(s, "localhost", 7778));
//            } catch (IOException ex) {
//                System.err.println(ex);
//            }
//        }
//    }

    public void start(int port) {
        try {
            sock = new DatagramSocket(port);
//            host = InetAddress.getByName("localhost");
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

    public String send(String message, String IP, int port) {
        String response = null;
        try {
            byte[] b = message.getBytes();
            DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName(IP), port);
            sock.send(dp);
            
            byte[] buffer = new byte[65536];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            sock.setSoTimeout(2000);
            try{
            sock.receive(reply);
            byte[] data = reply.getData();
            response = new String(data, 0, reply.getLength());
            }
            catch (SocketTimeoutException e){
                response = "Failed to unregister - node is not registered.";
            }

            //echo the details of incoming data - client ip : client port - client message
//            echo(reply.getAddress().getHostAddress() + " : " + reply.getPort() + " - " + s);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return response;
    }
}
