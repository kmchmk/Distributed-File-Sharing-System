/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uom.chord;

/**
 *
 * @author erang
 */
public class SimpleNeighbor {

    private final String ip;
    private final int port;
    private final int id;

    public SimpleNeighbor(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.id = Node.getHash(ip + port);
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }
}
