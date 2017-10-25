/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chord;

import COM.SocketConnector;
import COM.Server;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.print.DocFlavor;

/**
 *
 * @author Chanaka
 */
public class NodeImpl {

    private final int maxFingers = 10;
    
    public Server server;
    public SocketConnector client;

    private String ip;
    private int port;
    private String username;

    int myQueryListeningPort;
    int myBSListeningPort;
    
    public FingerTable fingerTable;
    public Map<Integer, String> files;

    public NodeImpl(String username, String ip, , String port, int myQueryListeningPort, int myBSListeningPort) {
        fingerTable = new FingertableImpl(maxFingers);
        files = new HashMap<>();
        
        init(username, IP, myQueryListeningPort, myBSListeningPort);
        populateWithFiles();
    }

    public void init(String username, String IP, int myQueryListeningPort, int myBSListeningPort) {

        try {
            this.username = username;
//            this.myIP = getMyIP();
            this.myIP = IP;
            this.myQueryListeningPort = myQueryListeningPort;
            this.myBSListeningPort = myBSListeningPort;
            this.server = new Server();
            this.client = new SocketConnector();

            server.start(myQueryListeningPort, this);//for search queries
            client.start(myBSListeningPort);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public void registerToNetwork() {
        System.out.println("Registering to network (" + username + ")");
        String registerMessage = " " + "REG" + " " + myIP + " " + Integer.toString(myQueryListeningPort) + " " + username;
        int length = registerMessage.length() + 4;

        registerMessage = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0")) + Integer.toString(length) + registerMessage;

        String reply = client.send(registerMessage, "localhost", 55555);
        System.out.println("Reply from bootstrap server:- " + reply);
        String[] replyList = reply.split(" ");
        if ("REGOK".equals(replyList[1])) {
            server.listen();
            if (replyList[2].equals("0")) {
                System.out.println("This is the first node.");
            } else if (replyList[2].equals("1")) {
                Neighbor firstNeighbor = new Neighbor(replyList[3], Integer.parseInt(replyList[4]));
                neighborList.add(firstNeighbor);
            } else if (replyList[2].equals("2")) {
                Neighbor firstNeighbor = new Neighbor(replyList[3], Integer.parseInt(replyList[4]));
                neighborList.add(firstNeighbor);
                Neighbor secondNeighbor = new Neighbor(replyList[5], Integer.parseInt(replyList[6]));
                neighborList.add(secondNeighbor);
            } else if (replyList[2].equals("9999")) {
                System.out.println(" Failed, there is some error in the command.");
            } else if (replyList[2].equals("9998")) {
                System.out.println("Failed, already registered to you, unregister first.");
            } else if (replyList[2].equals("9997")) {
                System.out.println("Failed, registered to another user, try a different IP and port.");
            } else if (replyList[2].equals("9996")) {
                System.out.println("Failed, can’t register. Bootstrap Server full.");
            }
        } else {
            System.out.println("Error in registration message or the server is offline");
        }

    }

    public void unregisterFromNetwork() {
        String registerMessage = " " + "UNREG" + " " + myIP + " " + Integer.toString(myQueryListeningPort) + " " + username;
        int length = registerMessage.length() + 4;

        registerMessage = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0")) + Integer.toString(length) + registerMessage;

        String reply = client.send(registerMessage, "localhost", 55555);
//        System.out.println("Reply from bootstrap server:- " + reply);
        String[] replyList = reply.split(" ");
        if ("UNROK".equals(replyList[1])) {
            if (replyList[2].equals("0")) {
                System.out.println("Successfully unregistered.");
            } else if (replyList[2].equals("9999")) {
                System.out.println("Error while unregistering. IP and port may not be in the registry or command is incorrect.");
            } else {
                System.out.println("Some error while unregistering.");
            }
        } else if ("Failed".equals(replyList[0])) {
            System.out.println(reply);
        } else {
            System.out.println("Error in unregistration message or the server is offline");
        }
    }

    public void search(String searchString) {
        System.out.println("\n\n");
        String searchQuery = " " + "SER" + " " + myIP + " " + Integer.toString(myQueryListeningPort) + " " + searchString;
        int length = searchQuery.length() + 4;

        searchQuery = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0")) + Integer.toString(length) + searchQuery;
        //asking from the first user
        if (neighborList.size() > 0) {
            //here, IP should be neighborList.get(0).getIP()
            String reply = client.send(searchQuery, "localhost", neighborList.get(0).getPort());
            System.out.println("Reply from first neighbor:- " + reply);
        }
        if (neighborList.size() > 1) {
            //here, IP should be neighborList.get(0).getIP()
            String reply = client.send(searchQuery, "localhost", neighborList.get(1).getPort());
            System.out.println("Reply from second neighbor:- " + reply);
        }
    }

    private String getMyIP() {
        try {
            final DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            return socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException ex) {
            System.err.println(ex);
            return null;
        }
    }

    public void populateWithFiles() {
        ArrayList<String> files = new ArrayList<>(Arrays.asList("Adventures of Tintin", "Jack and Jill", "Glee", "The Vampire Diarie", "King Arthur", "Windows XP", "Harry Potter", "Kung Fu Panda", "Lady Gaga", "Twilight", "Windows 8", "Mission Impossible", "Turn Up The Music", "Super Mario", "American Pickers", "Microsoft Office 2010", "Happy Feet", "Modern Family", "American Idol", "Hacking for Dummies"));
        for (int i = 0; i < 3; i++) {
            int rand = new Random().nextInt(files.size());
            String file = files.get(rand);
            files.remove(rand);
            fileList.add(file);
        }

//        System.out.println(fileList.toString());
    }

    public String executeSearch(String message) {
        System.out.println(message);
        String[] messageList = message.split(" ");

        if ("SER".equals(messageList[1])) {
            String searchString = messageList[4];
            System.out.println("Searching for (" + searchString + ")");
            if (fileList.contains(searchString)) {
                return ("Found - " + searchString);
            } else {
                return ("Not found - " + searchString);
            }
        }
        return "Search query is in wrong format";
    }
}
