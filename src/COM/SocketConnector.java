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

/**
 *
 * @author Chanaka
 */
public class SocketConnector implements Connector {

    private DatagramSocket socket;
    private DatagramPacket packet;

    public void start(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (IOException e) {
            System.err.println("IOException: " + e);
        }
    }

    public String send(String message, String ip, int port) {
        String response = null;
        try {
            byte[] b = message.getBytes();
            packet = new DatagramPacket(b, b.length, InetAddress.getByName(ip), port);
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

    public void listen() {
        sock = new DatagramSocket(myQueryListeningPort);
        byte[] buffer = new byte[65536];
        incoming = new DatagramPacket(buffer, buffer.length
                
        sock.receive(incoming);
        byte[] data = incoming.getData();
        String message = new String(data, 0, incoming.getLength());
        System.out.println("Search query received: " + message);

        //handle the incoming query
        String reply = myNode.executeSearch(message);

        DatagramPacket dp = new DatagramPacket(reply.getBytes(), reply.getBytes().length, incoming.getAddress(), incoming.getPort());
        sock.send(dp);

    }

    @Override
    public String sendMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
