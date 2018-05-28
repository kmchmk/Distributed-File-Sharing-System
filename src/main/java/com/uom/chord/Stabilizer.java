/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uom.chord;

/**
 * @author Chanaka
 */
public class Stabilizer
{

    private final Node thisNode;
    private final int waitingTime = 1000;
    private boolean live = true;
    Thread up = new Thread()
    {
        public void run()
        {
            thisNode.echo( "Stabilizer up started!" );
            while( live )
            {
                try
                {
                    if( thisNode.getSuccessor() != null )
                    {
                        thisNode.setSuccessorExists( false );

                        pingUp( thisNode.getSuccessor() );

                        Thread.sleep( waitingTime );

                        if( !thisNode.isSuccessorExists() )
                        {
                            //                    unregister that node from BS server.
                            thisNode.unregisterFromNetwork( thisNode.getSuccessor() );
                            thisNode.setSuccessor( null );
                            if( thisNode.getGreatSuccessor() != null )
                            {
                                thisNode.askToUpdateSuccessor( thisNode, thisNode.getGreatSuccessor() );
                                thisNode.askToUpdatePredecessor( thisNode.getGreatSuccessor(), thisNode );
                            }
                        }
                    }
                    Thread.sleep( waitingTime );
                }
                catch( InterruptedException ex )
                {
                    System.err.println( "Error in stabilizer up: " + ex );
                }
            }
        }
    };
    Thread down = new Thread()
    {
        public void run()
        {
            thisNode.echo( "Stabilizer down started!" );
            while( live )
            {

                try
                {
                    if( thisNode.getPredecessor() != null )
                    {
                        thisNode.setPredecessorExists( false );

                        pingDown( thisNode.getPredecessor() );

                        Thread.sleep( waitingTime );

                        if( !thisNode.isPredecessorExists() )
                        {
                            thisNode.unregisterFromNetwork( thisNode.getPredecessor() );
                            thisNode.setPredecessor( null );
                            if( thisNode.getGreatPredecessor() != null )
                            {
                                thisNode.askToUpdateSuccessor( thisNode.getGreatPredecessor(), thisNode );
                                thisNode.askToUpdatePredecessor( thisNode, thisNode.getGreatPredecessor() );
                            }
                        }
                    }
                    Thread.sleep( waitingTime );
                }
                catch( InterruptedException ex )
                {
                    System.err.println( "Error in stabilizer down: " + ex );
                }
            }
        }
    };

    public Stabilizer( Node thisNode )
    {
        this.thisNode = thisNode;
    }

    public void start()
    {
        up.start();
        down.start();
    }

    public void kill()
    {
        this.live = false;
    }

    private void pingUp( Node receiver )
    {
        if( receiver != null )
        {
            String ping = "PING_UP " + thisNode.getIp() + " " + thisNode.getPort() + " " + thisNode.getID() + " " + thisNode.getUserName();
            thisNode.getConnector().send( ping, receiver.getIp(), receiver.getPort() );
        }
    }

    private void pingDown( Node receiver )
    {
        if( receiver != null )
        {
            String ping = "PING_DOWN " + thisNode.getIp() + " " + thisNode.getPort() + " " + thisNode.getID() + " " + thisNode.getUserName();
            thisNode.getConnector().send( ping, receiver.getIp(), receiver.getPort() );
        }
    }

}
