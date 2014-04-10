/**
 * Created by GleasonK on 4/9/14.
 */
public class ImRedBlackC<Key extends Comparable<Key>, Value> implements ImRedBlack<Key, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private boolean color;
    private int N;
    private Key key;
    private Value val;
    private ImRedBlack<Key, Value> left;
    private ImRedBlack<Key, Value> right;
    //private Node root;


//    private class Node {
//        private Key key;           //  key
//        private Value val;         //  value
//        private Node left;
//        private Node right;  // left and right subtrees
//
//        private boolean color;
//
//
//        public Node(Key key, Value val, boolean color, int N) {
//            this.key = key;
//            this.val = val;
//
//            this.color = color;
//            this.left = null;
//            this.right = null;
//        }
//        private Node getLeft(){
//            return this.left;
//        }
//        private Node getRight(){
//            return this.right;
//        }
//    }

//    private void boolean makeblack (Node node){
//        node.color = True
//    }

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
        if (comp == 0)
            return new ImRedBlackC<Key, Value>(key, val, this.left, this.right);
        else if (comp < 0)
            return new ImRedBlackC<Key, Value>(this.key, this.val, this.left, this.right.put(key, val));
        else
            return new ImRedBlackC<Key, Value>(this.key, this.val, this.left.put(key,val), this.right);
    }


    //Need to write rotate functions that return ImRedBlack
    private ImRedBlack fix(){
        if (this.getRight().isRed() && !this.getLeft().isRed()) return rotateLeft(this);
        if (this.getLeft().isRed()  &&  this.getLeft().getLeft().isRed()) return rotateRight(this);
        if (this.getLeft().isRed()  &&  isRed(this.right)) return flipColors(h);
    }

    public boolean isRed(){
        return this.color == RED;
    }

    public String toString(){
        return "Debug";
    }

    public ImRedBlack getLeft(){return this.left;}

    public ImRedBlack getRight(){return this.right;}


}
