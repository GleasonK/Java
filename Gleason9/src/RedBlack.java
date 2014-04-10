//File: RedBlack.java
//
//
//

public interface RedBlack<Key extends Comparable<Key>, Value> {
    public Key getKey();
    public boolean isEmpty();
    public int size();
    public boolean contains(Key key);
    public Value get(Key key);
    public RedBlack<Key, Value> put(Key key, Value val);
    public String toString();
}
