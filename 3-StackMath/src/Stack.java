// Name: Kevin Gleason
// File: ResizeArrayStack.java
// Use: Interface for the the Stack type for PS3
// Date: Sunday Feb. 2

public interface Stack<T> {
    public void push(T element);
    public T pop();
    public T peek();
    public boolean isEmpty();
    public int size();
}