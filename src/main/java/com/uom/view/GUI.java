/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uom.view;

import com.uom.bootstrapServer.BootstrapServer;
import com.uom.chord.Node;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.BindException;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author Achini
 */
public class GUI extends javax.swing.JFrame
{

    /**
     * Creates new form GUI
     */
    Node node;
    long startTime;
    boolean socketOrRest;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ID;
    private javax.swing.JLabel IP;
    private javax.swing.JLabel PortNo;
    private javax.swing.JTextField Textuser;
    private javax.swing.JTextField Textuser1;
    private javax.swing.JTextField Textuser2;
    private javax.swing.JTextField enterPort;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel name;
    private javax.swing.JTable predecessorTable;
    private javax.swing.JTable resultsTable;
    private javax.swing.JTextField search;
    private javax.swing.JTable successorTable;
    public GUI()
    {
        //node.registerToNetwork();
        initComponents();
        startTime = System.currentTimeMillis();
    }

    /**
     * @param args the command line arguments
     */
    public static void main( String args[] )
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */

        try
        {
            for( javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels() )
            {
                if( "Nimbus".equals( info.getName() ) )
                {
                    javax.swing.UIManager.setLookAndFeel( info.getClassName() );
                    break;

                }
            }
        }
        catch( ClassNotFoundException ex )
        {
            java.util.logging.Logger.getLogger( GUI.class
                                                        .getName() ).log( java.util.logging.Level.SEVERE, null, ex );

        }
        catch( InstantiationException ex )
        {
            java.util.logging.Logger.getLogger( GUI.class
                                                        .getName() ).log( java.util.logging.Level.SEVERE, null, ex );

        }
        catch( IllegalAccessException ex )
        {
            java.util.logging.Logger.getLogger( GUI.class
                                                        .getName() ).log( java.util.logging.Level.SEVERE, null, ex );

        }
        catch( javax.swing.UnsupportedLookAndFeelException ex )
        {
            java.util.logging.Logger.getLogger( GUI.class
                                                        .getName() ).log( java.util.logging.Level.SEVERE, null, ex );
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater( new Runnable()
        {
            @Override
            public void run()
            {
                new GUI().setVisible( true );
            }
        } );

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        PortNo = new javax.swing.JLabel();
        IP = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        ID = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        search = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        resultsTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        enterPort = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Textuser = new javax.swing.JTextField();
        Textuser1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        Textuser2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        predecessorTable = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        successorTable = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();

        setDefaultCloseOperation( javax.swing.WindowConstants.EXIT_ON_CLOSE );
        setBackground( new java.awt.Color( 204, 0, 0 ) );

        jLabel1.setBackground( new java.awt.Color( 153, 255, 204 ) );
        jLabel1.setFont( new java.awt.Font( "Times New Roman", 1, 18 ) ); // NOI18N
        jLabel1.setHorizontalAlignment( javax.swing.SwingConstants.CENTER );
        jLabel1.setText( "MOVIE HUB" );
        jLabel1.setBorder( javax.swing.BorderFactory.createBevelBorder( javax.swing.border.BevelBorder.RAISED ) );

        jPanel1.setBorder( javax.swing.BorderFactory.createEtchedBorder() );

        jLabel6.setFont( new java.awt.Font( "Tahoma", 0, 12 ) ); // NOI18N
        jLabel6.setHorizontalAlignment( javax.swing.SwingConstants.CENTER );
        jLabel6.setText( "IP Address:" );

        jLabel7.setFont( new java.awt.Font( "Tahoma", 0, 12 ) ); // NOI18N
        jLabel7.setText( "Port:" );

        PortNo.setFont( new java.awt.Font( "Tahoma", 1, 12 ) ); // NOI18N
        PortNo.setText( "Not yet" );

        IP.setFont( new java.awt.Font( "Tahoma", 1, 12 ) ); // NOI18N
        IP.setText( "Not yet" );

        jLabel8.setText( "Username:" );

        jLabel9.setText( "ID:" );

        name.setFont( new java.awt.Font( "Tahoma", 1, 12 ) ); // NOI18N
        name.setText( "Not yet" );

        ID.setFont( new java.awt.Font( "Tahoma", 1, 12 ) ); // NOI18N
        ID.setText( "Not yet" );

        jLabel15.setFont( new java.awt.Font( "Tahoma", 1, 11 ) ); // NOI18N
        jLabel15.setText( "Not yet" );

        jLabel16.setText( "Files:" );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout( jPanel1 );
        jPanel1.setLayout( jPanel1Layout );
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                             .addGroup( jPanel1Layout.createSequentialGroup()
                                                     .addContainerGap()
                                                     .addGroup( jPanel1Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                             .addGroup( jPanel1Layout.createSequentialGroup()
                                                                                                     .addComponent( jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 21,
                                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                     .addComponent( ID )
                                                                                                     .addGap( 36, 36, 36 )
                                                                                                     .addComponent( jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 62,
                                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                     .addComponent( name )
                                                                                                     .addGap( 35, 35, 35 )
                                                                                                     .addComponent( jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70,
                                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                     .addComponent( IP, javax.swing.GroupLayout.PREFERRED_SIZE, 120,
                                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                                                     .addGap( 34, 34, 34 )
                                                                                                     .addComponent( jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
                                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                     .addComponent( PortNo, javax.swing.GroupLayout.PREFERRED_SIZE, 84,
                                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE ) )
                                                                             .addGroup( jPanel1Layout.createSequentialGroup()
                                                                                                     .addComponent( jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                     .addComponent( jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 328,
                                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE ) ) )
                                                     .addContainerGap( 32, Short.MAX_VALUE ) )
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                             .addGroup( jPanel1Layout.createSequentialGroup()
                                                     .addContainerGap()
                                                     .addGroup( jPanel1Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                             .addComponent( jLabel6 )
                                                                             .addGroup( jPanel1Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                                                     .addComponent( jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                    Short.MAX_VALUE )
                                                                                                     .addComponent( PortNo )
                                                                                                     .addComponent( IP )
                                                                                                     .addComponent( jLabel8 )
                                                                                                     .addComponent( jLabel9 )
                                                                                                     .addComponent( ID )
                                                                                                     .addComponent( name ) ) )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.UNRELATED )
                                                     .addGroup( jPanel1Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                             .addComponent( jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                             .addComponent( jLabel16 ) )
                                                     .addContainerGap( javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ) )
        );

        jPanel2.setBorder( javax.swing.BorderFactory.createBevelBorder( javax.swing.border.BevelBorder.RAISED ) );

        search.setEnabled( false );
        search.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed( java.awt.event.ActionEvent evt )
            {
                searchActionPerformed( evt );
            }
        } );

        jButton2.setText( "Search" );
        jButton2.setEnabled( false );
        jButton2.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed( java.awt.event.ActionEvent evt )
            {
                jButton2ActionPerformed( evt );
            }
        } );

        resultsTable.setModel( new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "File", "ID", "IP", "Port", "Username", "time", "Hops"
                }
        )
        {
            Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, true, false, false, true
            };

            public Class getColumnClass( int columnIndex )
            {
                return types[columnIndex];
            }

            public boolean isCellEditable( int rowIndex, int columnIndex )
            {
                return canEdit[columnIndex];
            }
        } );
        jScrollPane4.setViewportView( resultsTable );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout( jPanel2 );
        jPanel2.setLayout( jPanel2Layout );
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                             .addGroup( jPanel2Layout.createSequentialGroup()
                                                     .addContainerGap()
                                                     .addGroup( jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                             .addGroup( jPanel2Layout.createSequentialGroup()
                                                                                                     .addComponent( search )
                                                                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.UNRELATED )
                                                                                                     .addComponent( jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 72,
                                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE ) )
                                                                             .addComponent( jScrollPane4 ) )
                                                     .addContainerGap() )
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                             .addGroup( jPanel2Layout.createSequentialGroup()
                                                     .addContainerGap()
                                                     .addGroup( jPanel2Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                             .addComponent( search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                             .addComponent( jButton2 ) )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                     .addComponent( jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE )
                                                     .addContainerGap() )
        );

        jPanel3.setBorder( javax.swing.BorderFactory.createBevelBorder( javax.swing.border.BevelBorder.RAISED ) );
        jPanel3.setPreferredSize( new java.awt.Dimension( 125, 321 ) );

        jButton1.setText( "Start" );
        jButton1.addMouseListener( new java.awt.event.MouseAdapter()
        {
            public void mouseClicked( java.awt.event.MouseEvent evt )
            {
                jButton1MouseClicked( evt );
            }
        } );
        jButton1.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed( java.awt.event.ActionEvent evt )
            {
                jButton1ActionPerformed( evt );
            }
        } );

        jLabel2.setHorizontalAlignment( javax.swing.SwingConstants.CENTER );
        jLabel2.setText( "Bootstrap Server" );

        jButton3.setText( "Register" );
        jButton3.setEnabled( false );
        jButton3.addMouseListener( new java.awt.event.MouseAdapter()
        {
            public void mouseClicked( java.awt.event.MouseEvent evt )
            {
                jButton3MouseClicked( evt );
            }
        } );
        jButton3.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed( java.awt.event.ActionEvent evt )
            {
                jButton3ActionPerformed( evt );
            }
        } );

        jButton4.setText( "Unregister" );
        jButton4.setEnabled( false );
        jButton4.addMouseListener( new java.awt.event.MouseAdapter()
        {
            public void mouseClicked( java.awt.event.MouseEvent evt )
            {
                jButton4MouseClicked( evt );
            }
        } );
        jButton4.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed( java.awt.event.ActionEvent evt )
            {
                jButton4ActionPerformed( evt );
            }
        } );

        jLabel3.setHorizontalAlignment( javax.swing.SwingConstants.CENTER );
        jLabel3.setText( "My Node" );

        jLabel4.setHorizontalAlignment( javax.swing.SwingConstants.CENTER );
        jLabel4.setText( "Join Hub" );

        jButton6.setText( "Create Node Socket" );
        jButton6.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed( java.awt.event.ActionEvent evt )
            {
                jButton6ActionPerformed( evt );
            }
        } );

        enterPort.setText( "3001" );
        enterPort.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed( java.awt.event.ActionEvent evt )
            {
                enterPortActionPerformed( evt );
            }
        } );

        jLabel5.setText( "PORT?" );

        jLabel10.setText( "USER?" );

        Textuser.setText( "test" );
        Textuser.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed( java.awt.event.ActionEvent evt )
            {
                TextuserActionPerformed( evt );
            }
        } );

        Textuser1.setText( "localhost" );
        Textuser1.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed( java.awt.event.ActionEvent evt )
            {
                Textuser1ActionPerformed( evt );
            }
        } );

        jLabel11.setText( "BS IP?" );

        Textuser2.setText( "55555" );
        Textuser2.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed( java.awt.event.ActionEvent evt )
            {
                Textuser2ActionPerformed( evt );
            }
        } );

        jLabel14.setText( "BS Port?" );

        jButton7.setText( "Create Node Rest" );
        jButton7.addActionListener( new java.awt.event.ActionListener()
        {
            public void actionPerformed( java.awt.event.ActionEvent evt )
            {
                jButton7ActionPerformed( evt );
            }
        } );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout( jPanel3 );
        jPanel3.setLayout( jPanel3Layout );
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                             .addGroup( jPanel3Layout.createSequentialGroup()
                                                     .addGap( 10, 10, 10 )
                                                     .addGroup( jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                             .addGroup( jPanel3Layout.createSequentialGroup()
                                                                                                     .addGap( 27, 27, 27 )
                                                                                                     .addGroup( jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                                                                             .addGroup( jPanel3Layout.createParallelGroup(
                                                                                                                                     javax.swing.GroupLayout.Alignment.TRAILING, false )
                                                                                                                                                     .addComponent( jButton1,
                                                                                                                                                                    javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                    Short.MAX_VALUE )
                                                                                                                                                     .addComponent( jLabel2,
                                                                                                                                                                    javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                    92,
                                                                                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE ) )
                                                                                                                             .addComponent( jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 92,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                                                                             .addComponent( jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 92,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                                                                             .addComponent( jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92,
                                                                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE ) )
                                                                                                     .addContainerGap( javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ) )
                                                                             .addGroup( jPanel3Layout.createSequentialGroup()
                                                                                                     .addGroup( jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                                                                             .addComponent( jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                                                                                                             .addGroup( jPanel3Layout.createSequentialGroup()
                                                                                                                                                     .addGroup( jPanel3Layout.createParallelGroup(
                                                                                                                                                             javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                             false )
                                                                                                                                                                             .addComponent( jLabel5,
                                                                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                            Short.MAX_VALUE )
                                                                                                                                                                             .addComponent( jLabel10,
                                                                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                            Short.MAX_VALUE )
                                                                                                                                                                             .addComponent( jLabel11,
                                                                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                            Short.MAX_VALUE )
                                                                                                                                                                             .addComponent( jLabel14,
                                                                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                            52,
                                                                                                                                                                                            Short.MAX_VALUE ) )
                                                                                                                                                     .addPreferredGap(
                                                                                                                                                             javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                                                                     .addGroup( jPanel3Layout.createParallelGroup(
                                                                                                                                                             javax.swing.GroupLayout.Alignment.LEADING )
                                                                                                                                                                             .addComponent( Textuser1 )
                                                                                                                                                                             .addComponent( Textuser,
                                                                                                                                                                                            javax.swing.GroupLayout.Alignment.TRAILING )
                                                                                                                                                                             .addComponent( enterPort,
                                                                                                                                                                                            javax.swing.GroupLayout.Alignment.TRAILING )
                                                                                                                                                                             .addComponent(
                                                                                                                                                                                     Textuser2 ) ) ) )
                                                                                                     .addContainerGap() )
                                                                             .addGroup( jPanel3Layout.createSequentialGroup()
                                                                                                     .addGap( 10, 10, 10 )
                                                                                                     .addGroup( jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING, false )
                                                                                                                             .addComponent( jButton6, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                                                                                                             .addComponent( jButton7, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                            javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ) )
                                                                                                     .addGap( 0, 22, Short.MAX_VALUE ) ) ) )
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                             .addGroup( jPanel3Layout.createSequentialGroup()
                                                     .addContainerGap()
                                                     .addComponent( jLabel4 )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                     .addGroup( jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                             .addComponent( enterPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                             .addComponent( jLabel5 ) )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                     .addGroup( jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                             .addComponent( Textuser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                             .addComponent( jLabel10 ) )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                     .addGroup( jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                             .addComponent( Textuser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                             .addComponent( jLabel11 ) )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                     .addGroup( jPanel3Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                             .addComponent( Textuser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                             .addComponent( jLabel14 ) )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                     .addComponent( jButton6 )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                     .addComponent( jButton7 )
                                                     .addGap( 27, 27, 27 )
                                                     .addComponent( jLabel2 )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                     .addComponent( jButton1 )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE )
                                                     .addComponent( jLabel3 )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                     .addComponent( jButton3 )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                     .addComponent( jButton4 )
                                                     .addContainerGap() )
        );

        jPanel5.setBorder( javax.swing.BorderFactory.createBevelBorder( javax.swing.border.BevelBorder.RAISED ) );

        predecessorTable.setModel( new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {new Integer( 1 ), null, null, null},
                        {new Integer( 2 ), null, null, null},
                        {new Integer( 3 ), null, null, null},
                        {new Integer( 4 ), null, null, null}
                },
                new String[]{
                        "Index", "ID", "IP", "Port"
                }
        )
        {
            Class[] types = new Class[]{
                    java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, false
            };

            public Class getColumnClass( int columnIndex )
            {
                return types[columnIndex];
            }

            public boolean isCellEditable( int rowIndex, int columnIndex )
            {
                return canEdit[columnIndex];
            }
        } );
        jScrollPane3.setViewportView( predecessorTable );

        jLabel13.setFont( new java.awt.Font( "Tahoma", 1, 11 ) ); // NOI18N
        jLabel13.setHorizontalAlignment( javax.swing.SwingConstants.CENTER );
        jLabel13.setText( "Finger Tables" );

        successorTable.setModel( new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {new Integer( 1 ), null, null, null},
                        {new Integer( 2 ), null, null, null},
                        {new Integer( 3 ), null, null, null},
                        {new Integer( 4 ), null, null, null}
                },
                new String[]{
                        "Index", "ID", "IP", "Port"
                }
        )
        {
            Class[] types = new Class[]{
                    java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, false
            };

            public Class getColumnClass( int columnIndex )
            {
                return types[columnIndex];
            }

            public boolean isCellEditable( int rowIndex, int columnIndex )
            {
                return canEdit[columnIndex];
            }
        } );
        jScrollPane5.setViewportView( successorTable );

        jLabel17.setFont( new java.awt.Font( "Tahoma", 1, 11 ) ); // NOI18N
        jLabel17.setHorizontalAlignment( javax.swing.SwingConstants.CENTER );
        jLabel17.setText( "Predecessors" );

        jLabel18.setFont( new java.awt.Font( "Tahoma", 1, 11 ) ); // NOI18N
        jLabel18.setHorizontalAlignment( javax.swing.SwingConstants.CENTER );
        jLabel18.setText( "Successors" );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout( jPanel5 );
        jPanel5.setLayout( jPanel5Layout );
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                             .addGroup( jPanel5Layout.createSequentialGroup()
                                                     .addContainerGap()
                                                     .addGroup( jPanel5Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                             .addComponent( jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                                                             .addGroup( jPanel5Layout.createSequentialGroup()
                                                                                                     .addComponent( jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                                                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                     .addComponent( jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 395,
                                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE ) )
                                                                             .addGroup( jPanel5Layout.createSequentialGroup()
                                                                                                     .addComponent( jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE )
                                                                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                     .addComponent( jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE ) ) )
                                                     .addContainerGap() )
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                             .addGroup( jPanel5Layout.createSequentialGroup()
                                                     .addContainerGap()
                                                     .addComponent( jLabel13 )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                     .addGroup( jPanel5Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                             .addComponent( jLabel17 )
                                                                             .addComponent( jLabel18 ) )
                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                                     .addGroup( jPanel5Layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING, false )
                                                                             .addComponent( jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE )
                                                                             .addComponent( jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE ) )
                                                     .addContainerGap() )
        );

        jLabel12.setText( "0" );

        jLabel19.setText( "Received:" );

        jLabel20.setText( "0" );

        jLabel21.setText( "Sent:" );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout( getContentPane() );
        getContentPane().setLayout( layout );
        layout.setHorizontalGroup(
                layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                      .addGroup( layout.createSequentialGroup()
                                       .addContainerGap()
                                       .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                        .addComponent( jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                                        .addGroup( layout.createSequentialGroup()
                                                                         .addComponent( jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                         .addGap( 18, 18, 18 )
                                                                         .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                                          .addComponent( jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                         Short.MAX_VALUE )
                                                                                          .addComponent( jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                         Short.MAX_VALUE )
                                                                                          .addComponent( jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                         Short.MAX_VALUE ) ) )
                                                        .addGroup( javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                                                                     .addGap( 0, 0, Short.MAX_VALUE )
                                                                                                                     .addComponent( jLabel21 )
                                                                                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                                     .addComponent( jLabel20 )
                                                                                                                     .addGap( 73, 73, 73 )
                                                                                                                     .addComponent( jLabel19 )
                                                                                                                     .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                                     .addComponent( jLabel12 ) ) )
                                       .addContainerGap() )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                      .addGroup( layout.createSequentialGroup()
                                       .addContainerGap( javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                       .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING, false )
                                                        .addGroup( layout.createSequentialGroup()
                                                                         .addComponent( jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                         .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                         .addComponent( jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                         .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                         .addComponent( jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE ) )
                                                        .addComponent( jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE ) )
                                       .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                       .addComponent( jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE )
                                       .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                       .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                        .addComponent( jLabel12 )
                                                        .addComponent( jLabel19 )
                                                        .addComponent( jLabel20 )
                                                        .addComponent( jLabel21 ) ) )
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_searchActionPerformed
        jButton2ActionPerformed( evt );
    }//GEN-LAST:event_searchActionPerformed

    private void jButton2ActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_jButton2ActionPerformed
        //clear table
        DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();
        while( model.getRowCount() > 0 )
        {
            model.removeRow( 0 );
        }

        String fileName = search.getText();
        startTime = System.currentTimeMillis();
        node.search( fileName.toLowerCase() );
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new Thread( "BS Thread" )
        {
            @Override
            public void run()
            {
                BootstrapServer.runBootstrapServer();
            }
        }.start();
        jButton1.setEnabled( false );
        if( !jButton6.isEnabled() )
        {
            jButton3.setEnabled( true );
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3MouseClicked( java.awt.event.MouseEvent evt )
    {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton4MouseClicked( java.awt.event.MouseEvent evt )
    {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton1MouseClicked( java.awt.event.MouseEvent evt )
    {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton3ActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_jButton3ActionPerformed
        node.registerToNetwork( node );

        jButton3.setEnabled( false );
        search.setEnabled( true );
        jButton2.setEnabled( true );
        jButton4.setEnabled( true );
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_jButton4ActionPerformed
        node.unregisterFromNetwork( node );
    }//GEN-LAST:event_jButton4ActionPerformed

    public void UpdateSuccessor( int index, Node succ )
    {
        successorTable.setValueAt( succ == null ? "" : succ.getID(), index, 1 );
        successorTable.setValueAt( succ == null ? "" : succ.getIp(), index, 2 );
        successorTable.setValueAt( succ == null ? "" : succ.getPort(), index, 3 );
    }

    public void UpdatePredecessor( int index, Node pred )
    {
        predecessorTable.setValueAt( pred == null ? "" : pred.getID(), index, 1 );
        predecessorTable.setValueAt( pred == null ? "" : pred.getIp(), index, 2 );
        predecessorTable.setValueAt( pred == null ? "" : pred.getPort(), index, 3 );
    }

    public void updateResultsDisplay( String searchString, Node result, String hops )
    {
        long estimatedTime = System.currentTimeMillis() - startTime;
        DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();
        model.addRow( new Object[]{searchString, result.getID(), result.getIp(), result.getPort(), result.getUserName(), estimatedTime + "ms", hops} );
        File file;
        if( socketOrRest )
        {
            file = new File( "log-Socket.csv" );
        }
        else
        {
            file = new File( "log-Rest.csv" );
        }
        if( !file.exists() )
        {
            System.out.println( "Not Exists" );
            try
            {
                file.createNewFile();
                try (Writer writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( file ), "utf-8" ) ))
                {
                    writer.write( "Method,HopCount,Time\n" );
                    writer.close();
                }
            }
            catch( IOException ex )
            {
                JOptionPane.showMessageDialog( this, ex );
            }
        }
        try
        {
            PrintWriter out = new PrintWriter( new BufferedWriter( new FileWriter( file, true ) ) );
            out.println( ( socketOrRest ? "Socket," : "Rest," ) + hops + "," + estimatedTime + "ms" );
            out.close();
        }
        catch( IOException ex )
        {
            JOptionPane.showMessageDialog( this, ex );
        }
    }

    public void updateFilesDisplay( String text )
    {
        jLabel15.setText( text );
    }

    public void updateSendCount()
    {
        jLabel20.setText( Integer.toString( Integer.parseInt( jLabel20.getText() ) + 1 ) );
    }

    public void updateReceiveCount()
    {
        jLabel12.setText( Integer.toString( Integer.parseInt( jLabel12.getText() ) + 1 ) );
    }

    private void createNode( boolean socketOrRest, java.awt.event.ActionEvent evt )
    {
        int Port = Integer.parseInt( enterPort.getText() );
        String BSHost = Textuser1.getText();
        int BSPort = Integer.parseInt( Textuser2.getText() );

        node = new Node( Node.getMyIP(), Port, Textuser.getText(), BSHost, BSPort, this, true, socketOrRest );

        IP.setText( node.getIp() );
        PortNo.setText( String.valueOf( node.getPort() ) );
        name.setText( node.getUserName() );
        ID.setText( String.valueOf( node.getID() ) );
        jButton6.setEnabled( false );
        jButton7.setEnabled( false );
        enterPort.setEnabled( false );
        Textuser.setEnabled( false );
        Textuser1.setEnabled( false );
        Textuser2.setEnabled( false );

        if( isBSServerRunning( BSPort ) )
        {
            jButton1.setEnabled( false );
            jButton3ActionPerformed( evt );
        }
        else
        {
            jButton3.setEnabled( true );
        }
    }

    private void jButton6ActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_jButton6ActionPerformed
        socketOrRest = true;
        createNode( socketOrRest, evt );
    }//GEN-LAST:event_jButton6ActionPerformed

    boolean isBSServerRunning( int bsPort )
    {
        DatagramSocket sock = null;
        try
        {
            sock = new DatagramSocket( bsPort );
            sock.close();
            return false;
        }
        catch( BindException ignored )
        {
            return true;
        }
        catch( SocketException ex )
        {
            System.err.println( ex );
            return true;
        }
    }

    private void enterPortActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_enterPortActionPerformed
        jButton6ActionPerformed( evt );
    }//GEN-LAST:event_enterPortActionPerformed

    private void TextuserActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_TextuserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextuserActionPerformed

    private void Textuser1ActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_Textuser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Textuser1ActionPerformed

    private void Textuser2ActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_Textuser2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Textuser2ActionPerformed

    private void jButton7ActionPerformed( java.awt.event.ActionEvent evt )
    {//GEN-FIRST:event_jButton7ActionPerformed
        socketOrRest = false;
        createNode( socketOrRest, evt );
    }//GEN-LAST:event_jButton7ActionPerformed
    // End of variables declaration//GEN-END:variables

    public void clearSuccessorTable()
    {
        DefaultTableModel model = (DefaultTableModel) successorTable.getModel();
        for( int i = 0; i < 4; i++ )
        {
            for( int j = 1; j < 4; j++ )
            {
                model.setValueAt( "", i, j );

            }
        }
    }

    public void clearPredecessorTable()
    {
        DefaultTableModel model = (DefaultTableModel) predecessorTable.getModel();
        for( int i = 0; i < 4; i++ )
        {
            for( int j = 1; j < 4; j++ )
            {
                model.setValueAt( "", i, j );

            }
        }
    }
}
