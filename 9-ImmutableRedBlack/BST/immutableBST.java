/********************************************************************************************
 * File: immutableBST.java
 * Author: Jason Morse
 * Date: November 11, 2013
 * Class: Computer Science II - Problem Set 7
**********************************************************************************************/

import java.util.NoSuchElementException;

public class immutableBST<Key extends Comparable<Key>, Value> implements BST<Key, Value> {
  
        // Instance variables
        private Key key;
        private Value val;
        private BST<Key, Value> left, right;
        private int N;

        public immutableBST(Key key, Value val, BST<Key, Value> left, BST<Key, Value> right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
            this.N = left.size() + right.size() + 1;
        }
    
        // Constructor
        public immutableBST(Key key, Value val) {
            this.key = key;
            this.val = val;
            left = new emptyImmutableBST<Key, Value>();
            right = new emptyImmutableBST<Key, Value>();
        }
        
    // Determines if the BST is empty.
    public boolean isEmpty() {
        return false;
    }
    
    // Determines the size of the BST.
    public int size() {
        return this.N;
    }

    // Determines if the BST contains a certain key.
    public boolean contains(Key key) {
        return get(key) != null;
    }

    // Returns the value of the given key if it exists in the BST.
    public Value get(Key key) {
        if (isEmpty()) return null;
        int cmp = key.compareTo(this.key);
        if      (cmp < 0) return this.left.get(key);
        else if (cmp > 0) return this.right.get(key);
        else              return this.val;
    }

    // Puts a (key, value) pair into the BST.
    public BST<Key, Value> put(Key key, Value val) {
        int cmp = key.compareTo(this.key);
        if (cmp == 0)
            return new immutableBST(key, val, this.left, this.right);
        else if (cmp < 0)
            return new immutableBST(this.key, this.val, this.left.put(key, val), this.right);
        else
            return new immutableBST(this.key, this.val, this.left, this.right.put(key, val));
    }     

    // Deletes the minimum element from the BST. 
    public BST<Key, Value> deleteMin() {
        if (this.left.isEmpty()) {
            return this.right;
        } else {
            return new immutableBST(this.key, this.val, this.left.deleteMin(), this.right);
        }
    }
        
    // Deletes the maximum element from the BST. 
    public BST<Key, Value> deleteMax() {
        if (this.right.isEmpty()) {
            return this.left;
        } else {
            return new immutableBST(this.key, this.val, this.left, this.right.deleteMax());
        }
    }

    // Deletes a given key and its value from the BST.  
    public BST<Key, Value> delete(Key key) {
//        if (key == null) throw new NoSuchElementException("Cannot delete empty key.");
//        if (!contains(key)) throw new NoSuchElementException("Cannot delete nonexistent key.");
        
        int cmp = key.compareTo(this.key);
        if (cmp < 0) {
            BST<Key, Value> newLeft = this.left.delete(key);
            return new immutableBST(this.key, this.val, newLeft, this.right);
        }
        if (cmp > 0) {
            BST<Key, Value> newRight = this.right.delete(key);
            return new immutableBST(this.key, this.val, this.left, newRight);
        }
        else {
            Key rightMin = this.right.min();
            Value rightMinVal = get(rightMin);
            if (this.right.isEmpty()) return this.left;
            if (this.left.isEmpty()) return this.right;
            return new immutableBST(rightMin, rightMinVal, this.left, this.right.deleteMin());
        }
    }

    // Returns the minimum element of the BST.
    public Key min() {
        if (this.left.isEmpty()) return this.key;
        else return this.left.min();
    }

    // Returns the maximum element of the BST.
    public Key max() {
        if (this.right.isEmpty()) return this.key;
        else return this.right.max();
    }
    
    // Converts the BST content to a string.
    public String toString() {
        String
            leftChildString = left.toString(),
            keyString = key.toString(),
            valueString = val.toString(),
            rightChildString = right.toString();
        return "(" +  leftChildString + ", " + keyString + ", " + valueString + ", " + rightChildString + ")";
    }

    // Returns an equal or next largest element relative to the given key.
    public Key floor(Key key) {
        if (this.key == null) { return null; }
        int cmp = key.compareTo(this.key);
        if (cmp == 0) { return key; }
        if (cmp <  0) { return this.left.floor(key); }
        if (cmp >  0) { return this.right.floor(key); }
        Key t = this.right.floor(key); 
        if (t != null) return t;
        else return this.key; 
    } 

    // Determines the height of the BST.
    public int height() { 
        return 1 + Math.max(left.height(), right.height());
    }
    
    // Helper function to add elements to the BST using an array.
    private static BST<String, String> insert(String[] elements, int i, BST<String, String> bst) {
        if (i == elements.length)
            return bst;
        else
            return insert(elements, i + 1, bst.put(elements[i], elements[i]));
    }

    // Tester function (main method).
    public static void main(String[] args) { 

      String[] elements = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
      
      System.out.println("Adding elements to BST...");
      BST<String, String> bst = insert(elements, 0, new emptyImmutableBST());
      System.out.println("BST = " + bst.toString() + " \n");
      
      System.out.println("Testing 'isEmpty' method (should return false)...");
      System.out.println(bst.isEmpty() + " \n");
      
      System.out.println("Testing 'size' method (should return 10)...");
      System.out.println("Size = " + bst.size() + " \n");
      
      System.out.println("Testing 'contains' method for 'E' (should return true)...");
      System.out.println(bst.contains("E") + " \n");
      
      System.out.println("Testing 'contains' method for 'X' (should return false)...");
      System.out.println(bst.contains("X") + " \n");
      
      System.out.println("Testing 'get' method for 'B' (should return 'B')...");
      System.out.println(bst.get("B") + " \n");
      
      System.out.println("Putting 'K' in BST...");
      BST<String, String> bst1 = bst.put("K", "K");
      System.out.println("Updated BST = " + bst1.toString() + " \n");
      
      System.out.println("Testing 'delete min' method (should remove 'A')...");
      BST<String, String> bst2 = bst1.deleteMin();
      System.out.println("Updated BST = " + bst2.toString() + " \n");
      
      System.out.println("Testing 'delete max' method (should remove 'K')...");
      BST<String, String> bst3 = bst2.deleteMax();
      System.out.println("Updated BST = " + bst3.toString() + " \n");
      
      System.out.println("Deleting 'F' from BST...");
      BST<String, String> bst4 = bst3.delete("F");
      System.out.println("Updated BST = " + bst4.toString() + " \n");
      
      System.out.println("Testing 'min' method (should be 'B')...");
      System.out.println("Min = " + bst4.min() + " \n");
      
      System.out.println("Testing 'max' method (should be 'J')...");
      System.out.println("Max = " + bst4.max() + " \n");
      
      System.out.println("Testing 'floor' method on 'J' (should be 'J')...");
      System.out.println("Floor = " + bst4.floor("J") + " \n");
      
      System.out.println("Testing 'height' method (should return 8)...");
      System.out.println("Height = " + bst4.height() + " \n");
      
      System.out.println("Testing complete.");
        
    }
}
    
    