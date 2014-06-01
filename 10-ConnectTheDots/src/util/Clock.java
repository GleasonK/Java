package util;

import players.*;
import java.util.*;

public class Clock {
    
    private long firstStartTime;
    private long restartTime;
    private long limit;
    private double secondsLimit;
    
    private long elapsedTime;
    private long lastMark;
        
    private boolean stopped;
    private Player player;
    
    public Clock(Player player, long milliSeconds) 
    { 
        this.player = player;
        this.stopped = false;
        this.secondsLimit = (double) (milliSeconds / 1000.0);
        elapsedTime = (long) 0;
        this.limit  = milliSeconds;
        this.firstStartTime = 0;
    }
    
    public Player getPlayer() { return this.player; }
    
    public void start()   {
        firstStartTime = System.currentTimeMillis();
        restartTime = firstStartTime;
        stopped = false;
    }
    public void restart() { 
        restartTime = System.currentTimeMillis();
        if(firstStartTime == 0)
            firstStartTime = restartTime;
        stopped = false;
    }
    
    public void stop()  {
        if(!stopped) {
            elapsedTime = elapsedTime + (System.currentTimeMillis() - restartTime);
 //           System.out.println("stop: elapsedTime= " + elapsedTime + ", timeRemaining=" + timeRemaining());
            stopped = true;
        }
    }
      
    public long elapsedTime() { return elapsedTime; }
    
    public long timeRemaining() {
        return limit - elapsedTime(); // (elapsedTime() + (System.currentTimeMillis() - startTime));
    }
 
    public String toString() {
        double et = timeRemaining() / 1000.0;
        return String.format("%4.2f", et); // new Double(et).toString();
    }
    
    public static void main(String[] args) {
        
        Player p = new HumanPlayer();
        
        Clock c = new Clock(p, 2000);
        c.start();
        int j = 0;
        for(int i = 0; i < 100000000; i++) {
            Properties pp = System.getProperties();
        }
        c.stop();
        String pre = "player " + p.toString() + " used ";
        System.out.println(pre + c.elapsedTime() + " milliseconds.");
    }  
}   