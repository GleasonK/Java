// Name: Kevin Gleason
// Date: March 15, 2013
// File: MaxPQC.java
// Use: Implements the Linked MaxPQ interface

import java.util.*;

public class MaxPQC<Key extends Comparable<Key>> implements MaxPQ<Key>{

    //Instance Variables
    private int N;
    private Node root;
    private Node current;

    private class Node {
        private Key info;
        private Node left;
        private Node right;
        private Node parent;

        private Node(Key info) {
            this.info = info;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        private Key getInfo() {return this.info;}
        private Node getLeft() {return this.left;}
        private Node getRight() {return this.right;}
        private Node getParent() {return this.parent;}
        private void setInfo(Key i) {this.info = i;}
        private void setLeft(Node n) {this.left = n;}
        private void setRight(Node n) {this.right = n;}
        private void setParent(Node n) {this.parent = n;}
    }

    // Constructor for PQC
    public MaxPQC(){
        this.root = null;
        this.current = this.root;
        this.N = 0;

    }
    private void find(int loc) {     // Find to just move current pointer
        this.current = this.root;
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

    private void swim(Node swimmer) {
        if (swimmer.getParent() != null && swimmer.getInfo().compareTo(swimmer.getParent().getInfo()) > 0) {
            exch(swimmer, swimmer.getParent());
            swim(swimmer.getParent());
        }
    }

    private void sink(Node sinker) {
        if (sinker.getLeft() != null) { //Always will be a node to left, if not then you cant sink
            Key big; //Bigger value - Check which is bigger of children
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
    private void exch(Node n, Node other) {
        Key nInfo = n.getInfo();
        n.setInfo(other.getInfo());
        other.setInfo(nInfo);
    }

    public Key delMax(){
        if (this.root == null)  //If no elements have been added yet.
            throw new NoSuchElementException("No Keys have been added yet.");
        Node last = null;                //The last added node
        Key nInfo = this.root.getInfo(); //The root info returned
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


    public void insert(Key key){
        Node insertion = new Node(key);
        this.N++;
        if (this.root == null) {
            this.root = insertion;
            //this.current = insertion;
        }
        else {
            find(this.N);
            insertion.setParent(this.current);
            if (this.N%2 == 0) insertion.getParent().setLeft(insertion);
            else insertion.getParent().setRight(insertion);
        }
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