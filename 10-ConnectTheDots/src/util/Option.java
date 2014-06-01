package util;

public interface Option<T> {
    public boolean hasValue();
    public T valueOf();
    public String toString();
}



