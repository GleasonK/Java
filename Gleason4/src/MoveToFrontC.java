// File: MoveToFront.java
// Name: Kevin Gleason
// Date: Feb 2, 2014
// Use: Move to front method

public class MoveToFrontC<T> implements MoveToFront<T>{
    // Instance Variables
    private Node front;
    private Node back;
    private int N;

    // Constructor

    public MoveToFrontC() {
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

    public void push(T info) {
        Node temp = new Node(info);
        if (isEmpty()){
            this.front = temp;
            this.back = temp;
        }
        else if (inList(info)) {
            temp.setNext(this.front);
            this.front.setPrevious(temp);
            this.front = temp;
        }
        else {
            temp.setNext(this.front);
            this.front.setPrevious(temp);
            this.front = temp;
        }
        this.N++;

    }

    private boolean inList(T character) {
        Node temp = this.front;
        while(temp!=null) {
            if (temp.getInfo() == character) {
                removeFromLink(character); //Removes from the list if in it
                this.N--;
                return true;
            }
            else
                temp = temp.getNext();
        }
        return false;
    }

    public void removeFromLink(T character) {
        Node temp = this.front;
        while(temp!=null) {
            if (temp.getInfo() == character && temp.getNext()==null) {
                this.back = temp.getPrevious();
                //temp.previous = null; //Removes the character if it is the last item in the list

            }
            else if (temp.getInfo() == character && temp.getPrevious()==null) {
                this.front = temp.getNext();  //May need to tweak
                //temp.
            }
            else if (temp.getInfo() == character) {
                Node prev = temp.getPrevious();
                Node next = temp.getNext();
                prev.next = next;
                next.previous = prev;
                temp.previous = null;
                temp.next=null;
            }
            else
                temp = temp.getNext();
        }
    }

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
        MoveToFront<String> movingList = new MoveToFrontC<String>();
        movingList.push("a");
        movingList.push("b");
        movingList.push("c");
        //movingList.push("c");
        System.out.println("Three Pushes - " + movingList.toString());
    }

}
