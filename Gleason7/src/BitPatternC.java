//File: BitPatternC.java
//Author: Kevin Gleason
//Date: 3/29/14
//Use: implementation of BitPattern interface

public class BitPatternC implements BitPattern {
    
    private int bits;
    private int size;
    
    public BitPatternC(int bits, int size) {
        this.bits = bits;
        this.size = size;
    }
    
    public int getLength(){
        return size; 
    }

    public int getBit(){ return this.bits; }
}