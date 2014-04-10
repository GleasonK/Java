//File: ImRedBlack.java
//Name: Kevin Gleason
//Date: April 10, 2014
//Use: Implementation for the Immutable Red-Black BST


public class ImRedBlackC<Key extends Comparable<Key>, Value> implements ImRedBlack<Key, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private boolean color;
    private int N;
    private Key key;
    private Value val;
    private ImRedBlack<Key, Value> left;
    private ImRedBlack<Key, Value> right;

    public ImRedBlackC(Key key, Value val, boolean color, ImRedBlack left, ImRedBlack right) {
        this.val = val;
        this.key = key;
        this.color = color;
        this.left = left;
        this.right = right;
        this.N = this.left.size() + this.right.size() + 1;
    }

    public boolean isEmpty(){
        return false;
    }

    public int size(){
        return this.N;
    }

    public boolean contains(Key key){
        return this.get(key) != null;
    }

//    public Value get(Node node, Key key){
//        int comp = node.key.compareTo(key);
//        if (comp < 0) return get(node.getRight(), key);
//        if (comp > 0) return get(node.getLeft(), key);
//        return node.val;
//    }

    public Value get(Key key){
        int comp = this.key.compareTo(key);
        if (comp < 0) return this.right.get(key);
        if (comp > 0) return this.left.get(key);
        return this.val;
    }

    public ImRedBlack<Key, Value> put(Key key, Value val){
        //CHECK FOR FIX
        //Find the node to attach
        int comp = this.key.compareTo(key);
        if (comp == 0) {
            ////////////////Red the right color?
            //return new ImRedBlackC<Key, Value>(key, val, RED,this.left, this.right).fix();
            ImRedBlack<Key, Value> t = new ImRedBlackC<Key, Value>(key, val, RED, this.left, this.right).fix();
            return new ImRedBlackC<Key, Value>(key, val, RED, this.left, this.right).fix();

            //System.out.println("COMP == 0"); //DEBUG
            
        }
        else if (comp < 0) {
            return new ImRedBlackC<Key, Value>(this.key, this.val, RED, this.left, this.right.put(key, val).fix());
            //ImRedBlack<Key, Value> t = new ImRedBlackC<Key, Value>(this.key, this.val, RED, this.left, this.right.put(key, val));
            //t.fix();
            //System.out.println("COMP < 0, move right"); //DEBUG
            //return t;
            //return new ImRedBlackC<Key, Value>(this.key, this.val, RED, this.left, this.right.put(key, val));
        }
        else {
            ImRedBlack<Key, Value> t = new ImRedBlackC<Key, Value>(this.key, this.val, RED, this.left.put(key, val), this.right);
            t.fix();
            //System.out.println("COMP > 0 move left"); //DEBUG
            return t;
        }
    }


    //Need to write rotate functions that return ImRedBlack
    public ImRedBlack fix(){
        if (this.getRight().isRed() && !this.getLeft().isRed())  return this.rotateLeft();
        if (this.getLeft().isRed()  &&  this.getLeft().getLeft().isRed())  return this.rotateRight();
        if (this.getLeft().isRed()  &&  this.right.isRed())  return this.flipColors();
        else return this;
    }

    private ImRedBlack rotateLeft(){
        ImRedBlack rt = this.getRight();
        this.setRight(rt.getLeft());
        rt.setLeft(this);
        rt.setColor(this.getColor());
        this.setColor(RED);
        rt.setSize(this.N);
        this.N = this.left.size() + this.right.size() + 1;
        return rt;
    }

    private ImRedBlack rotateRight(){
        ImRedBlack lt = this.getLeft();
        this.setLeft(lt.getRight());
        lt.setRight(this);
        lt.setColor(this.getColor());
        this.setColor(RED);
        lt.setSize(this.N);
        this.N = this.getLeft().size() + this.getRight().size() + 1;
        return lt;

    }

    private ImRedBlack flipColors(){
        this.color = !this.color;
        this.getLeft().setColor(!this.getLeft().getColor());
        this.getRight().setColor(!this.getRight().getColor());
        return this;
    }

    public boolean isRed(){
        return this.color == RED;
    }

    public String toString(){
        //System.out.println("Printing tree");
        System.out.print("(" + this.key + ":" + this.val + ":" + this.showColor());
        System.out.print(" L"); this.getLeft().toString();
        System.out.print(" R");this.getRight().toString();
        System.out.print(")");
        //System.out.println();
        return "";
    }

    //Get the private information
    public ImRedBlack getLeft(){return this.left;}
    public ImRedBlack getRight(){return this.right;}

    public boolean getColor() {
            return this.color;
    }

    private String showColor(){
        if (getColor()) return "red";
        else return "black";
    }

    //Set the private information
    public void setLeft(ImRedBlack irb){ this.left = irb; }
    public void setRight(ImRedBlack irb){ this.right = irb; }
    public void setColor(boolean color) { this.color = color; }
    public void setSize(int n){ this.N = n; }

    public static void main (String[] args){
        ImRedBlack<String, Integer> irb = new emptyC<String, Integer>();
        irb = irb.put("F", 1);
        irb.toString();System.out.println();
        irb = irb.put("L",2);
        irb.toString();System.out.println();
        irb = irb.put("O",3);
        irb.toString();System.out.println();
        irb = irb.put("R",3);
        irb.toString();System.out.println();
        irb = irb.put("I",3);
        irb.toString();System.out.println();
        irb = irb.put("D",3);
        irb.toString();System.out.println();
        irb = irb.put("A",3);
        irb.toString();System.out.println();
//        irb = irb.put("A",3);
//        irb.toString();System.out.println();
    }

}
