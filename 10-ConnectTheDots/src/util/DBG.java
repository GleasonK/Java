package util;

public class DBG {

    public static final boolean UI = false;
    public static final boolean CONTROL = false;
    public static final boolean BOARD = false;
    public static final boolean PLAYERS = false;

    public boolean debug;
    private String file;

    public DBG(boolean debugOrNot, String file) {
        this.debug = debugOrNot;
        this.file = file;
    }
    public DBG(boolean debugOrNot) { this(debugOrNot, ""); }
    public DBG() { this(false, ""); }
    
    public void println(String msg) {
        System.out.println(this.file + ": " + msg);
    }
}