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

/**
 * Only handles communication. sends and receive messages and delegate handling
 * messages to myNode.
 *
 * @author erang
 */
public class SocketConnector implements Connector {

//    private DatagramSocket socket;
    Node myNode;
    Thread listner;
    boolean live;

    public SocketConnector(Node myNode) {
        this.myNode = myNode;
    }

    public void stop() {
        this.live = false;
    }

    @Override
    public void send(String OutgoingMessage, String OutgoingIP, int OutgoingPort) {
        System.out.println("Sending message is: " + OutgoingMessage + " (to " + OutgoingIP + ":" + OutgoingPort + ")");
        try {

            byte[] bytes = OutgoingMessage.getBytes();
            //System.out.println(bytes.length);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(OutgoingIP), OutgoingPort);

            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("Message sent...");
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
                    System.out.println("1");
                    while (live) {
                        System.out.println("2");
                        socket.receive(incomingPacket);
                        System.out.println("3");
                        byte[] data = incomingPacket.getData();
                        System.out.println("4");
                        String incomingIP = incomingPacket.getAddress().getHostAddress();
                        System.out.println("5");
                        int incomingPort = incomingPacket.getPort();
                        System.out.println("6");
                        String incomingMessage = new String(data, 0, incomingPacket.getLength());
                        System.out.println("7");
                        System.out.println("Message Received: " + incomingMessage + " (from " + incomingIP + ":" + incomingPort + ")");
                        System.out.println("8");
//                        new Thread() {
//                            public void run() {
                        System.out.println("9");
                        System.out.println("++++++++++++++++" + incomingMessage + "+++++++++++++");
                        System.out.println("10");
                        myNode.handleMessage(incomingMessage, incomingIP);
                        System.out.println("11");
                        System.out.println("Message handled...");
                        System.out.println("12");
//                            }
//                        }.start();
                        System.out.println("13");
                    }
                } catch (SocketException ex) {
                    System.out.println("15");
                    System.err.println(ex);
                } catch (IOException ex) {
                    System.out.println("16");
                    System.err.println(ex);
                }
            }
        };

        listner.start();
    }

    @Override
    public void sendToBS(String message) {
        try {
            DatagramSocket socket = new DatagramSocket();

            byte[] b = message.getBytes();

            DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName(myNode.getBSip()), myNode.getBSport());
            socket.send(dp);

            //now receive reply
            //buffer to receive incoming data
            byte[] buffer = new byte[65536];
            DatagramPacket repl = new DatagramPacket(buffer, buffer.length);
            socket.receive(repl);

            byte[] data = repl.getData();
            String reply = new String(data, 0, repl.getLength());

            myNode.handleMessage(reply, repl.getAddress().getHostAddress());

        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

}
