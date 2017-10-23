/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Node;

import UPDSocket.Client;
import UPDSocket.Server;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chanaka
 */
public class Node {

    public String username;
    public String myIP;
    int myQueryListeningPort;
    int myBSListeningPort;
    public ArrayList<String> neighborList;//this should be Neighbor object list

    public Node(String username, String IP, int myQueryListeningPort, int myBSListeningPort) {
        init(username, IP, myQueryListeningPort, myBSListeningPort);
    }

    public void init(String username, String IP, int myQueryListeningPort, int myBSListeningPort) {

        try {
            this.username = username;
//            this.myIP = getMyIP();
            this.myIP = IP;
            this.myQueryListeningPort = myQueryListeningPort;
            this.myBSListeningPort = myBSListeningPort;

            Server.start(myQueryListeningPort);//for search queries
            Client.start(myBSListeningPort);
        } catch (Exception ex) {
            System.out.println("Error");
            System.err.println(ex);
        }
    }

    public void registerToNetwork() {
        System.out.println("connecting to bootstrap server...");
        String registerMessage = " " + "REG" + " " + myIP + " " + Integer.toString(myQueryListeningPort) + " " + username;
        int length = registerMessage.length() + 4;

        registerMessage = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0")) + Integer.toString(length) + registerMessage;

        String reply = Client.send(registerMessage, 55555);
        System.out.println("Reply from bootstrap server:- " + reply);
        String[] replyList = reply.split(" ");
        if ("REGOK".equals(replyList[1])) {
            if (replyList[2].equals("0")) {
                System.out.println("This is the first node.");
            } else if (replyList[2].equals("1")) {
                Neighbor firstNeighbor = new Neighbor(replyList[3], Integer.parseInt(replyList[4]));
            } else if (replyList[2].equals("2")) {
                Neighbor firstNeighbor = new Neighbor(replyList[3], Integer.parseInt(replyList[4]));
                Neighbor secondNeighbor = new Neighbor(replyList[5], Integer.parseInt(replyList[6]));
            } else if (replyList[2].equals("9999")) {
                System.out.println(" Failed, there is some error in the command.");
            } else if (replyList[2].equals("9998")) {
                System.out.println("Failed, already registered to you, unregister first.");
            } else if (replyList[2].equals("9997")) {
                System.out.println("Failed, registered to another user, try a different IP and port.");
            } else if (replyList[2].equals("9996")) {
                System.out.println("Failed, can’t register. Bootstrap Server full.");
            }
            Server.listen();
        } else {
            System.out.println("Error in registration message or the server is offline");
        }

    }

    public void unregisterFromNetwork() {
        String registerMessage = " " + "UNREG" + " " + myIP + " " + Integer.toString(myQueryListeningPort) + " " + username;
        int length = registerMessage.length() + 4;

        registerMessage = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0")) + Integer.toString(length) + registerMessage;

        String reply = Client.send(registerMessage, 55555);
//        System.out.println("Reply from bootstrap server:- " + reply);
        String[] replyList = reply.split(" ");
        if ("UNROK".equals(replyList[1])) {
            if (replyList[2].equals("0")) {
                System.out.println("Successfully unregistered.");
            } else if (replyList[2].equals("9999")) {
                System.out.println("Error while unregistering. IP and port may not be in the registry or command is incorrect.");
            }
        } else {
            System.out.println("Error in unregistration message or the server is offline");
        }
    }

    public void listen() {
        Server.listen();
    }

    public void doSomething() {
        System.out.println("Doing something...");
    }

    public void search() {
        //search a file
    }

    private String getMyIP() {
        try {
            final DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            return socket.getLocalAddress().getHostAddress();
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }
}
