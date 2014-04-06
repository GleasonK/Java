//File: Puff.java
//Author: Kevin Gleason (with Andrew Francl)
//Date: 4/5/14
//Use: File decompression.

import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;


public class Puff {
    //Instance Variables
    private static final int MagicNum = 0x0BC0;
    private FileIO io = new FileIOC();
    private BinaryIn input;

    private SymTable<Integer, TableValue> st = new SymTableC<Integer, TableValue>();

    private Puff(String input){
        this.input = io.openBinaryInputFile(input);
    }

    //Need to:
    //  Open the File
    //  Check magic number (2 byte)
    //  Read 4byte size of table
    //  Reconstruct SymTable > HuffTree
    //  Run through tree using binary to write letters

    private void puffAway() throws IOException, DataFormatException{
        //BinaryIn in = this.openBinFile();
        //assert checkMagic(in);
        checkMagic(input);
        int tableSize = input.readInt();
        System.out.println("Table Size: " + tableSize);
        makePuffTable(tableSize);
        HuffTree tree = makeHTree();
        //System.out.println(tree.toString());   //DEBUG
        tree.writeText(this.io, this.input);


    }

    private boolean checkMagic(BinaryIn inputFile) throws DataFormatException {
        int mNum = inputFile.readShort(); // 8bit = 1byte
        int a = 0x0BC0;
        //System.out.println("a: " + a + " mNum: "+ mNum);  //DEBUG
        if (mNum == this.MagicNum) return true;
        else throw new DataFormatException("Magic Number did not match! Data Corrupt.");
    }

    private void makePuffTable(int tableSize){
        for (int i = 0; i < tableSize; i++) {
            //System.out.println("Char: " + this.input.readByte() + " Freq " + this.input.readInt());
            st.put((int) this.input.readByte(), new TableValueC(this.input.readInt()));

        }
        st.toStringFreq();
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


    public static void main(String[] args) throws IOException, DataFormatException{
        //new Puff("what.zip").puffAway(); //for use in IntelliJ
        new Puff(args[0]).puffAway();
    }
}
