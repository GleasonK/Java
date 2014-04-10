/********************************************************************************************
 * File: BST.java
 * Author: Jason Morse
 * Date: November 11, 2013
 * Class: Computer Science II - Problem Set 7
**********************************************************************************************/

// BST Interface
public interface BST<Key extends Comparable<Key>, Value> { 
    public boolean isEmpty();
    public int size();
    public boolean contains(Key key);
    public Value get(Key key);
    public BST<Key, Value> put(Key key, Value val);
    public BST<Key, Value> deleteMin();
    public BST<Key, Value> deleteMax();
    public BST<Key, Value> delete(Key key);
    public Key min();
    public Key max();
    public String toString();
    public Key floor(Key key);
    public int height(); 
}