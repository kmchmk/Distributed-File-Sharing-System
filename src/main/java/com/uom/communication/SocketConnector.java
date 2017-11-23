/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uom.communication;

import com.uom.chord.Node;
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

    Node myNode;
    Thread listner;
    boolean live;

    DatagramSocket sendSocket;

    public SocketConnector(Node myNode) {

        try {
            sendSocket = new DatagramSocket();
        } catch (SocketException ex) {
            System.err.println("Error 0001");
            System.err.println(ex);
        }
        this.myNode = myNode;
    }

    public void kill() {
        this.live = false;
    }

    @Override
    public void send(String OutgoingMessage, String OutgoingIP, String OutgoingPort) {
        send(OutgoingMessage, OutgoingIP, Integer.parseInt(OutgoingPort));
    }

    @Override
    public void send(String OutgoingMessage, Node destination) {
        if (destination != null) {
            send(OutgoingMessage, destination.getIp(), destination.getPort());
        }
    }

    @Override
    public void send(String OutgoingMessage, String OutgoingIP, int OutgoingPort) {

//        myNode.echo("Sending: " + OutgoingMessage);
        try {
            byte[] bytes = OutgoingMessage.getBytes();
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(OutgoingIP), OutgoingPort);

            sendSocket.send(packet);
            myNode.getGUI().updateSendCount();
//        myNode.getGUI().echo("Message sent...");
        } catch (IOException ex) {
            System.err.println("Error 00002");
            System.err.println(ex);
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
//                        String incomingIP = incomingPacket.getAddress().getHostAddress();
//                        int incomingPort = incomingPacket.getPort();
                        String incomingMessage = new String(data, 0, incomingPacket.getLength());
//                        myNode.echo("Received: " + incomingMessage);
                        myNode.getGUI().updateReceiveCount();
                        myNode.handleMessage(incomingMessage);
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
        new Thread() {
            public void run() {

                try {
                    DatagramSocket socket = new DatagramSocket();

                    byte[] b = message.getBytes();

                    DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName(myNode.getBSip()), myNode.getBSport());
                    socket.send(dp);
                    myNode.getGUI().updateSendCount();
                    myNode.echo("Sending to BS: " + message);
                    //now receive reply
                    //buffer to receive incoming data
                    byte[] buffer = new byte[65536];
                    socket.setSoTimeout(10000);
                    DatagramPacket repl = new DatagramPacket(buffer, buffer.length);
                    socket.receive(repl);

                    byte[] data = repl.getData();
                    String reply = new String(data, 0, repl.getLength());
                    myNode.getGUI().updateReceiveCount();
                    myNode.handleMessage(reply);

                } catch (IOException e) {
                    System.err.println("IOException " + e);
                }
            }
        }.start();
    }

}
