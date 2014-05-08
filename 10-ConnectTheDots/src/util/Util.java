package util;
import java.awt.Color;

public class Util {
    public static final double DEFAULT_SECONDS = 20.0; 
    
    public static int N = 4;
    public static int ALL = N * N;
    public static int SLIDER_MAX = 100;
    public static final int DEFAULT_DISPLAY_HEIGHT = 800;
    public static final long DEFAULT_TIMEOUT = ((long) DEFAULT_SECONDS * 1000);    // 3 thousand milliseconds
    public static final double ASPECT_RATIO = 1.618;
    public static final Color NOCOLOR = new Color(245, 245, 245);
    public static final int DEFAULT_GAMES = 5;
    
    public static final int DEFAULT_PACE = 10;  // milliseconds?
    
    public static final Color PLAYER1_COLOR = new Color(255,98,67); //makeRandomColor();
    public static final Color PLAYER2_COLOR = new Color(22,146,254); //makeRandomColor();
    public static final Score PLAYER1_WINS = new ScoreC(1, 0);
    public static final Score PLAYER2_WINS = new ScoreC(0, 1);
    public static final Score TIE = new ScoreC(0, 0);
   
    
    public static Color makeRandomColor() {
        float 
            red   = (float) Math.random(),
            green = (float) Math.random(),
            blue  = (float) Math.random();
        return new Color(red, green, blue); 
    }
    public static void wait(int pace) {
        try {
            Thread.sleep((int) Math.pow(pace, 2)); // currently squaring the pace.       
        }
        catch(InterruptedException e) {}   
    }
    public static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (Exception ignore) {}
    }
    
    public static String milliToSeconds(long milliseconds) {
        return String.format("%4.1f", ((double) milliseconds));
    }
}