/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uom.chord;

import com.uom.communication.Connector;
import com.uom.communication.SocketConnector;
import com.uom.view.GUI;
import static java.lang.System.exit;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author erang
 */
public final class Node {

    private GUI gui;
    public static final int MAX_FINGERS = 4;

    private String BSip;
    private int BSport;

    private final String ip;
    private final int port;
    private final String username;
    private final int id;

    private Connector connector;

    private ArrayList<String> files;
    private Node[] successors;
    private Node[] predeccessors;

    private boolean successorExists;
    private boolean predecessorExists;
    private Node successor;
    private Node predecessor;
    private FingerFixer fingerFixer;

    public Node(String username, String ip, int port, String BSip, int BSport, GUI gui, boolean MainOrDummy) {

        this.username = username;
        this.ip = ip;
        this.port = port;

        this.id = getHash(this.ip + this.port);

        if (MainOrDummy) {
            this.BSip = BSip;
            this.BSport = BSport;
            files = new ArrayList<>();

            this.connector = new SocketConnector(this);
//            this.connector = new RestConnector(this);

            this.successors = new Node[4];
            this.predeccessors = new Node[4];

            this.gui = gui;
            this.fingerFixer = new FingerFixer(this);
            initialize();
        }
    }

    public Node(String username, String ip, String port) {
        this(username, ip, Integer.parseInt(port), null, 55555, null, false);
    }

    public void initialize() {
        System.out.println("I am " + this.id);
        populateWithFiles();
        this.connector.listen(port);
    }

    public ArrayList<String> getFiles() {
        return files;
    }

    public String getIp() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }

    public String getBSip() {
        return BSip;
    }

    public int getBSport() {
        return BSport;
    }

    public String getUserName() {
        return this.username;
    }

    public int getID() {
        return this.id;
    }

    public Node getSuccessor() {
        return this.successor;
    }

    public Node getPredecessor() {
        return this.predecessor;
    }

    public void setSuccessor(Node successor) {
        this.successor = successor;
        this.successors[0] = successor;
        gui.UpdateSuccessor(0, successor);
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
        this.predeccessors[0] = predecessor;
        gui.UpdatePredecessor(0, predecessor);
    }

    public GUI getGUI() {
        return this.gui;
    }

    public Connector getConnector() {
        return connector;
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public static String getMyIP() {
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
        ArrayList<String> filelist = new ArrayList<>(Arrays.asList("Adventures of Tintin", "Jack and Jill", "Glee", "The Vampire Diarie", "King Arthur", "Windows XP", "Harry Potter", "Kung Fu Panda", "Lady Gaga", "Twilight", "Windows 8", "Mission Impossible", "Turn Up The Music", "Super Mario", "American Pickers", "Microsoft Office 2010", "Happy Feet", "Modern Family", "American Idol", "Hacking for Dummies"));
        String text = "<html>";
        for (int i = 0; i < 3; i++) {
            int rand = new Random().nextInt(filelist.size());
            String file = filelist.get(rand);
            files.add(file.toLowerCase());
            text += "My file " + getHash(file) + " is: " + file + "<br>";
            filelist.remove(rand);
        }
        text += "</html>";
        if (gui != null) {
            gui.updateFilesDisplay(text);
        }
    }

    public void registerToNetwork() {
        String registerMessage = " " + "REG" + " " + ip + " " + Integer.toString(port) + " " + username;
        int length = registerMessage.length() + 4;
        registerMessage = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0"))
                + Integer.toString(length) + registerMessage;

        connector.sendToBS(registerMessage);
    }

    public void unregisterFromNetwork() {
        String unregisterMessage = " " + "UNREG" + " " + ip + " " + Integer.toString(port) + " " + username;
        int length = unregisterMessage.length() + 4;
        unregisterMessage = String.join("", Collections.nCopies(4 - (Integer.toString(length).length()), "0")) + Integer.toString(length) + unregisterMessage;
        connector.sendToBS(unregisterMessage);
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
    public void handleMessage(String message) {
        String[] messageList = message.split(" ");
        if (null != messageList[0] && messageList.length > 1) {

            switch (messageList[0]) {
                case "JOIN"://find successor
                    int receiverID = Integer.parseInt(messageList[3]);
                    Node receiver = new Node(messageList[4], messageList[1], messageList[2]);
                    if (this.getID() < receiverID) {
                        if (this.getSuccessor() == null) {
                            this.setSuccessor(receiver);
                            askToUpdatePredeccessor(receiver, this);
                        } else if (this.getSuccessor().getID() < receiverID) {
                            connector.send(message, this.getSuccessor().getIp(), this.getSuccessor().getPort());
                        } else {
                            //mysuccessor is his succerssor
                            askToUpdateSuccessor(receiver, this.getSuccessor());
                            askToUpdatePredeccessor(receiver, this);

                            askToUpdatePredeccessor(this.getSuccessor(), receiver);
                            this.setSuccessor(receiver);
                        }
                    } else if (this.getPredecessor() == null) {
                        this.setPredecessor(receiver);
                        askToUpdateSuccessor(receiver, this);
                    } else if (this.getPredecessor().getID() > receiverID) {
                        connector.send(message, this.getPredecessor().getIp(), this.getPredecessor().getPort());
                    } else {
                        //he is my predecessor
                        askToUpdateSuccessor(receiver, this);
                        askToUpdatePredeccessor(receiver, this.getPredecessor());

                        askToUpdateSuccessor(this.getPredecessor(), receiver);
                        this.setPredecessor(receiver);
                    }
                    break;
                case "UPDATE_SUCC":
                    Node successor1 = new Node(messageList[4], messageList[1], messageList[2]);
                    this.setSuccessor(successor1);
                    break;

                case "UPDATE_PRED":
                    Node predecessor1 = new Node(messageList[4], messageList[1], messageList[2]);
                    this.setPredecessor(predecessor1);
                    break;
                case "SEARCH_UP":
                    String fileUp = message.split("@")[1];
                    for (String eachResult : this.contains(fileUp)) {
                        String found = "FOUND_FILE " + this.getIp() + " " + this.getPort() + " " + this.getID() + " " + this.getUserName() + " @" + eachResult;
                        connector.send(found, messageList[1], Integer.parseInt(messageList[2]));
                    }
                    if (this.getSuccessor() != null) {
                        connector.send(message, this.getSuccessor().getIp(), this.getSuccessor().getPort());
                    }
                    break;
                case "SEARCH_DOWN":
                    String fileDown = message.split("@")[1];
                    for (String eachResult : this.contains(fileDown)) {
                        String found = "FOUND_FILE " + this.getIp() + " " + this.getPort() + " " + this.getID() + " " + this.getUserName() + " @" + eachResult;
                        connector.send(found, messageList[1], Integer.parseInt(messageList[2]));
                    }
                    if (this.getPredecessor() != null) {
                        connector.send(message, this.getPredecessor().getIp(), this.getPredecessor().getPort());
                    }
                    break;
                case "FOUND_FILE":
                    gui.updateResultsDisplay(message.split("@")[1], new Node(messageList[4], messageList[1], messageList[2]));
                    break;

                case "HEARTBEAT_UP":
                    int indexUp = Integer.parseInt(messageList[5]);
                    if (indexUp > 0) {
                        String present = "PRESENT_UP " + this.getIp() + " " + this.getPort() + " " + this.getID() + " " + this.getUserName() + " " + indexUp;
                        connector.send(present, messageList[1], Integer.parseInt(messageList[2]));
                    }
                    if (this.getSuccessor() != null && indexUp < 3) {
                        String nextHeartBeat = messageList[0] + " " + messageList[1] + " " + messageList[2] + " " + messageList[3] + " " + messageList[4] + " " + (indexUp + 1);
                        connector.send(nextHeartBeat, this.getSuccessor().getIp(), this.getSuccessor().getPort());
                    }
                    break;

                case "HEARTBEAT_DOWN":
                    int indexDown = Integer.parseInt(messageList[5]);
                    if (indexDown > 0) {
                        String present = "PRESENT_DOWN " + this.getIp() + " " + this.getPort() + " " + this.getID() + " " + this.getUserName() + " " + indexDown;
                        connector.send(present, messageList[1], Integer.parseInt(messageList[2]));
                    }
                    if (this.getPredecessor() != null && indexDown < 3) {
                        String nextHeartBeat = messageList[0] + " " + messageList[1] + " " + messageList[2] + " " + messageList[3] + " " + messageList[4] + " " + (indexDown + 1);
                        connector.send(nextHeartBeat, this.getPredecessor().getIp(), this.getPredecessor().getPort());
                    }
                    break;

                case "PRESENT_UP":
                    Node existingUp = new Node(messageList[4], messageList[1], messageList[2]);
                    int existingIndexUp = Integer.parseInt(messageList[5]);
                    updateExistingSuccessor(existingIndexUp, existingUp);
                    break;

                case "PRESENT_DOWN":
                    Node existingDown = new Node(messageList[4], messageList[1], messageList[2]);
                    int existingIndexDown = Integer.parseInt(messageList[5]);
                    updateExistingPredecessor(existingIndexDown, existingDown);
                    break;
                default:
                    break;
            }

            if ("REGOK".equals(messageList[1])) {
                switch (messageList[2]) {
                    case "0":
                        gui.echo("This is the first node.");
                        fingerFixer.start();
                        break;

                    case "1":
                        gui.echo("This is the second node.");
                        SimpleNeighbor firstNeighbor = new SimpleNeighbor(messageList[3], Integer.parseInt(messageList[4]));
                        joinNetwork(firstNeighbor);
                        fingerFixer.start();
                        break;

                    case "2":
                        gui.echo("This is the third or a later node.");
                        SimpleNeighbor firstNeighbor1 = new SimpleNeighbor(messageList[3], Integer.parseInt(messageList[4]));
                        SimpleNeighbor secondNeighbor = new SimpleNeighbor(messageList[5], Integer.parseInt(messageList[6]));

                        //get the closest neigbour
                        if (Math.abs(this.id - firstNeighbor1.getId()) < Math.abs(this.id - secondNeighbor.getId())) {
                            joinNetwork(firstNeighbor1);
                        } else {
                            joinNetwork(secondNeighbor);
                        }
                        fingerFixer.start();
                        break;

                    case "9999":
                        JOptionPane.showMessageDialog(gui, " Failed, there is some error in the command.");
                        exit(0);
                        break;

                    case "9998":
                        JOptionPane.showMessageDialog(gui, "Failed, already registered to you, unregister first.");
                        exit(0);
                        break;

                    case "9997":
                        JOptionPane.showMessageDialog(gui, "Failed, registered to another user, try a different IP and port.");
                        exit(0);
                        break;

                    case "9996":
                        JOptionPane.showMessageDialog(gui, "Failed, can’t register. Bootstrap Server full.");
                        exit(0);
                        break;

                    default:
                        break;
                }
            } else if ("UNROK".equals(messageList[1])) {
                switch (messageList[2]) {
                    case "0":
                        gui.echo("Successfully unregistered.\n");
                        this.connector.kill(); //stop listning, equivelent to leave the network
                        this.fingerFixer.kill();
                        JOptionPane.showMessageDialog(gui, "Close interface?");
                        exit(0);
                        break;

                    case "9999":
                        gui.echo("Error while unregistering. IP and port may not be in the registry or command is incorrect.");
                        break;

                    default:
                        gui.echo("Some error while unregistering.");
                        break;
                }
            }
        }
    }

    public static int getHash(String text) {
        return Math.abs(text.hashCode()) % 1000;
    }

    public void echo(String output) {
        if (gui != null) {
            gui.echo("The message received is: " + output);
        }
    }

    private void joinNetwork(SimpleNeighbor neighbour) {
        String message = "JOIN " + this.getIp() + " " + this.getPort() + " " + this.getID() + " " + this.getUserName();
        connector.send(message, neighbour.getIp(), neighbour.getPort());
    }

    public void askToUpdateSuccessor(Node receiver, Node successor_) {
        String message = "UPDATE_SUCC " + successor_.getIp() + " " + successor_.getPort() + " " + successor_.getID() + " " + successor_.getUserName();
        connector.send(message, receiver.getIp(), receiver.getPort());
    }

    public void askToUpdatePredeccessor(Node receiver, Node predeccessor_) {
        String message = "UPDATE_PRED " + predeccessor_.getIp() + " " + predeccessor_.getPort() + " " + predeccessor_.getID() + " " + predeccessor_.getUserName();
        connector.send(message, receiver.getIp(), receiver.getPort());
    }

    public void search(String file) {
        for (String eachResult : this.contains(file)) {
            gui.updateResultsDisplay(eachResult, this);
        }

        String searchUp = "SEARCH_UP " + this.ip + " " + this.port + " @" + file;
        if (this.getSuccessor() != null) {
            connector.send(searchUp, this.getSuccessor().getIp(), this.getSuccessor().getPort());
        }
        String searchDown = "SEARCH_DOWN " + this.ip + " " + this.port + " @" + file;
        if (this.getPredecessor() != null) {
            connector.send(searchDown, this.getPredecessor().getIp(), this.getPredecessor().getPort());
        }
    }

    private ArrayList<String> contains(String file) {
        ArrayList<String> results = new ArrayList<>();
        for (String eachFile : this.getFiles()) {

            if (file.equals(eachFile)) {
                results.add(eachFile);
            }
            String[] words = eachFile.split(" ");
            if (words.length > 1) {
                for (int i = 0; i < words.length; i++) {
                    if (file.equals(words[i])) {
                        results.add(eachFile);
                    }
                }
            }

        }
        return results;

    }

    public Node[] getSuccessors() {
        return successors;
    }

    public Node[] getPredeccessors() {
        return predeccessors;
    }

    public void updateExistingSuccessor(int index, Node existingSuccessor) {
        gui.UpdateSuccessor(index, existingSuccessor);
        this.getSuccessors()[index] = existingSuccessor;
        this.fingerFixer.temporarySuccessors[index] = existingSuccessor;
    }

    public void updateExistingPredecessor(int index, Node existingPredecessor) {
        gui.UpdatePredecessor(index, existingPredecessor);
        this.getPredeccessors()[index] = existingPredecessor;
        this.fingerFixer.temporaryPredeccessors[index] = existingPredecessor;
    }

}
