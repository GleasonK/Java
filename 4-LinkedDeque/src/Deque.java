// File: Deque
// Name: Kevin Gleason
// Date: Feb 2, 2014
// Use: The interface for Deque

public interface Deque<T> {
    public void pushLeft(T item);
    public void pushRight(T item);
    public T popLeft();
    public T popRight();
    public int size();
    public boolean isEmpty();
    public String toString();
}