//File: TableValueC.java
//Author: Kevin Gleason
//Date: 3/25/14
//Use: Implementation for the TableValue interface

public class TableValueC implements TableValue{
    //Instance Variables
    private int frequency;
    private BitPattern bits;

    public TableValueC (int freq) {
        this.frequency = freq;
        this.bits = null;
    }

    public BitPattern getBits(){ return this.bits; }
    public int getFrequency() { return this.frequency; }
    public void setBits(BitPattern bit) { this.bits = bit; }
    public void setFrequency(int freq) { this.frequency = freq; }


}
