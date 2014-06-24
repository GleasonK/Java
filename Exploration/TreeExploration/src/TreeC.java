import java.util.*;

public class TreeC<T extends Comparable<T>>{
	private T info;
    private int N;
	private TreeC<T> left;
	private TreeC<T> right;
    private TreeC<T> parent;
    private TreeC<T> current; //for insert();
	
	//constructor
	public TreeC(T info){
		this.info = info;
		this.left = null;
		this.right = null;
	}

//	public int compareTo(TreeC o)
//	{
//		// TODO: Implement
//        if (this.info.compareTo(o.getInfo()) > )
//		return 0;
//	}

    // Constructor for PQC
    private TreeC<T> find(int loc) {     // Find to just move current pointer
        // TODO: Find recursive way to get the node to be attached to
        // Should be a matter of going through based on loc%2 and loc/2

        this.current = this;
        int start = loc/2;
        Deque<Integer> directions = new LinkedList<Integer>();
        while (start > 1) {
            if (start <= 2) {directions.push(start-2); start/=2;}
            else {
                directions.push(start%2);
                start/= 2;
            }
        }
        while (directions.size() > 0 ){
            if (directions.pop() == 0) {this.current = this.current.getLeft();}
            else {
                this.current = this.current.getRight();
            }
        }
    }

    private void swim(TreeC<T> swimmer) {
        //TODO: Make recursive swim
        if (swimmer.getParent() != null && swimmer.getInfo().compareTo(swimmer.getParent().getInfo()) > 0) {
            exch(swimmer, swimmer.getParent());
            swim(swimmer.getParent());
        }
    }

    private void sink(TreeC<T> sinker) {
        //TODO: Need a recursive sink method.
        if (sinker.getLeft() != null) { //Always will be a node to left, if not then you cant sink
            T big; //Bigger value - Check which is bigger of children
            if (sinker.getRight()==null || sinker.getLeft().getInfo().compareTo(sinker.getRight().getInfo()) > 0){
                big = sinker.getLeft().getInfo();
                if (sinker.getInfo().compareTo(big) < 0) {
                    exch(sinker, sinker.getLeft());
                    sink(sinker.getLeft());
                }
            }        // easier way to do it?
            else {
                big = sinker.getRight().getInfo();
                if (sinker.getInfo().compareTo(big) < 0) { //if big is larger
                    exch(sinker, sinker.getRight());
                    sink(sinker.getRight());
                }
            }
        }
    }

    //Need to exchange index and info.
    private void exch(TreeC<T> n, TreeC<T> other) {
        //TODO: FIX
        T nInfo = n.getInfo();
        other.setInfo(n.getInfo());
        other.setInfo(this.getInfo());
    }

    public T delMax(){
        if (this.root == null)  //If no elements have been added yet.
            throw new NoSuchElementException("No Keys have been added yet.");
        Node last = null;                //The last added node
        T nInfo = this.root.getInfo(); //The root info returned
        find(this.N);
        if (this.current.getRight() != null) last = this.current.getRight();
        else if (this.current.getLeft() != null)last = this.current.getLeft();
        if (last != null) {
            exch(this.root, last);
            if (this.N % 2 == 0) this.current.setLeft(null); //Delete the max from tree now.
            else this.current.setRight(null);
            last.setParent(null);
            sink(this.root);
        }
        else this.root = null; //If root was only element, delete and set to null.
        this.N--;              //Decrement the number of elements.
        return nInfo;
    }


    public void insert(T info){
        TreeC<T> insertion = new TreeC<T>(info);
        this.N++;
        TreeC<T> current = find(this.N/2);
        insertion.setParent(this.current);
        if (this.N%2 == 0) insertion.getParent().setLeft(insertion);
        else insertion.getParent().setRight(insertion);
        swim(insertion);
    }


    public boolean isEmpty(){return this.N==0; }

    public int size(){return this.N;}

    public String toString(){  //Recursively move through tree
        System.out.print("Root: ");
        print(this.root);
        System.out.println();
        return "String Output Printed";
    }

    private void print(Node parent){  //Check if node has children.
        System.out.print(parent.getInfo() + ""); //Start at root
        if (parent.getRight() != null && parent.getLeft() != null){
            System.out.print("<--(L");
            print(parent.getLeft());
            System.out.print(" R");
            print(parent.getRight());
            System.out.print(")");
        }
        else if (parent.getLeft() != null) {  //if no left node, no children.
            System.out.print("<--(L");
            print(parent.getLeft());
        }
    }

    //Getter Functions
    private T getInfo() {return this.info;}
    private TreeC<T> getLeft() {return this.left;}
    private TreeC<T> getRight() {return this.right;}
    private TreeC<T> getParent() {return this.parent;}
    private void setInfo(T i) {this.info = i;}
    private void setLeft(TreeC<T> n) {this.left = n;}
    private void setRight(TreeC<T> n) {this.right = n;}
    private void setParent(TreeC<T> n) {this.parent = n;}

    public static void main(String[] args) {
        MaxPQ<Integer> hi = new MaxPQC<Integer>();
        System.out.println("Tree is Empty at start = " + hi.isEmpty());
        // hi.delMax(); //Test the no such element exception
        hi.insert(1);
        hi.insert(2);
        hi.insert(3);
        hi.insert(4);
        hi.insert(5);
        hi.insert(6);
        hi.insert(7);
        System.out.println(hi.toString());
        System.out.println("Size = " + hi.size());
        System.out.println("Max = " + hi.delMax());
        System.out.println(hi.toString());
        hi.insert(17);
        hi.insert(722);
        hi.insert(73);
        hi.insert(1000);
        hi.insert(73);
        System.out.println(hi.toString());
        System.out.println("Max = " + hi.delMax());
        System.out.println("Size = " + hi.size());
        System.out.println(hi.toString());
        System.out.println("Tree isEmpty at End = " + hi.isEmpty());
    }
	
}
