//File: Huff.java
//Author: Kevin Gleason
//Date: 3/30/14
//Use: The Huff program main class for compressing files

import java.io.*;
import java.util.*;

public class Huff {
    //Instance Variables
    public static boolean DEBUG = false;
    private final String input;

    //Magic Number
    private static int MagicNum = 0x0BC0;

    private FileIO io = new FileIOC();


    private SymTable<Integer, TableValue> st = new SymTableC<Integer, TableValue>();
    private PriorityQueue<HuffTree> HuffPQ;

    public Huff(String input) {
        this.input = input;
    }

    //Executes all the code
    private void huffAway() throws IOException {
        this.makeTable();
        HuffTree tree = this.makeHTree();
        //System.out.print("Code: ");                     //DEBUGs
        makeBinary(tree); //Void function, updates the symbol table
        st.toStringCodes();                             //DEBUG
        //System.out.println("Tree: " + tree.toString()); //DEBUG
    }

    private void makeTable() throws IOException{
        FileReader inputFile = io.openInputFile(this.input);
        //System.out.println("FILE IN: "); //DEBUG

        int temp = 0; //Set temp so it can get in the while loop
        int freq;
        while (temp!= -1){
            temp = inputFile.read();  // reads the content
            if (temp!= -1){
                if (st.containsKey(temp)){
                    freq = st.get(temp).getFrequency();
                    st.get(temp).setFrequency(freq+1);
                    //System.out.println("ST.put: " + temp + " Freq: " + freq); //DEBUG
                }
                else{
                    st.put(temp, new TableValueC(1));
                }
            }
        }
        inputFile.close();

        //System.out.println();                //DEBUG
        //System.out.println(st.toString());   //DEBUG
    }

    private HuffTree makeHTree() {
        Set<Integer> STKeys = st.getKeys();
        PriorityQueue<HuffTree> tempQueue = new PriorityQueue<HuffTree>();
        for (Integer i : STKeys) {
            //Need push everything in to a PQ
            int freq = st.get(i).getFrequency();
            char c = (char) i.intValue();
            tempQueue.add(new HuffTreeC(c, freq, null, null));
        }
        while (tempQueue.size() > 1) {
            HuffTree p1 = tempQueue.poll();
            HuffTree p2 = tempQueue.poll();

            int tempWeight = p1.getWeight() + p2.getWeight();
            //Huff tree carries weight int and left/right/char
            tempQueue.add(new HuffTreeC((char) 0, tempWeight, p1, p2));
        }
        return tempQueue.poll();

    }

    private void makeBinary(HuffTree tree) throws IOException {
        tree.makeBitPattern(0, 0, this.st); //Make the BitPattern on the SymTable
        FileReader inputFile = io.openInputFile(this.input);  //Open the input file again
        BinaryOut outFile = io.openBinaryOutputFile();
        outFile.write(this.MagicNum,16);
        Set<Integer> STKeys = this.st.getKeys();
        outFile.write(STKeys.size());
        for (Integer i : STKeys) {
            TableValue tv = (TableValue) this.st.get(i);
            char c = (char)((int) i);
            int freqW = tv.getFrequency();
            outFile.write(c);
            outFile.write(freqW);
            //System.out.print("Char: " + c + "Freq: " + freqW);  //DEBUG
        }

        inputFile.close();


        FileReader fin = io.openInputFile(this.input);
        //System.out.println("FILE IN: "); //DEBUG

        int temp = 0; //Set temp so it can get in the while loop
        int freq;
        while (temp!= -1){
            temp = fin.read();  // reads the content
            if (temp!= -1){
                TableValue bits = st.get(temp);
                BitPattern bp = bits.getBits();
                outFile.write(bp.getBit(), bp.getLength());
            }
        }
        outFile.close();
        fin.close();

    }

    public static void main(String[] args) throws IOException {
        //Execute compression from Terminal with an argument
        //new Huff(args[0]).huffAway();
        new Huff("src/what1.txt").huffAway();
    }
}