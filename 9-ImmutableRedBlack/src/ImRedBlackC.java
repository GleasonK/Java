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

    public ImRedBlack put(Key key, Value val){
        ImRedBlack tree = put(key,val, "");

//        System.out.println(tree.getColor());

        tree.setColor(BLACK);

//        System.out.println(tree.getColor());
//        System.out.println("\nReturn Tree");
//        tree.toString();

        return tree;
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

    public Value get(Key key){
        int comp = this.key.compareTo(key);
        if (comp < 0) return this.right.get(key);
        if (comp > 0) return this.left.get(key);
        return this.val;
    }

    public ImRedBlack<Key, Value> put(Key key, Value val, String s){
        int comp = this.key.compareTo(key);
        if (comp == 0) {
            //LOOK AT COLOR
            return new ImRedBlackC<Key, Value>(key, val, this.color, this.left, this.right);

        }
        else if (comp < 0) {
            ImRedBlack i = new ImRedBlackC<Key, Value>(this.key, this.val, this.color, this.left, this.right.put(key, val,""));
            //NOT CALLED UNTIL RECURSE DONE, Need to make something recurse up the tree.
            i = i.fix();

//            System.out.println("\nI.FIX()");
//            i.toString();

            return i;

            //return new ImRedBlackC<Key, Value>(this.key, this.val, RED, this.left, this.right.put(key, val).fix());
        }
        else {
            ImRedBlack i = new ImRedBlackC<Key, Value>(this.key, this.val, this.color, this.left.put(key, val,""), this.right);
            i = i.fix();

//            System.out.println("\nI.FIX()");
//            i.toString();

            return i;
            //return new ImRedBlackC<Key, Value>(this.key, this.val, RED, this.left.put(key, val,""), this.right).fix();

        }
    }


    //Need to write rotate functions that return ImRedBlack
    public ImRedBlack fix(){
        ImRedBlack i;
        if (this.getRight().isRed() && !this.getLeft().isRed()) {
            i = this.rotateLeft();

        }


        if (this.getLeft().isRed()  &&  this.getLeft().getLeft().isRed())  return this.rotateRight();
        if (this.getLeft().isRed()  &&  this.right.isRed())  return this.flipColors();
        else {System.out.println("\nRETURN ELSE CALLED ");return this;}
    }

    private ImRedBlack rotateLeft(){
        System.out.println("\nROTATE LEFT CALLED"); //Debug
        System.out.println("this:"); //Debug

//        this.toString();  //DEBUG
//        System.out.println();

        ImRedBlack rt = this.getRight();
        this.setRight(rt.getLeft());

//        System.out.println("this after setRight:"); //Debug
//        this.toString(); //DEBUG

        rt.setLeft(this);


//        System.out.println("\nRT rotate Left"); //Debug
//        rt.toString(); //DEBUG
//        System.out.println("\ndone:"); //Debug

        rt.setColor(this.getColor());
        this.setColor(RED);
        rt.setSize(this.N);
        this.N = this.left.size() + this.right.size() + 1;

//        System.out.println("\nRT Returned:"); //Debug
//        rt.toString(); //DEBUG

        return rt;
    }

    private ImRedBlack rotateRight(){
        System.out.println("\n ROTATE RIGHT CALLED");
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
        System.out.println("\nFLIP COLORS CALLED");
        this.color = !this.color;
        this.getLeft().setColor(!this.getLeft().getColor());
        this.getRight().setColor(!this.getRight().getColor());
        return this;
    }

    private void makeBlack(){
        this.setColor(BLACK);
    }

    public boolean isRed(){
        return this.color == RED;
    }

    public String toString(){

        System.out.print("(" + this.key + ":" + this.val + ":" + this.showColor());
        System.out.print(" L");
            this.getLeft().toString();
        //System.out.print("" + this.key + ":" + this.showColor()); //+ ":" + this.val + ":" + this.showColor());
        System.out.print(" R");
            this.getRight().toString();
        System.out.print(")");
        //System.out.println();
        return "";
    }

    public String toStringLine(){
        return this.getLeft().toStringLine() + this.key + ":" + this.val + ":" + this.showColor() + this.getRight().toStringLine();
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
        irb.toString();System.out.println(" 2 ");
        irb = irb.put("O",3);
        irb.toString();System.out.println(" O ");
        irb = irb.put("R",3);
        irb.toString();System.out.println(" R ");
        System.out.println(irb.toStringLine());
        irb = irb.put("I",3);
        irb.toString();System.out.println();
        irb = irb.put("D",3);
        irb.toString();System.out.println();
        irb = irb.put("A",3);
        irb.toString();System.out.println();
        irb = irb.put("A",3);
        irb.toString();System.out.println();
        System.out.println(irb.toStringLine());
    }

}
