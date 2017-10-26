/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chord;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author erang
 */
public class FingertableImpl implements FingerTable {

    Node[] fingerEntries;
    private final int maxEntries;

    public FingertableImpl(int maxEntries) {
        this.maxEntries = maxEntries;
        this.fingerEntries = new Node[maxEntries];
    }

    @Override
    public Node[] getFingerEntries(){
        return this.fingerEntries;
    }
    
    @Override
    public int searchEntries(int key){
        for (int i=0; i<maxEntries; i++){
            if (fingerEntries[i].getID() == key)
                return i;
        }
        return -1;
    }
    
    @Override
    public Node getNode(int key) {
        for (Node node : fingerEntries){
            if (node.getID() == key)
                return node;
        }
        return null;
    }

    @Override
    public void updateEntry(int index, Node node) {
        this.fingerEntries[index] = node;
    }

    @Override
    public Node getClosestPredecessorToKey(int Destkey) {
        int maxBeforeKey = Integer.MIN_VALUE;
        boolean found = false;
        Node ClosestPredecessor = null;
        
        for (int i=0; i<maxEntries; i++) {
            int key = fingerEntries[i].getID();
            if(key < Destkey && key > maxBeforeKey){
                maxBeforeKey = i;
                found = true;
            }
        }
        
        if (found) {
            ClosestPredecessor = this.fingerEntries[maxBeforeKey];
        }
        return ClosestPredecessor;
    }

    /*@Override
    public void removeEntry(int key) {
        this.fingerEntries.remove(key);
    }*/

    @Override
    public Node getNodeAt(int index) {
        if (index >=0 && index < maxEntries)
            return fingerEntries[index];
        return null;
    }
}
