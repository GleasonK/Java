public interface BSTInterface<Key extends Comparable<Key>, Value> { 
    public boolean isEmpty();
    public int size();
    public boolean contains(Key key);
    public Value get(Key key);
    public BST<Key, Value> put(Key key, Value val);
    public BST<Key, Value> deleteMin();
    public BST<Key, Value> deleteMax();
    public BST<Key, Value> delete(Key key);    // Hibbard Deletion
    public Key min();
    public Key max();
    public String toString();
    public Key floor(Key key);
    public int height(); 
}