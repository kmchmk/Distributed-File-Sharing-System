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
import java.util.Map;
import java.util.Random;

/**
 *
 * @author erang
 */
public class NodeImpl implements Node {

    private static final int MAX_FINGERS = 10;

    private final String BSip;
    private final int BSport;

    private final String ip;
    private final int port;
    private final String username;
    private final int id;

    private SocketConnector socketConnector;

    private FingerTable fingerTable;
    private Node successor;
    private Node predecessor;

    private ArrayList<SimpleNeighbor> neighborList;

    private Map<Integer, String> metaData;
    private ArrayList<String> files;

    public NodeImpl(String username, String ip, int port, String BSip, int BSport) {
        fingerTable = new FingertableImpl(MAX_FINGERS);
        metaData = new HashMap<>();
        files = new ArrayList<>();
        neighborList = new ArrayList<>();

        this.username = username;
        this.ip = ip;
        this.port = port;
        this.BSip = BSip;
        this.BSport = BSport;
        this.id = this.username.hashCode();

        this.socketConnector = new SocketConnector(this);
    }

    public NodeImpl(String username, int port, String BSip, int BSport) {
        this(username, getMyIP(), port, BSip, BSport);
    }

    public NodeImpl(String username, int port) {
        this(username, getMyIP(), port, getMyIP(), 55555);
    }

    @Override
    public void initialize() {
        populateWithFiles();
        this.socketConnector.listen(port);
    }

    @Override
    public String getIp() {
        return this.ip;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getBSip() {
        return BSip;
    }

    @Override
    public int getBSport() {
        return BSport;
    }

    @Override
    public boolean joinNetwork() {
        this.successor = findSuccessor();
        askToUpdatePredecessor(this.successor);

        this.predecessor = findPredecessor();
        askToUpdateSuccessor(this.predecessor);

        return updateFingerTable();
    }

    private void askToUpdatePredecessor(Node tempSuccessor) {
        String reply = null; // dirty fix
        
        String message = "UP " + this.ip + " " + this.port;
        
        socketConnector.send(message, tempSuccessor.getIp(), tempSuccessor.getPort());
        
        if (reply.equals("updated")) {
            System.out.println("Suuccessfully updated the predecessor.");
        } else {
            System.out.println("Couldn't update the predecessor.");
        }
    }

    private void askToUpdateSuccessor(Node tempPredecessor) {
        String reply = null; // dirty fix
        
        socketConnector.send("US " + this.ip + " " + this.port, tempPredecessor.getIp(), tempPredecessor.getPort());
        
        if (reply.equals("updated")) {
            System.out.println("Suuccessfully updated the successor.");
        } else {
            System.out.println("Couldn't update the successor.");
        }
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
        String reply = null; // dirty fix
        
        socketConnector.send("CP " + Integer.toString(id), neighbor.getIp(), neighbor.getPort());
        
        System.out.println(reply);
        String[] replyList = reply.split(" ");
        
        if ("PRED".equals(replyList[0])) {
            String predecessorIP = replyList[1];
            int predecessorPort = Integer.parseInt(replyList[2]);
            return new NodeImpl(null, predecessorIP, predecessorPort, this.BSip, this.BSport);
//            return null;
        } else {
            return null;
        }
    }

    private Node askClosestSuccessor(SimpleNeighbor neighbor) {
        String reply = null; // dirty fix
        
        
        socketConnector.send("CS " + Integer.toString(id), neighbor.getIp(), neighbor.getPort());
        
        String[] replyList = reply.split(" ");
        
        if ("SUCC".equals(replyList[0])) {
            String successorIP = replyList[1];
            int successorPort = Integer.parseInt(replyList[2]);
//          return new SimpleNeighbor(successorIP, successorPort);
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
        String reply = null; // dirty fix
        
        System.out.println("Registering to network: (" + username + ")");

        String registerMessage = " " + "REG" + " " + ip + " " + Integer.toString(port) + " " + username;
        int length = registerMessage.length() + 4;

        registerMessage = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0"))
                + Integer.toString(length) + registerMessage;

        socketConnector.send(registerMessage, BSip, BSport);

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
        
        String reply = null; // dirty fix
        
        String registerMessage = " " + "UNREG" + " " + ip + " " + Integer.toString(port) + " " + username;
        int length = registerMessage.length() + 4;

        registerMessage = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0")) + Integer.toString(length) + registerMessage;

        socketConnector.send(registerMessage, "localhost", BSport);
        System.out.println("Reply from bootstrap server:- " + reply);
        
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
        
        String reply = null; // dirty fix
        
        String searchQuery = " " + "SER" + " " + ip + " " + Integer.toString(port) + " " + searchString;
        int length = searchQuery.length() + 4;

        searchQuery = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0")) + Integer.toString(length) + searchQuery;
        //asking from the first user
        if (neighborList.size() > 0) {
            //here, IP should be neighborList.get(0).getIP()
            socketConnector.send(searchQuery, "localhost", neighborList.get(0).getPort());
            System.out.println("Reply from first neighbor:- " + reply);
        }
        if (neighborList.size() > 1) {
            //here, IP should be neighborList.get(0).getIP()
            socketConnector.send(searchQuery, "localhost", neighborList.get(1).getPort());
            System.out.println("Reply from second neighbor:- " + reply);
        }
    }

    public void populateWithFiles() {

        System.out.println("Populating files:\n");

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
            files.add(file);
            System.out.println(file);
            filelist.remove(rand);
        }

        distributeFileMetadata();
    }

    public String searchMetaData(String queryMessage) {
        System.out.println(queryMessage);
        String[] messageList = queryMessage.split(" ");

        if ("SER".equals(messageList[1])) {

            String queryWord = messageList[4];
            System.out.println("Searching for (" + queryWord + ")");

            if (metaData.containsKey(queryWord.hashCode())) {
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
    public void routeMessge(String message, int key) {
        if (this.id >= key) {
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
        return this.successor;
    }

    @Override
    public Node getPredeccessor() {
        return this.predecessor;
    }

    @Override
    public void setSuccessor(Node successor) {
        this.successor = successor;
    }

    @Override
    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    private void redirectMessage(String message, Node next) {
        socketConnector.send(message, next.getIp(), next.getPort());
    }

    @Override
    public FingerTable getFingerTable() {
        return fingerTable;
    }

    private void distributeFileMetadata() {
        for (String file : files) {
            int hash = file.hashCode();
            String message = "REGMD " + hash;
            System.out.println(message);
            routeMessge(message, hash);
        }
    }

    private void insertFileMetadata(int key, String file) {
        this.metaData.put(key, file);
    }

    /*  
    length REG IP_address port_no username
    length REGOK no_nodes IP_1 port_1 IP_2 port_2
    length UNREG IP_address port_no username
    length UNROK value
    length JOIN IP_address port_no
    length JOINOK value
    length LEAVE IP_address port_no
    length LEAVEOK value
    length SER IP port file_name hops
    length SEROK no_files IP port hops filename1 filename2 ... ...
    length ERROR
     */
    @Override
    public void handleMessage(String message, String incomingIP, int incomingPort) {

        String[] messageList = message.split(" ");

        if (null != messageList[0]) {
            switch (messageList[0]) {

                case "CP":

                    int tempID = Integer.parseInt(messageList[1]);
                    Node temp = this.getFingerTable().getClosestPredecessorToKey(tempID);
                    //"PRED " + temp.getIp() + " " + temp.getPort();
                    //DatagramPacket dp = new DatagramPacket(reply.getBytes(), reply.getBytes().length, InetAddress.getByName(incomingIP), incomingPort);
                    //socket.send(dp);
                    break;

                case "UP":

                    this.setPredecessor(new NodeImpl(null, messageList[1], Integer.parseInt(messageList[2]), this.getBSip(), this.getBSport()));
                    break;

                case "US":

                    this.setSuccessor(new NodeImpl(null, messageList[1], Integer.parseInt(messageList[2]), this.getBSip(), this.getBSport()));
                    break;

                default:
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Node n = new NodeImpl("NodeOne", 7);
        n.initialize();
    }
}
