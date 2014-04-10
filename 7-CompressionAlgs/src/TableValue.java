//File: TableValue.java
//Author: Kevin Gleason
//Date: 3/25/14
//Use: Interface for handling SymTable values

public interface TableValue {
    public BitPattern getBits();
    public int getFrequency();
    public void setBits(BitPattern bit);
    public void setFrequency(int freq);
}
