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
    
    private Node thisNode;
    
    
    public Stabilizer(Node thisNode){
        this.thisNode = thisNode;
    }

    @Override
    public void run() {
        while(true){
            try{
                // send predecessor req
                Thread.sleep(5000);
            }catch(InterruptedException ex){
                
            }
        }
    }
    
}
