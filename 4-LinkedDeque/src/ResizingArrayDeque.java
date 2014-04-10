// File: ResizingArrayDeque
// Name: Kevin Gleason
// Date: Feb 2, 2014
// Use: One implementation of the Deque interface


import java.util.NoSuchElementException;

public class ResizingArrayDeque<T> implements Deque<T> {
    // Instance Variables
    private T[] q; //Queue Element
    private int N = 0; //Size
    private int first = 0; //First element index
    private int last = 0; //Last Element index


    public ResizingArrayDeque() {
        this.q = (T[]) new Object[2]; //Empty Queue capacity=2
    }

    private void resize(int capacity) {
        assert capacity >= this.N;
        T[] temp = (T[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = this.q[(this.first + i) % this.q.length];
        }
        this.q = temp;
        this.first = 0;
        this.last  = N;
    }

    public void pushLeft(T item) {  //Peek to see null left?
        if (this.N == this.q.length)
            resize(2*this.q.length);
        if (this.first == 0) {
            this.first = this.q.length-1;
            this.q[this.first] = item;
        }
        else {
            this.first--;
            this.q[this.first] = item;
        }
        this.N++;
    }


    public void pushRight(T item) { //Need to check to see if next pointer is null
        if (this.N == this.q.length)
            resize(2*this.q.length);
        this.q[this.last++] = item;
        if (this.last == this.q.length)
            this.last = 0; //Wrap it around
        this.N++;
    }
    public T popLeft() {
        if (isEmpty())
            throw new NoSuchElementException("Queue Underflow");
        T firstEle = this.q[this.first];
        this.q[this.first] = null;
        this.N--;
        this.first++;
        if (this.first == this.q.length) this.first = 0;
        if (this.N > 0 && this.N == this.q.length/4) resize(this.q.length/2);
        return firstEle;
    }
    public T popRight() {
        if (this.isEmpty())
            throw new NoSuchElementException("Queue Underflow.");
        T lastEle = this.q[this.last];
        if (this.last == 0)
            this.last = this.q.length-1; //Wrap to last
        else this.last--;
        this.N--;
        if (this.N > 0 && this.N == this.q.length/4) resize(this.q.length/2);
        return lastEle;
    }

    public int size() {return this.N;}

    public boolean isEmpty() { return this.N == 0;}

    public String toString(){
        StringBuilder sb = new StringBuilder("Queue = ");
        for (int i = 0; i < this.N; i++) {
            sb.append((this.q[(this.first + i) % this.q.length]) + " ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Deque<String> Ds = new ResizingArrayDeque<String>();
        Ds.pushLeft("Hello");
        System.out.println("One Push Left - " + Ds.toString());
        Ds.pushRight("KevinRight1");
        Ds.pushLeft("KevinLeft1");
        Ds.pushLeft("KevinLeft2");
        Ds.pushRight("KevinRight2");
        System.out.println("Pushes Lt/Rt - " + Ds.toString());
        Ds.popLeft();
        System.out.println("Popped Left - " + Ds.toString());
        Ds.popRight();
        System.out.println("Pop Right - " + Ds.toString());
        Ds.pushRight("KevinRight2");
        System.out.println("Push Right - " + Ds.toString() + "\nStack Size: " + Ds.size());
        Ds.popLeft();
        Ds.popLeft();
        Ds.popRight();
        Ds.popRight();
        System.out.println("Popping until empty: " + Ds.toString());
        //Ds.popLeft();  // Uncomment for error messages.



    }
}
