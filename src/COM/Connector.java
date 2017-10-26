/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM;

/**
 *
 * @author erang
 */
public interface Connector {

    public void send(String OutgoingMessage, String ip, int port);

    public void listen(int port);
}
