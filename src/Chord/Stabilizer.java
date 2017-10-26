/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chord;

/**
 *
 * @author Irfad Hussain
 */
public class Stabilizer implements Runnable {
    
    private Node me;
    
    public Stabilizer(Node node){
        this.me = node;
    }

    @Override
    public void run() {
        Node currentSuccessor = me.getSuccessor();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
