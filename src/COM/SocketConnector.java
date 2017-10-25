/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chanaka
 */
public class SocketConnector implements Connector {

    @Override
    public void listen(int port) {
        new Thread() {
            @Override
            public void run() {
                try {
                    DatagramSocket socket = new DatagramSocket(port);
                    
                    byte[] buffer = new byte[65536];
                    DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                    
                    socket.receive(incoming);
                    
                    byte[] data = incoming.getData();
                    
                    String message = new String(data, 0, incoming.getLength());
                    
                    System.out.println("Message Received: " + message);
                    
                } catch (SocketException ex) {
                    Logger.getLogger(SocketConnector.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SocketConnector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    @Override
    public String sendMessage(String message, String ip, int port) {
        String response = null;
        try {
            byte[] b = message.getBytes();
            DatagramPacket packet = new DatagramPacket(b, b.length, InetAddress.getByName(ip), port);
            
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);

            socket.setSoTimeout(2000);

            byte[] buffer = new byte[65536];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);

            byte[] data = reply.getData();
            response = new String(data, 0, reply.getLength());

        } catch (IOException ex) {
            System.err.println(ex);
        }
        return response;
    }

    @Override
    public void initialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
