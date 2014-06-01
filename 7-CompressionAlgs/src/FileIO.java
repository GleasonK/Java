// file: FileIO.java
// author: Bob Muller
// date: March 10, 2012
//
// The FileIO interface provides functions that can be used for File I/O in
// a Huffman coding/decoding application.
//
// The program that performs compression (in our case, Huff.java) can use 
// the function openInputFile to open the source file. The openInputFile 
// function will record the file name. If the file name was foo.txt, then
// a subsequent call to openBinaryOutputFile will create a new file foo.zip
// for output.
//
// The program that performs decompression (in our case, Puff.java) can use 
// the function openBinaryInputFile to open the zip file. The openBinaryInputFile 
// function will record the file name. If the file name was foo.zip, then
// a subsequent call to openOutputFile will create a new file foo.txt for 
// output.
//
// NB: The FileReader and FileWriter classes are from the JRE. The BinaryIn and
//     BinaryOut classes are from the SW stdlib.jar.
//
import java.io.*;

public interface FileIO {

    // opeInputFile opens myFile.txt for reading. See the JRE documentation
    // for FileReader.  This function might be called from Huff.java.
    //
    public FileReader openInputFile(String fname);

    // openOutputFile opens a file for output text. This function might be
    // called from Puff.java to create the uncompressed version of the file.
    //
    public FileWriter openOutputFile();

    // openBinaryOutputFile is synched up with openInputFile. If openInputFile
    // opened myFile.txt, openOutputFile will create a new binary output file
    // myFile.zip.  See the documentation of BinaryOut in SW.  This function
    // would be called from Huff.java.
    //
    public BinaryOut openBinaryOutputFile();

    // openBinaryInputFile can open the zip file to uncompress it. This would
    // be called from Puff.java.
    //
    public BinaryIn openBinaryInputFile(String fname);
}