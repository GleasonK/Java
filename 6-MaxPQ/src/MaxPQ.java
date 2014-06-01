// Name: Kevin Gleason
// Date: March 15, 2013
// File: MaxPQ.java
// Use: The MaxPQ interface.

public interface MaxPQ<Key extends Comparable<Key>> {
    public Key delMax();
    public void insert(Key key);
    public boolean isEmpty();
    public int size();
    public String toString();
}
