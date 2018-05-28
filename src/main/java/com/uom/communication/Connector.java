/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uom.communication;

import com.uom.chord.Node;

/**
 * @author charith munasinghe
 */
public interface Connector
{

    public void send( String OutgoingMessage, String OutgoingIP, int OutgoingPort );

    public void send( String OutgoingMessage, String OutgoingIP, String OutgoingPort );

    public void send( String OutgoingMessage, Node destination );

    public void sendToBS( String message );

    public void listen( int port );

    public void kill();
}
