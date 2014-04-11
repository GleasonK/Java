//File: RedBlack.java
//
//
//

public interface ImRedBlack<Key extends Comparable<Key>, Value> {
    public boolean isEmpty();
    public int size();
    public boolean contains(Key key);
    public Value get(Key key);
    public ImRedBlack<Key, Value> put(Key key, Value val);
    public String toString();

    //Questioning
    public ImRedBlack fix();
    public ImRedBlack getLeft();
    public ImRedBlack getRight();
    public boolean getColor();

    public void setLeft(ImRedBlack irb);
    public void setRight(ImRedBlack irb);
    public void setColor(boolean color);
    public void setSize(int n);
    public boolean isRed();

    public String toStringLine();


    public ImRedBlack<Key, Value> put(Key key, Value val, String s);
}
