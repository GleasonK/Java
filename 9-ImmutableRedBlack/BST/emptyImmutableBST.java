/********************************************************************************************
 * File: emptyImmutableBST.java
 * Author: Jason Morse
 * Date: November 11, 2013
 * Class: Computer Science II - Problem Set 7
**********************************************************************************************/

import java.util.NoSuchElementException;

public class emptyImmutableBST<Key extends Comparable<Key>, Value> implements BST<Key, Value> {
    
    public boolean isEmpty() { return true; }
    
    public int size() { return 0; }
    
    public boolean contains(Key key) { return false; }
    
    public Value get(Key key) { return null; }
    
    public BST<Key, Value> put(Key key, Value val) {
        return new immutableBST(key, val, this,this);
    }
    
    public BST<Key, Value> deleteMin() { return this; }
    
    public BST<Key, Value> deleteMax() { return this; }
    
    public BST<Key, Value> delete(Key key) { return this; }
    
    public Key min() { throw new NoSuchElementException(); }
    
    public Key max() { throw new NoSuchElementException(); }
    
    public String toString() { return "--"; }
    
    public Key floor(Key key) { return null; }
    
    public int height() {return 0; }
    
}