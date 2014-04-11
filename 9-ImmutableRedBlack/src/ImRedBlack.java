//File: ImRedBlack.java
//Author: Kevin Gleason
//Date: 4/11/14
//Use: Interface for the ImRedBlackC and EmptyC Classes

public interface ImRedBlack<Key extends Comparable<Key>, Value> {
    public boolean isEmpty();
    public int size();
    public boolean contains(Key key);
    public Value get(Key key);
    public ImRedBlack<Key, Value> put(Key key, Value val);
    public String toString();

    //Fix and Getters
    public ImRedBlack fix();
    public ImRedBlack getLeft();
    public ImRedBlack getRight();
    public boolean getColor();

    //Setters
    public void setLeft(ImRedBlack irb);
    public void setRight(ImRedBlack irb);
    public void setColor(boolean color);
    public void setSize(int n);
    public boolean isRed();

    //Additional toString
    public String toStringStructure();

    //Pseudo put
    public ImRedBlack<Key, Value> put(Key key, Value val, String s);
}
