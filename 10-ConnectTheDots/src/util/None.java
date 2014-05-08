package util;

import java.util.*;

public class None<T> implements Option<T> {
    public boolean hasValue() { return false; }
    public T valueOf() { 
        throw new NoSuchElementException("None has no value.");
    }
    public String toString() { return "None"; }
}