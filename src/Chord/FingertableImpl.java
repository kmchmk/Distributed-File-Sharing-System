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
    public Node getClosestPredecessorToKey(int key) {
//            Iterator it = this.fingerEntries..iterator();
//    while (it.hasNext()) {
//        Map.Entry pair = (Map.Entry)it.next();
//        System.out.println(pair.getKey() + " = " + pair.getValue());
//        it.remove(); // avoids a ConcurrentModificationException
//    }
        return null;
    }
}
