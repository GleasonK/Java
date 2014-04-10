//File:

public class emptyC<Key extends Comparable<Key>, Value> implements ImRedBlack<Key, Value> {

    public boolean isEmpty() { return true; }

    public int size() { return 0; }

    public boolean contains(Key key) { return false; }

    public Value get(Key key) { return null; }

    public ImRedBlack<Key, Value> put(Key key, Value val) {
        return new ImRedBlackC(key, val, this, this);
    }

    public String toString() { return "EMPTY"; }

}
