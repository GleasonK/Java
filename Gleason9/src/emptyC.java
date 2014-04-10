//File:

import java.util.NoSuchElementException;

public class emptyC<Key extends Comparable<Key>, Value> implements RedBlack<Key, Value>{

    public boolean isEmpty() { return true; }

    public int size() { return 0; }

    public boolean contains(Key key) { return false; }

    public Value get(Key key) { return null; }

    public RedBlack<Key, Value> put(Key key, Value val) {
        return new RedBlackC(key, val, this, this);
    }

    public String toString() { return "EMPTY"; }

}
