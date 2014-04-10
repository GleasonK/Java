//File: SymTableC.java
//Author: Kevin Gleason
//Date: 3/30/14
//Use: Implements SymTable interface

import java.util.*;

public class SymTableC<Key, Value> implements SymTable<Key, Value> {

    private Map<Key, Value> hMap = new HashMap<Key, Value>();

    //Constructor
    public SymTableC() {}

    public Value get(Key k){ return hMap.get(k); }
    public void put(Key k, Value v){ hMap.put(k, v); }
    public boolean containsKey(Key k){ return hMap.containsKey(k); }
    public int size() { return hMap.size(); }
    public Set<Key> getKeys() { return hMap.keySet(); }
    public Collection<Value> getVals() { return hMap.values(); }
    public String toString() { return hMap.toString(); }


    //Made additional toString for after all codes have been placed to read all ch:freq:code
    public void toStringCodes(){
        Set<Key> STKeys = this.getKeys();
        System.out.println("Wrote Symbol Table Codes:");
        System.out.println("Char:Frequency:Code ");
        for (Key i : STKeys) {
            //Need to print everything
            TableValue tv = (TableValue) this.get(i);
            Integer ii = (Integer) i;
            System.out.println((char)((int) ii) + ":" + (int) tv.getFrequency() + ":" + tv.getBits().getBit() + " ");
        }
        System.out.println();
    }
    //For the frequencies
    public void toStringFreq(){
        Set<Key> STKeys = this.getKeys();
        System.out.println("Wrote Symbol Table Codes as ");
        System.out.print("Char:Frequency ");
        for (Key i : STKeys) {
            //Need to print everything
            TableValue tv = (TableValue) this.get(i);
            Integer ii = (Integer) i;
            System.out.println((char)((int) ii) + ":" + (int) tv.getFrequency());
        }
        //System.out.println(); //optional space
    }
}
