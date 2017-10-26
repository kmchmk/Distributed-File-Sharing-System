/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chord;

/**
 *
 * @author erang
 */
public interface Node {

    public void joinNetwork();

    public boolean leaveNetwork();

    public void initialize();

    public void routeMessge(String message, int key);

    public String search();

    public Node getSuccessor();

    public Node getPredeccessor();

    public void setSuccessor(Node successor);

    public void setPredecessor(Node predecessor);

    public String getIp();

    public int getPort();

    public String getBSip();

    public int getBSport();

    public void handleMessage(String message, String incomingIP, int incomingPort);

    public FingerTable getFingerTable();

    public int getID();

    public Node findSuccessorOf(int id);
    
}
