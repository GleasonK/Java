//File: SymTable.java
//Author: Kevin Gleason
//Date: 3/30/14
//Use: The SymTable interface

import java.util.*;

public interface SymTable<Key, Value> {
    public int size();
    public Value get(Key k);
    public Set<Key> getKeys();
    public void put(Key k, Value v);
    public boolean containsKey(Key k);
    public void toStringCodes();
    public void toStringFreq();
}
