/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM;

import Chord.Node;

/**
 *
 * @author erang
 */
public interface Connector {
    public String sendMessage(String message, String ip, int port);
    public void initialize();
    public void listen(int port, Node me);
}
