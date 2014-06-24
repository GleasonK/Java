// File: LinkedDeque
// Name: Kevin Gleason
// Date: Feb 2, 2014
// Use: One implementation of the Deque interface


public class LinkedDeque<T> implements Deque<T> {
    // Instance Variables
    private Node front;
    private Node back;
    private int N;

    // Constructor

    public LinkedDeque() {
        this.front = null;
        this.back = null;
        this.N = 0;
    }

    // Define node class
    private class Node {
        // Instance Variables
        private T info;
        private Node next;
        private Node previous;

        private Node(T info) {
            this.info = info;
            this.next = null;
            this.previous = null;
        }

        private Node getNext() {return this.next;}
        private Node getPrevious() {return this.previous;}
        private T getInfo() {return this.info;}
        private void setPrevious(Node n) {this.previous = n;}
        private void setNext(Node n) {this.next = n;} // To make it easier to set
    }

    public void pushLeft(T info) {
        Node temp = new Node(info);
        if (isEmpty()){
            this.front = temp;
            this.back = temp;
        }
        else {
            temp.setNext(this.front);
            this.front.setPrevious(temp);
            this.front = temp;
        }
        this.N++;

    }


    public void pushRight(T info){
        Node temp = new Node(info);
        if (isEmpty()){
            this.front = temp;
            this.back = temp;
        }
        else {
            this.back.setNext(temp);
            temp.setPrevious(this.back);
            this.back = temp;
        }
        this.N++;
    }

    public T popLeft() {
        if (this.isEmpty())
            throw new RuntimeException("Stack Underflow.");
        else {
            T topEle = this.front.getInfo();
            this.front = this.front.getNext();
            this.front.setPrevious(null);
            this.N--;
            if (this.N == 0)
                this.back = null;
            return topEle;
        }
    }

    public T popRight() {
        if (this.isEmpty())
            throw new RuntimeException("Stack Underflow.");
        else {
            T backEle = this.back.getInfo();
            this.back = this.back.getPrevious();
            this.back.setNext(null);
            this.N--;
            if (this.N == 0)
                this.front = null;
            return backEle;
        }
    }

    public int size() {return this.N;}

    public boolean isEmpty() { return this.N == 0;}

    public String toString(){
        // Use Sting Builder?
        StringBuilder sb = new StringBuilder("Queue = ");
        Node temp = this.front;
        while(temp!=null) {
            sb.append(temp.getInfo() + " ");
            temp = temp.getNext();
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        Deque<String> Ds = new LinkedDeque<String>();
        Ds.pushLeft("Hello");
        Ds.pushRight("KevinRight");
        Ds.pushLeft("KevinLeft");
        System.out.println("Three Pushes - " + Ds.toString());
        Ds.popLeft();
        System.out.println("Popped Left - " + Ds.toString());
        Ds.pushRight("188");
        System.out.println("Push Right - " + Ds.toString());
        Ds.popRight();
        System.out.println("Pop Right - " + Ds.toString());
        System.out.println(Ds.toString());
        Ds.pushRight("KevinRight2");
        System.out.println(Ds.toString() + " Stack Size: " + Ds.size());
        Ds.popLeft();
        Ds.popLeft();
        System.out.println("Popping until empty: " + Ds.toString());
        // Ds.popRight();  // Uncomment for error messages.


    }


}
