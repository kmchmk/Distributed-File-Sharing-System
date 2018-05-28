package com.uom.communication;

import com.uom.chord.Node;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RestConnector implements Connector
{

    private final Node myNode;
    private boolean started = false;
    private Server jettyServer;

    public RestConnector( Node myNode )
    {
        this.myNode = myNode;
    }

    public void kill()
    {
        if( started )
        {
            started = false;
            try
            {
                jettyServer.stop();
            }
            catch( Exception e )
            {
                System.err.println( "Error occurred when stopping the REST server due to : {}" + e.getMessage() );
            }
        }
    }

    @Override
    public void listen( int port )
    {
        try
        {
            if( started )
            {
                System.out.println( "Listener already running" );
            }
            else
            {
                ResourceConfig config = new ResourceConfig();
                config.register( new RestController( myNode ) );

                ServletHolder servlet = new ServletHolder( new ServletContainer( config ) );

                jettyServer = new Server( myNode.getPort() );

                ServletContextHandler context = new ServletContextHandler( jettyServer, null );
                context.addServlet( servlet, "/*" );
                context.setSessionHandler( new SessionHandler() );
                jettyServer.setHandler( context );

                try
                {
                    jettyServer.start();
                }
                catch( Exception e )
                {
                    System.err.println( "Error occurred when starting REST server due to" + e );
                    return;
                }

                System.out.println( "REST Server started successfully ..." );
            }
        }
        catch( Exception e )
        {
            System.err.println( e );
        }
    }

    @Override
    public void send( String OutgoingMessage, String OutgoingIP, String OutgoingPort )
    {
        send( OutgoingMessage, OutgoingIP, Integer.parseInt( OutgoingPort ) );
    }

    @Override
    public void send( String OutgoingMessage, Node destination )
    {
        if( destination != null )
        {
            send( OutgoingMessage, destination.getIp(), destination.getPort() );
        }
    }

    @Override
    public void send( String OutgoingMessage, String OutgoingIP, int OutgoingPort )
    {
        try
        {
            UriBuilder url = UriBuilder.fromPath( "rest" )
                                       .path( OutgoingMessage )
                                       .scheme( "http" )
                                       .host( OutgoingIP )
                                       .port( OutgoingPort );
            myNode.echo( "Sending REST message: " + OutgoingMessage );
            myNode.getGUI().updateReceiveCount();
            //        System.out.println(url.toString());

            Client client = JerseyClientBuilder.createClient();

            String response = client.target( url )
                                    .request( MediaType.APPLICATION_JSON )
                                    .get( String.class );

            if( response.equals( Response.Status.OK.toString() ) )
            {
                myNode.echo( "Message Successfully Sent." );
            }
            else
            {
                myNode.echo( "Message did not delivered." );
            }
        }
        catch( Exception e )
        {
            System.err.println( "Send error" );
        }
    }

    @Override
    public void sendToBS( String message )
    {
        new Thread()
        {
            public void run()
            {

                try
                {
                    DatagramSocket socket = new DatagramSocket();

                    byte[] b = message.getBytes();

                    DatagramPacket dp = new DatagramPacket( b, b.length, InetAddress.getByName( myNode.getBSip() ), myNode.getBSport() );
                    socket.send( dp );
                    myNode.getGUI().updateSendCount();
                    //now receive reply
                    //buffer to receive incoming data
                    byte[] buffer = new byte[65536];
                    socket.setSoTimeout( 10000 );
                    DatagramPacket repl = new DatagramPacket( buffer, buffer.length );
                    socket.receive( repl );

                    byte[] data = repl.getData();
                    String reply = new String( data, 0, repl.getLength() );
                    myNode.getGUI().updateReceiveCount();
                    myNode.handleMessage( reply );

                }
                catch( Exception e )
                {
                    System.err.println( "IOException " + e );
                }
            }
        }.start();
    }

}
