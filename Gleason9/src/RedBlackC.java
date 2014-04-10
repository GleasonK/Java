/**
 * Created by GleasonK on 4/9/14.
 */
public class RedBlackC<Key extends Comparable<Key>, Value> implements RedBlack {
    private int N;
    private Key key;
    private Value val;
    private  RedBlack<Key, Value> left;
    private RedBlack<Key, Value> right;
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

    public RedBlackC(Key key, Value val, RedBlack left, RedBlack right) {
        this.val = val;
        this.key = key;
        this.left = left;
        this.right = right;
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

    public RedBlack<Key, Value> put(Key key, Value val){
        this.N++;

    }

    public String toString(){
        return "Debug";
    }
}
