//File:

public class emptyC<Key extends Comparable<Key>, Value> implements ImRedBlack<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private boolean color = BLACK;



    public boolean isEmpty() { return true; }

    public int size() { return 0; }

    public boolean contains(Key key) { return false; }

    public Value get(Key key) { return null; }

    public ImRedBlack<Key, Value> put(Key key, Value val, String s) {
        return new ImRedBlackC(key, val, RED, new emptyC<Key, Value>(), new emptyC<Key, Value>());
    }

    public ImRedBlack<Key, Value> put(Key key, Value val) {
        //System.out.println("NEW EMPTY");  //DEBUG
        return new ImRedBlackC(key, val, RED, new emptyC<Key, Value>(), new emptyC<Key, Value>());
    }

    public String toString() { System.out.print(" -- ");
        return "";
    }


    //Needed or even true?
    public boolean isRed(){return false;}

    //Need these?
    public emptyC getRight(){return this;}
    public emptyC getLeft(){return this;}
    public boolean getColor() { return BLACK; }

    //Set the private information
    public void setLeft(ImRedBlack irb){}
    public void setRight(ImRedBlack irb){}
    public void setColor(boolean color) {}
    public void setSize(int n){}

    //FIX?
    public ImRedBlack fix(){return this;}
    public String toStringLine() {return "--";}
}
