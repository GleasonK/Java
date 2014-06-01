package util;

public class Some<T> implements Option<T> {    
    private T value;
    public Some(T value) { this.value = value; }
    
    public boolean hasValue() { return true; }
    public T valueOf() { return this.value; }

    public String toString() {
        return valueOf().toString();
    }
    
    public static void main(String[] args) {
     
        Option<String> os = new Some<String>("Hello World!");
        if (os.hasValue()) {
            String s = os.valueOf();
            System.out.println(s);
        }
    }
}