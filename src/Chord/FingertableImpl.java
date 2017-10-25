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

    Map<Integer, Node> fingerEntries;
    private final int maxEntries;

    public FingertableImpl(int maxEntries) {
        this.maxEntries = maxEntries;
        this.fingerEntries = new HashMap<>(maxEntries);
    }

    @Override
    public Map<Integer, Node> getFingerEntries(){
        return this.fingerEntries;
    }
    
    @Override
    public boolean searchEntries(int key){
        return this.fingerEntries.containsKey(key);
    }
    
    @Override
    public Node getNode(int key) {
        if (this.fingerEntries.containsKey(key)) {
            return this.fingerEntries.get(key);
        } else {
            return null;
        }
    }

    @Override
    public void updateEntry(int key, Node node) {
        this.fingerEntries.replace(key, node);
    }

    @Override
    public Node getClosestPredecessorToKey(int Destkey) {
        int maxBeforeKey = Integer.MIN_VALUE;
        boolean found = false;
        Node ClosestPredecessor = null;
        
        for (int key : this.fingerEntries.keySet()) {
            if(key < Destkey && key > maxBeforeKey){
                maxBeforeKey = key;
                found = true;
            }
        }
        
        if (found) {
            ClosestPredecessor = this.fingerEntries.get(maxBeforeKey);
        }
        return ClosestPredecessor;
    }

    @Override
    public void removeEntry(int key) {
        this.fingerEntries.remove(key);
    }
}
