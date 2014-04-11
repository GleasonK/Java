//File:

public class EmptyC<Key extends Comparable<Key>, Value> implements ImRedBlack<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private boolean color = BLACK;

    public boolean isEmpty() { return true; }

    public int size() { return 0; }

    public boolean contains(Key key) { return false; }

    public Value get(Key key) { return null; }

    public ImRedBlack<Key, Value> put(Key key, Value val, String s) {
        return new ImRedBlackC(key, val, RED, new EmptyC<Key, Value>(), new EmptyC<Key, Value>());
    }

    public ImRedBlack<Key, Value> put(Key key, Value val) {
        return new ImRedBlackC(key, val, RED, new EmptyC<Key, Value>(), new EmptyC<Key, Value>());
    }

    public String toString() { return " -- ";}

    public boolean isRed(){return false;}

    //Getter Functions
    public EmptyC getRight(){return this;}
    public EmptyC getLeft(){return this;}
    public boolean getColor() { return this.color; }

    //Set the private information
    public void setLeft(ImRedBlack irb) {}
    public void setRight(ImRedBlack irb){}
    public void setColor(boolean color) {}
    public void setSize(int n){}

    //FIX?
    public ImRedBlack fix(){return this;}
    public String toStringStructure() { System.out.print(" -- "); return "";}
}
