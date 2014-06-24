//File: HuffTree.java
//Author: Kevin Gleason
//Date: 3/29/14
//Use: The HuffTree interface

import java.io.FileWriter;
import java.io.IOException;

public interface HuffTree{ // extends Comparable {

    public int getWeight();

    public char getCh();
    public char checkLeaf(BinaryIn in);
    public HuffTree getLeft();
    public HuffTree getRight();
    public void writeText(FileIO io, BinaryIn in) throws IOException;
    public void makeBitPattern(int s, int r, SymTable<Integer, TableValue> st);
    public boolean isLeaf();
}
