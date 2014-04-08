//File: TouchPadHTML.java
//Author: Kevin Gleason
//Date: Monday, April 7, 2014
//Use: Tags Deque interface

public interface TPTags<T> {
    public String rip();
    public void push(T s);
    public boolean isEmpty();
    public int size();
    public int getIndent();
    public String getBuffer();
    public String toString();
    //public void checkIndent();
}
