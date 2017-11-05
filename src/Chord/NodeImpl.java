/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chord;

import COM.SocketConnector;
import View.GUI;
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
public final class NodeImpl implements Node {

    private GUI gui;
    public static final int MAX_FINGERS = 4;
    public static final int MAX_NODES = (int) Math.pow(2, MAX_FINGERS);

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

    private Stabilizer stabilizer;
    private FingerFixer fingerFixer;
    private PredecessorCheckor predecessorCheckor;

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
        this.id = Math.abs((this.ip + this.port).hashCode()) % MAX_NODES;

        this.socketConnector = new SocketConnector(this);

        this.stabilizer = new Stabilizer(this);
        this.fingerFixer = new FingerFixer(this);
        this.predecessorCheckor = new PredecessorCheckor(this);

        initialize();
    }

    public NodeImpl(String username, String ip, int port, boolean proxy) {
        fingerTable = new FingertableImpl(MAX_FINGERS);
        metaData = new HashMap<>();
        files = new ArrayList<>();
        neighborList = new ArrayList<>();

        this.username = username;
        this.ip = ip;
        this.port = port;
        this.BSip = null;
        this.BSport = 0;
        this.id = Math.abs((this.ip + this.port).hashCode()) % MAX_NODES;
    }

//    public NodeImpl(String username, int port, String BSip, int BSport) {
//        this(username, getMyIP(), port, BSip, BSport);
//    }
    public NodeImpl(String username, int port) {
        this(username, getMyIP(), port, "192.168.43.96", 55555);
    }

    @Override
    public void initialize() {
//        System.out.println("Init (" + this.username + ")");
        populateWithFiles();
        this.socketConnector.listen(port);
        echo("initializing key:" + this.id);
//        System.out.println("Start listening...(" + port + ") ");
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
    public String getUserName() {
        return this.username;
    }

    @Override
    public int getID() {
        return this.id;
    }

    public int getthePort() {
        return port;
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

    @Override
    public void redirectMessage(String message, Node next) {
        socketConnector.send(message, next.getIp(), next.getPort());
        echo("redirecting message to: " + next.getPort() + "(" + message + ")");
    }

    @Override
    public FingerTable getFingerTable() {
        return fingerTable;
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    private void distributeFileMetadata() {
        for (String file : files) {
            int hash = Math.abs(file.hashCode()) % MAX_NODES;
            String message = "REGMD " + hash;
//            System.out.println(message);
            //routeMessge(message, hash);
        }
    }

    private void insertFileMetadata(int key, String file) {
        this.metaData.put(key, file);
    }

    public static String getMyIP() {
//        if (1 == 1) {
//            return "localhost";
//        }
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

//        System.out.println("Populating files:");
        ArrayList<String> filelist = new ArrayList<>(Arrays.asList("Adventures of Tintin", "Jack and Jill", "Glee", "The Vampire Diarie", "King Arthur", "Windows XP", "Harry Potter", "Kung Fu Panda", "Lady Gaga", "Twilight", "Windows 8", "Mission Impossible", "Turn Up The Music", "Super Mario", "American Pickers", "Microsoft Office 2010", "Happy Feet", "Modern Family", "American Idol", "Hacking for Dummies"));

        for (int i = 0; i < 3; i++) {
            int rand = new Random().nextInt(filelist.size());
            String file = filelist.get(rand);
            files.add(file);
//            System.out.println(file);
            filelist.remove(rand);
        }

        distributeFileMetadata();
    }

    @Override
    public void routeMessge(String message, int key) {
        Node next;
        if ((next = fingerTable.getNode(key)) == null) {
            next = fingerTable.getClosestPredecessorToKey(key);
        }
        if (next == null) {
            next = this.successor;
        }
        redirectMessage(message, next);
    }

    public void registerToNetwork() {

        String registerMessage = " " + "REG" + " " + ip + " " + Integer.toString(port) + " " + username;
        int length = registerMessage.length() + 4;

        registerMessage = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0"))
                + Integer.toString(length) + registerMessage;

//        System.out.println("register message - (" + registerMessage + ")");
        socketConnector.sendToBS(registerMessage);

    }

    public void unregisterFromNetwork() {

        echo("Unregistering from network as: (" + username + ")");

        String unregisterMessage = " " + "UNREG" + " " + ip + " " + Integer.toString(port) + " " + username;
        int length = unregisterMessage.length() + 4;

        unregisterMessage = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0")) + Integer.toString(length) + unregisterMessage;

        socketConnector.sendToBS(unregisterMessage);
    }

    @Override
    public void joinNetwork() {

        sendMessageToSuccessor();
        sendMessageToPredecessor();//not implemented yet
    }

    @Override
    public boolean leaveNetwork() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void sendMessageToSuccessor() {
        String message = "FS " + this.id + " " + this.ip + " " + this.port;
        this.socketConnector.send(message, this.neighborList.get(0).getIp(), this.neighborList.get(0).getPort());
        echo("Sending message to neighbor (" + this.neighborList.get(0).getPort() + "), Routing to self (" + message + ")");
    }

    public void sendMessageToPredecessor() {
        //not implemented yet
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

    /*
    BS Messages:
    
    length REG IP_address port_no username
    length REGOK no_nodes IP_1 port_1 IP_2 port_2
    length UNREG IP_address port_no username
    length UNROK value
    
    Node Messages:
    
    length JOIN IP_address port_no
    length JOINOK value
    length LEAVE IP_address port_no
    length LEAVEOK value
    length SER IP port file_name hops
    length SEROK no_files IP port hops filename1 filename2 ... ...
    
    length ERROR
     */
    @Override
    public void handleMessage(String message, String incomingIP) {
//        echo(message);//this is also implemented in listener

        String[] messageList = message.split(" ");

        if (null != messageList[0] && messageList.length > 1) {

            //echo("message received: (" + messageList[0] + ")");
            switch (messageList[0]) {
                case "FS"://find successor
                    echo("message received: (" + messageList[0] + ")");

                    int key = Integer.parseInt(messageList[1]);

                    if (this.getSuccessor() == null) {

                        //set new node as my successor
                        Node tempSuccessor = new NodeImpl(null, messageList[2], Integer.parseInt(messageList[3]), true);
                        this.setSuccessor(tempSuccessor);
                        System.out.println(this.getPort() + ": my successor is :- " + this.successor.getIp() + ":" + this.successor.getPort());

                        System.out.println("succ null");
                        ///send me as the successor for new node
                        String tempMsg = "US " + this.getIp() + " " + this.getPort();
                        socketConnector.send(tempMsg, messageList[2], Integer.parseInt(messageList[3]));

                    } else if (this.id >= key) {
                        //Ask to update new nodes successor to my successor
                        //"US <successorIP> <successorPort>"
                        String tempMsg = "US " + successor.getIp() + " " + successor.getPort();
                        socketConnector.send(tempMsg, messageList[2], Integer.parseInt(messageList[3]));

                        Node tempSuccessor = new NodeImpl(null, messageList[2], Integer.parseInt(messageList[3]), true);
                        this.setSuccessor(tempSuccessor);
                        System.out.println(this.getPort() + ": my successor is : " + this.successor.getPort());

                        //request the finger tabel from my successor
                        //"RFT <myIP> <myPort>"
                        tempMsg = "RFT " + this.getIp() + " " + this.getPort();
                        socketConnector.send(tempMsg, incomingIP, Integer.parseInt(messageList[3]));
                        System.out.println(this.getPort() + ": request finger table : (" + tempMsg + ")");
                    } else {
                        routeMessge(message, key);
                    }
                    break;

                case "US": //update succesor
//                    echo("message received: (" + messageList[0] + ")");

                    Node tempSuccessor = new NodeImpl(null, messageList[1], Integer.parseInt(messageList[2]), true);
                    this.setSuccessor(tempSuccessor);
                    System.out.println(this.getPort() + ": my successor is : " + this.successor.getPort());
                    new Thread() {
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                String tempRFTMsg = "RFT " + ip + " " + port;
                                socketConnector.send(tempRFTMsg, incomingIP, Integer.parseInt(messageList[2]));
                                System.out.println(port + ": request finger table : (" + tempRFTMsg + ")");
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }.start();

                    break;
                case "RFT": //request finger table
                    echo("message received: (" + messageList[0] + ")");

                    //send finger table
                    //UFT <ip_1> <port_1> <ip_2> <port_2> <ip_3> <port_3>.....
                    if (this.fingerTable.getFingerEntries()[0] == null) {
                        for (int i = 0; i < MAX_FINGERS; i++) {
                            this.fingerTable.updateEntry(i, this);
                        }
                    }

                    String tempMsg = "UFT "; //update finger table
                    for (int i = 0; i < MAX_FINGERS; i++) {
                        Node entry = fingerTable.getEntryByIndex(i);
                        tempMsg += entry.getIp() + " " + entry.getPort() + " ";
                    }
                    socketConnector.send(tempMsg, messageList[1], Integer.parseInt(messageList[2]));
                    System.out.println(this.getPort() + ": sending finger table to : " + messageList[2] + " (" + tempMsg + ")");
                    break;
                case "UFT"://update finger table
                    echo("message received: (" + messageList[0] + ")");

                    int i = 0;
                    Node temp = new NodeImpl(null, messageList[(2 * i) + 1], Integer.parseInt(messageList[(2 * i) + 2]), true);

                    for (; i < MAX_FINGERS; i++) {

                        fingerTable.updateEntry(i, temp);
                    }
                    stabilizer.start();
                    fingerFixer.start();
                    predecessorCheckor.start();
                    break;
                case "NOTIFY_S":    // notify succoessor

                    Node tempPredecessor = new NodeImpl(null, messageList[1], Integer.parseInt(messageList[2]), this.getBSip(), this.getBSport());
                    if (predecessor == null) {
                        predecessor = tempPredecessor;
                        System.out.println("NOTIFY_S: Update predecessor of " + id + " to " + tempPredecessor.getID());
                    } else if (predecessor.getID() > this.id) {
                        if ((predecessor.getID() < tempPredecessor.getID() && tempPredecessor.getID() < MAX_NODES)
                                || (0 <= tempPredecessor.getID() && tempPredecessor.getID() < this.id)) {
                            System.out.println("NOTIFY_S: Update predecessor of " + id + " from  " + predecessor.getID() + " to " + tempPredecessor.getID());
                            predecessor = tempPredecessor;
                        }
                    } else if (predecessor.getID() < tempPredecessor.getID() && tempPredecessor.getID() < this.id) {
                        System.out.println("NOTIFY_S: Update predecessor of " + id + " from  " + predecessor.getID() + " to " + tempPredecessor.getID());
                        predecessor = tempPredecessor;
                    }
                    break;

                case "GET_PRED":    // get predecessor request from Stabilizer
                    String rep = "GET_PRED_OK " + this.predecessor.getIp() + " " + this.predecessor.getPort();
                    redirectMessage(rep, new NodeImpl("", messageList[1], Integer.parseInt(messageList[2]), BSip, BSport));
                    break;

                case "GET_PRED_OK":     // respond from get predecessor request
                    Node newPred = new NodeImpl("", messageList[1], Integer.parseInt(messageList[2]), BSip, BSport);
                    stabilizer.setNewPredessor(newPred);
                    stabilizer.interrupt();
                    break;

                case "FIND_S":   // find successor message from findSuccosser
                    Node succosser = findSuccessorOf(Integer.parseInt(messageList[1]), Integer.parseInt(messageList[2]), messageList[3], Integer.parseInt(messageList[4]));
                    if (succosser != null) {
                        String response = "FIND_S_OK " + messageList[1] + " " + ip + " " + port;
                        redirectMessage(response, new NodeImpl("", messageList[3], Integer.parseInt(messageList[4]), BSip, BSport));
                    }
                    break;

                case "FIND_S_OK":   // reply to findSuccessor
                    fingerFixer.setSuccossorReply(message.substring(10));
                    fingerFixer.setWaitingForSuccessor(true);
                    fingerFixer.interrupt();
                    break;

                case "HB":      // heartbeat
                    System.out.println("HB recieved. Sending HB_OK");
                    redirectMessage("HB_OK", new NodeImpl("", messageList[1], Integer.parseInt(messageList[2]), BSip, BSport));
                    break;

                case "HB_OK":
                    System.out.println("HB_OK received");
                    predecessorCheckor.setPredecessorHBOK(true);
                    break;
                default:
                    break;
            }

            if ("REGOK".equals(messageList[1])) {
                //echo("message received: (" + messageList[1] + ")");

                switch (messageList[2]) {
                    case "0":
                        System.out.println("This is the first node.\n");
                        for (int i = 0; i < MAX_FINGERS; i++) {
                            this.fingerTable.updateEntry(i, this);
                        }
                        stabilizer.start();
                        fingerFixer.start();
//                        predecessorCheckor.start();
                        break;

                    case "1": {
                        System.out.println("This is the second node.\n");
                        SimpleNeighbor firstNeighbor = new SimpleNeighbor(messageList[3], Integer.parseInt(messageList[4]));
                        neighborList.add(firstNeighbor);
                        joinNetwork();
                        break;
                    }

                    case "2": {
                        System.out.println("This is the third or later node.\n");
                        SimpleNeighbor firstNeighbor = new SimpleNeighbor(messageList[3], Integer.parseInt(messageList[4]));
                        neighborList.add(firstNeighbor);
                        SimpleNeighbor secondNeighbor = new SimpleNeighbor(messageList[5], Integer.parseInt(messageList[6]));
                        neighborList.add(secondNeighbor);
                        joinNetwork();
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
            } else if ("UNROK".equals(messageList[1])) {
                //("message received: (" + messageList[1] + ")");
                switch (messageList[2]) {
                    case "0":
                        System.out.println("Successfully unregistered.\n");
                        this.socketConnector.stop(); //stop listning, equivelent to leave the network
                        stabilizer.stop();
                        fingerFixer.stop();
                        //predecessorCheckor.stop();
                        break;

                    case "9999":
                        System.out.println("Error while unregistering. IP and port may not be in the registry or command is incorrect.");
                        break;
                    default:
                        System.out.println("Some error while unregistering.");
                        break;
                }
            } else if ("SER".equals(messageList[1])) {
                String tempIP = messageList[2];
                int TempPort = Integer.parseInt(messageList[3]);
                String searchString = messageList[4];
                int hashedID = Math.abs(searchString.hashCode());
                if (hashedID > this.id) {
                    System.out.println("Handle the request here / route");
                } else {
                    System.out.println("Handle the request here / route");
                }
            }
        }
    }

    /* 
       ask node n to find the successor of key
     */
    @Override
    public Node findSuccessorOf(int finger, int key, String originIP, int originPort) {
        if (this.successor == null) {
            return null;
        } else {
            int successorID = this.successor.getID();
            // this node and successor are in opposite side of 0
            if (successorID < this.id) {
                if ((this.id < key && key < MAX_NODES) || (0 <= key && key <= successorID)) {
                    return this.successor;
                }
            } else if (this.id < key && key <= successorID) // normal successor
            {
                return this.successor;
            } else {
                Node nextNode = this.fingerTable.getClosestPredecessorToKey(key);
                String message = "FIND_S " + finger + " " + key + " " + originIP + " " + originPort;
                redirectMessage(message, nextNode);
                return null;
            }
            return null;
        }

    }

    @Override
    public void echo(String output) {
        System.out.println("The message received is: " + output + "\n");
    }

}
