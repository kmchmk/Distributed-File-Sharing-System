/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM;

import Chord.Node;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Only handles communication. sends and receive messages and delegate handling
 * messages to myNode.
 *
 * @author erang
 */
public class SocketConnector implements Connector {

    Node myNode;
    Thread listner;
    boolean live;

    public SocketConnector(Node myNode) {
        this.myNode = myNode;
    }
    
    public void stop(){
        this.live =  false;
    }

    @Override
    public void send(String OutgoingMessage, String OutgoingIP, int OutgoingPort) {

        try {

            byte[] bytes = OutgoingMessage.getBytes();
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(OutgoingIP), OutgoingPort);

            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);

        } catch (UnknownHostException ex) {
            Logger.getLogger(SocketConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(SocketConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SocketConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void listen(int port) {

        live = true;
        listner = new Thread() {

            @Override
            public void run() {
                try {
                    DatagramSocket socket = new DatagramSocket(port);

                    byte[] buffer = new byte[65536];
                    DatagramPacket incomingPacket = new DatagramPacket(buffer, buffer.length);

                    while (live) {
                        socket.receive(incomingPacket);

                        byte[] data = incomingPacket.getData();

                        String incomingMessage = new String(data, 0, incomingPacket.getLength());
//                        System.out.println("Message Received: " + incomingMessage);

                        myNode.handleMessage(incomingMessage, incomingPacket.getAddress().getHostAddress(), incomingPacket.getPort());

                    }
                } catch (SocketException ex) {
                    System.err.println(ex);
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        };
                
        listner.start();
    }

    @Override
    public void sendToBS(String message) {
        try {
            DatagramSocket sock = new DatagramSocket();

            byte[] b = message.getBytes();

            DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName(myNode.getBSip()), myNode.getBSport());
            sock.send(dp);

            //now receive reply
            //buffer to receive incoming data
            byte[] buffer = new byte[65536];
            DatagramPacket repl = new DatagramPacket(buffer, buffer.length);
            sock.receive(repl);

            byte[] data = repl.getData();
            String reply = new String(data, 0, repl.getLength());

            myNode.handleMessage(reply, repl.getAddress().getHostAddress(), repl.getPort());

        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

}
