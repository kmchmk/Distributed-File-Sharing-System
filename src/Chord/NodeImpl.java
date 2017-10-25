/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chord;

import COM.SocketConnector;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Chanaka
 */
public class NodeImpl implements Node {

    private final int maxFingers = 10;

    private final String BSip;
    private final int BSport;

    private final String ip;
    private final int port;
    private final String username;
    private final int id;

    public SocketConnector socketConnector;

    public FingerTable fingerTable;

    private List<SimpleNeighbor> neighborList;

    public Map<Integer, String> files;
    private Node successor;
    private Node predecessor;

    public NodeImpl(String username, int port, String BSip, int BSport) {
        fingerTable = new FingertableImpl(maxFingers);
        files = new HashMap<>();
        neighborList = new ArrayList<>();

        this.username = username;
        this.ip = getMyIP();
        this.port = port;
        this.BSip = BSip;
        this.BSport = BSport;
        this.id = this.username.hashCode();

        this.socketConnector = new SocketConnector();
    }

    public NodeImpl(String username, int port, int BSport) {
        this(username, port, getMyIP(), BSport);
    }

    @Override
    public String getIp() {
        return this.ip;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    public boolean joinNetwork() {
        this.successor = findSuccessor();
//        this.successor.updatePredecessor(this);

        this.predecessor = findPredecessor();
//        this.predecessor.updateSuccessor(this);

        return updateFingerTable();
    }

    private Node findSuccessor() {
        Node successorNeighbor = null;
        if (neighborList.size() > 0) {
            successorNeighbor = askClosestSuccessor(neighborList.get(0));
        }
        if (neighborList.size() > 1) {
            Node tempNeighbor = askClosestSuccessor(neighborList.get(1));
            //have to check what is the closest

        }
        return successorNeighbor;
    }

    private Node findPredecessor() {
        Node predecessorNeighbor = null;
        if (neighborList.size() > 0) {
            predecessorNeighbor = askClosestPredecessor(neighborList.get(0));
        }
        if (neighborList.size() > 1) {
            Node tempNeighbor = askClosestPredecessor(neighborList.get(1));
            //have to check what is the closest

        }
        return predecessorNeighbor;
    }

    private Node askClosestPredecessor(SimpleNeighbor neighbor) {
        String reply = socketConnector.sendMessage("CP " + Integer.toString(id), neighbor.getIp(), neighbor.getPort());
        String[] replyList = reply.split(" ");
        if ("PRED".equals(replyList[0])) {
            String predecessorIP = replyList[1];
            int predecessorPort = Integer.parseInt(replyList[2]);
//            return new SimpleNeighbor(predecessorIP, predecessorPort);
            return null;
        } else {
            return null;
        }
    }

    private Node askClosestSuccessor(SimpleNeighbor neighbor) {
        String reply = socketConnector.sendMessage("CS " + Integer.toString(id), neighbor.getIp(), neighbor.getPort());
        String[] replyList = reply.split(" ");
        if ("SUCC".equals(replyList[0])) {
            String successorIP = replyList[1];
            int successorPort = Integer.parseInt(replyList[2]);
//            return new SimpleNeighbor(successorIP, successorPort);
            return null;
        } else {
            return null;
        }
    }

    private boolean updateFingerTable() {

        return true;
    }

    private static String getMyIP() {
        try {
            final DatagramSocket socket = new DatagramSocket();
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            return socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException ex) {
            System.err.println(ex);
            return null;
        }
    }

    public void registerToNetwork() {
        System.out.println("Registering to network (" + username + ")");
        String registerMessage = " " + "REG" + " " + ip + " " + Integer.toString(port) + " " + username;
        int length = registerMessage.length() + 4;

        registerMessage = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0")) + Integer.toString(length) + registerMessage;

        String reply = socketConnector.sendMessage(registerMessage, "localhost", BSport);
        System.out.println("Reply from bootstrap server:- " + reply);

        String[] replyList = reply.split(" ");

        if ("REGOK".equals(replyList[1])) {

            switch (replyList[2]) {
                case "0":
                    System.out.println("This is the first node.");
                    break;
                case "1": {
                    SimpleNeighbor firstNeighbor = new SimpleNeighbor(replyList[3], Integer.parseInt(replyList[4]));
                    neighborList.add(firstNeighbor);
                    break;
                }
                case "2": {
                    SimpleNeighbor firstNeighbor = new SimpleNeighbor(replyList[3], Integer.parseInt(replyList[4]));
                    neighborList.add(firstNeighbor);
                    SimpleNeighbor secondNeighbor = new SimpleNeighbor(replyList[5], Integer.parseInt(replyList[6]));
                    neighborList.add(secondNeighbor);
                    break;
                }
                case "9999":
                    System.out.println(" Failed, there is some error in the command.");
                    break;
                case "9998":
                    System.out.println("Failed, already registered to you, unregister first.");
                    break;
                case "9997":
                    System.out.println("Failed, registered to another user, try a different IP and port.");
                    break;
                case "9996":
                    System.out.println("Failed, can’t register. Bootstrap Server full.");
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("Error in registration message or the server is offline");
        }

    }

    public void unregisterFromNetwork() {
        String registerMessage = " " + "UNREG" + " " + ip + " " + Integer.toString(port) + " " + username;
        int length = registerMessage.length() + 4;

        registerMessage = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0")) + Integer.toString(length) + registerMessage;

        String reply = socketConnector.sendMessage(registerMessage, "localhost", BSport);
//        System.out.println("Reply from bootstrap server:- " + reply);
        String[] replyList = reply.split(" ");

        if ("UNROK".equals(replyList[1])) {
            switch (replyList[2]) {
                case "0":
                    System.out.println("Successfully unregistered.");
                    break;
                case "9999":
                    System.out.println("Error while unregistering. IP and port may not be in the registry or command is incorrect.");
                    break;
                default:
                    System.out.println("Some error while unregistering.");
                    break;
            }
        } else if ("Failed".equals(replyList[0])) {
            System.out.println(reply);
        } else {
            System.out.println("Error in unregistration message or the server is offline");
        }
    }

    public void search(String searchString) {
        System.out.println("\n\n");
        String searchQuery = " " + "SER" + " " + ip + " " + Integer.toString(port) + " " + searchString;
        int length = searchQuery.length() + 4;

        searchQuery = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0")) + Integer.toString(length) + searchQuery;
        //asking from the first user
        if (neighborList.size() > 0) {
            //here, IP should be neighborList.get(0).getIP()
            String reply = socketConnector.sendMessage(searchQuery, "localhost", neighborList.get(0).getPort());
            System.out.println("Reply from first neighbor:- " + reply);
        }
        if (neighborList.size() > 1) {
            //here, IP should be neighborList.get(0).getIP()
            String reply = socketConnector.sendMessage(searchQuery, "localhost", neighborList.get(1).getPort());
            System.out.println("Reply from second neighbor:- " + reply);
        }
    }

    public void populateWithFiles() {
        ArrayList<String> filelist = new ArrayList<>(Arrays.asList(
                "Adventures of Tintin",
                "Jack and Jill",
                "Glee",
                "The Vampire Diarie",
                "King Arthur",
                "Windows XP",
                "Harry Potter",
                "Kung Fu Panda",
                "Lady Gaga",
                "Twilight",
                "Windows 8",
                "Mission Impossible",
                "Turn Up The Music",
                "Super Mario",
                "American Pickers",
                "Microsoft Office 2010",
                "Happy Feet",
                "Modern Family",
                "American Idol",
                "Hacking for Dummies"));

        for (int i = 0; i < 3; i++) {
            int rand = new Random().nextInt(filelist.size());
            String file = filelist.get(rand);
            filelist.remove(rand);
            filelist.add(file);
        }
    }

    public String searchFileList(String queryMessage) {
        System.out.println(queryMessage);
        String[] messageList = queryMessage.split(" ");

        if ("SER".equals(messageList[1])) {

            String queryWord = messageList[4];
            System.out.println("Searching for (" + queryWord + ")");

            if (files.containsKey(queryWord.hashCode())) {
                return ("Found - " + queryWord);
            } else {
                return ("Not found - " + queryWord);
            }
        }
        return "Search query is in wrong format";
    }

    @Override
    public boolean leaveNetwork() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize() {
        populateWithFiles();
        this.socketConnector.listen(port);
    }

    @Override
    public void routeMessge(String message, int key) {
        if (this.id == key) {
            //handle request
        } else {
            Node next;
            if (fingerTable.searchEntries(key)) {
                next = fingerTable.getNode(key);
            } else if (fingerTable.getClosestPredecessorToKey(key) != null) {
                next = fingerTable.getClosestPredecessorToKey(key);

            } else {
                next = this.successor;
            }
            redirectMessage(message, next);
        }
    }

    @Override
    public String search() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node getSuccessor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node getPredeccessor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateSuccessor(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updatePredecessor(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void redirectMessage(String message, Node next) {
        socketConnector.sendMessage(message, next.getIp(), next.getPort());
    }
}
