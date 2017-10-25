/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chord;

/**
 *
 * @author Chanaka
 */


//This Neighbor is same as the Neighbour in bootstrap server.
//But I have used two classes for decoupling purposes
public class Neighbor {
    public String IP;
    public int port;

    public Neighbor(String IP, int port) {
        this.IP = IP;
        this.port = port;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return port;
    }
    
}
