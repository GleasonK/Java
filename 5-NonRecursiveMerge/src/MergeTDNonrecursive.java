//File: MergeTDNonrecursive.java
//Name: Kevin Gleason
//Date: February 21, 2014
//Use: A Non recursive implementation of the Merge function

/*************************************************************************
 *  Compilation:  javac Merge.java
 *  Execution:    java Merge < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   http://algs4.cs.princeton.edu/22mergesort/tiny.txt
 *                http://algs4.cs.princeton.edu/22mergesort/words3.txt
 *
 *  Sorts a sequence of strings from standard input using mergesort.
 *
 *  % more tiny.txt
 *  S O R T E X A M P L E
 *
 *  % java Merge < tiny.txt
 *  A E E L M O P R S T X                 [ one string per line ]
 *
 *  % more words3.txt
 *  bed bug dad yes zoo ... all bad yet
 *
 *  % java Merge < words3.txt
 *  all bad bed bug dad ... yes yet zoo    [ one string per line ]
 *
 *************************************************************************/

/**
 *  The <tt>Merge</tt> class provides static methods for sorting an
 *  array using mergesort.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

import java.lang.Comparable;
import java.util.*;
public class MergeTDNonrecursive {

    // This class should not be instantiated.
    private MergeTDNonrecursive() { }

    private static Stack<int[]> notSorted = new Stack<int[]>();
    private static Stack<int[]> sorted = new Stack<int[]>();



    // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }

        // postcondition: a[lo .. hi] is sorted
        assert isSorted(a, lo, hi);
    }

    // mergesort a[lo..hi] using auxiliary array aux[lo..hi]

    private static int[] split(int[] front){
        int lo = front[0];
        int hi = front[1];
        int mid = lo + (hi - lo + 1) / 2;
        int[] right = new int[2];
        int[] left = new int[2];
        left[0] = lo;
        left[1] = mid - 1;
        right[0] = mid;
        right[1] = hi; //ranges of the left and right splits
        notSorted.push(right);
        return left;
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */

    //Main sort function
    //Runs split then sorts everything based on level
    public static void sort(Comparable[] a) {
        boolean isSorting = true;
        Comparable[] aux = new Comparable[a.length];
        int[] b;
        int mid;
        int[] prev = new int[2];
        int[] front = new int[2];

        front[0] = 0;
        front[1] = a.length - 1;

        while (isSorting){
            //Split until a is length 1.
            while (front[1] > front[0]){
                front = split(front);
            }
            b = notSorted.pop();
            //Only need to worry if b is of length 2
            if ((b[1] - b[0]) > 0){
                mid = b[0];
                merge(a, aux, b[0], mid, b[1]);
            }
            mid = front[1];
            merge(a, aux, front[0], mid, b[1]);
            front[1] = b[1]; //sets new front right value to be right value of b

            while(!sorted.isEmpty()){ //to determine if front can be merged with whats in the sorted stack
                prev = sorted.peek();
                int pLength = prev[1] - prev[0]; //level of item on sorted
                int fLength = front[1] - front[0]; //level of front item
                if (pLength == fLength || pLength == (fLength - 1)){ //compares levels
                    prev = sorted.pop();
                    merge(a, aux, prev[0], prev[1], front[1]); //merges same level
                    front[0] = prev[0]; //sets new front left value to be left value of previous
                }
                else break; //exits loop if shouldn't be merged yet
            }
            sorted.push(front);
            // Work on new layer if sorted
            if(!notSorted.isEmpty()){
                front = notSorted.pop();
            }
            //Exit loop when all is sorted.
            else{
                isSorting = false;
            }
        }

        //////////////////////////////
        //String Builder for debugging
        //////////////////////////////

//        StringBuilder sb = new StringBuilder("Sorted: ");
//        for (int i=0; i < a.length; i++) {
//            sb.append(a[i] + " ");
//        }
//        System.out.println(sb.toString());
    }


    /***********************************************************************
     *  Helper sorting functions
     ***********************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }


    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }


    public static void main(String[] args) {
        //Timer for Merge Nonrecursive
        Stopwatch sw = new Stopwatch();
        In myfile = new In(args[0]);
        String[] a = myfile.readAllStrings();
        MergeTDNonrecursive.sort(a);
        double seconds = sw.elapsedTime();     // how many seconds since start
        System.out.println("Merge Nonrecursive takes this many seconds: " + seconds);

//        //Timer for MergeBU
//        Stopwatch sw2 = new Stopwatch();
//        In myfile2 = new In(args[0]);
//        String[] b = myfile2.readAllStrings();
//        MergeBU.sort(b);
//        double seconds2 = sw2.elapsedTime();     // how many seconds since start
//        System.out.println("MergeBU Takes this many seconds: " +seconds2);

//        //Timer for Merge
//        Stopwatch sw3 = new Stopwatch();
//        In myfile3 = new In(args[0]);
//        String[] c = myfile3.readAllStrings();
//        Merge.sort(c);
//        double seconds3 = sw3.elapsedTime();
//        System.out.println("Marge Recursive takes this many seconds: " + seconds3);

    }
}