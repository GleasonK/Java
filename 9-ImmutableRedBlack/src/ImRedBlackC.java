//File: ImRedBlack.java
//Name: Kevin Gleason
//Date: April 10, 2014
//Use: Implementation for the Immutable Red-Black BST


public class ImRedBlackC<Key extends Comparable<Key>, Value> implements ImRedBlack<Key, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    //Instance Variables
    private boolean color;
    private int N;
    private Key key;
    private Value val;
    private ImRedBlack<Key, Value> left;
    private ImRedBlack<Key, Value> right;

    //Constructor, Set all the instance variables
    public ImRedBlackC(Key key, Value val, boolean color, ImRedBlack left, ImRedBlack right) {
        this.val = val;
        this.key = key;
        this.color = color;
        this.left = left;
        this.right = right;
        this.N = this.left.size() + this.right.size() + 1;
    }

    //Handler function for put to set the root as black after returning.
    public ImRedBlack<Key, Value> put(Key key, Value val){
        ImRedBlack tree = put(key,val, "");
        tree.setColor(BLACK);
        return tree;
    }

    //Will not be empty, since only EmptyC will return empty
    public boolean isEmpty(){
        return false;
    }

    public int size(){
        return this.N;
    }

    public boolean contains(Key key){
        return this.get(key) != null;
    }

    //Recursively search through tree
    //If it finds a key match it returns the value. If it does not it uses the EmptyC get function to return null
    public Value get(Key key){
        int comp = this.key.compareTo(key);
        if (comp < 0) return this.right.get(key);
        if (comp > 0) return this.left.get(key);
        return this.val;
    }

    //Pseudo put function so that the tree can be changed to black prior to returning to user.
    //Created new trees each call to properly mutate and return the correct type.
    //Function recursively puts each level of the tree in and then recursively calls the fix function
    //In case of conflict, it overrides the key with a new value. Ok since Value is not used to compare.
    public ImRedBlack<Key, Value> put(Key key, Value val, String s){
        int comp = this.key.compareTo(key);
        if (comp == 0)
            return new ImRedBlackC<Key, Value>(key, val, this.color, this.left, this.right);
        else if (comp < 0)
            return new ImRedBlackC<Key, Value>(this.key, this.val, this.color, this.left, this.right.put(key, val,"")).fix();
        else
            return new ImRedBlackC<Key, Value>(this.key, this.val, this.color, this.left.put(key, val,""), this.right).fix();
    }

    //Fix all the issues with the RB Tree. Called recursively to fix all three.
    public ImRedBlack fix(){
        if (this.getRight().isRed() && !this.getLeft().isRed()) { return this.rotateLeft().fix(); }
        if (this.getLeft().isRed()  &&  this.getLeft().getLeft().isRed())  return this.rotateRight().fix();
        if (this.getLeft().isRed()  &&  this.right.isRed())  return this.flipColors();
        else return this;
    }

    //Rotate the Tree left in case of Right-Leaning red link.
    private ImRedBlack rotateLeft(){
        ImRedBlack<Key, Value> rt = this.getRight();
        this.setRight(rt.getLeft());
        rt.setLeft(this);
        rt.setColor(this.getColor());
        this.setColor(RED);
        rt.setSize(this.N);
        this.N = this.left.size() + this.right.size() + 1;
        return rt;
    }

    //Rotate the Tree right in case of double Left-Leaning links.
    private ImRedBlack rotateRight(){
        ImRedBlack<Key, Value> lt = this.getLeft();
        this.setLeft(lt.getRight());
        lt.setRight(this);
        lt.setColor(this.getColor());
        this.setColor(RED);
        lt.setSize(this.N);
        this.N = this.getLeft().size() + this.getRight().size() + 1;
        return lt;
    }

    //Flip the RED and BLACK in case of both sides being a RED link.
    private ImRedBlack flipColors(){
        this.color = !this.color;
        this.getLeft().setColor(!this.getLeft().getColor());
        this.getRight().setColor(!this.getRight().getColor());
        return this;
    }

    //Helper function for fix, used to check if a left or right child is red
    public boolean isRed(){
        return this.color == RED;
    }

    //Helper toString function to show the structure of the tree as key:value:color with arrows pointing up to root
    public String toStringStructure(){
        System.out.print(this.key + ":" + this.val + ":" + this.showColor());
        System.out.print(" <--(Lt ");
            this.getLeft().toStringStructure();
        System.out.print(" Rt ");
            this.getRight().toStringStructure();
        System.out.print(")");
        return "";
    }

    //toString function to show the elements in order from left to right (least to greatest) with key:val:color
    public String toString(){
        return this.getLeft().toString() + this.key + ":" + this.val + ":" + this.showColor() + " " + this.getRight().toString();
    }

    //Get the private information
    public ImRedBlack getLeft(){ return this.left; }
    public ImRedBlack getRight(){ return this.right; }
    public boolean getColor() { return this.color; }

    //Helper function for toString()
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
        ImRedBlack<String, Integer> irb = new EmptyC<String, Integer>();
        System.out.println("Put 'F', Put 'L'");
        irb = irb.put("F", 5);
        irb = irb.put("L", 4);
        System.out.println("Size of tree is: " + irb.size());
        System.out.println(irb.toString());
        System.out.println(irb.toStringStructure());

        irb = irb.put("O", 14);
        irb = irb.put("R", 600);
        irb = irb.put("I", 2);
        irb = irb.put("D", 90);
        irb = irb.put("A", 45);
        irb = irb.put("A", 8);
        System.out.println("\nPut 'O', Put 'R', Put 'I', Put 'D', Put 'A => 45', Put 'A => 8'");
        System.out.println("Immutable RedBlack isEmpty: " + irb.isEmpty());
        System.out.println("Size of tree is: " + irb.size());
        System.out.println("Contains F: " + irb.contains("F"));
        System.out.println("Contains Q: " + irb.contains("Q"));
        System.out.println("Get F: " + irb.get("F"));
        System.out.println("Get A: " + irb.get("A"));
        System.out.println(irb.toString());
        System.out.println(irb.toStringStructure());

    }

}
