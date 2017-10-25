/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Chord;

/**
 *
 * @author erang
 */
public interface FingerTable {
    public Node getNode(int key);
    public void updateEntry(int key, Node node);
    public void removeEntry(int key);
    public Node getClosestPredecessorToKey(int key);
}
