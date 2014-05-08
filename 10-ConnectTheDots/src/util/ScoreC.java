package util;

import players.*;
import players.player1.*;
import players.player2.*;

public class ScoreC implements Score {

    private int player1;
    private int player2;

    public ScoreC(int player1, int player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public int getPlayer1() { return this.player1; }
    public int getPlayer2() { return this.player2; }

    public void setPlayer1(int n) { this.player1 = n; }
    public void setPlayer2(int n) { this.player2 = n; }
    
    public void add(Player p, int score) {
        if(p.getId() == 1)
            this.setPlayer1(this.getPlayer1() + score);
        else
            this.setPlayer2(this.getPlayer2() + score);
    }
    
    public String toString() {
        int
            p1s = this.player1,
            p2s = this.player2;
       
        return "{player1=" + p1s + ", player2=" + p2s + "}";
    }
    public void add(Score other) {
        this.setPlayer1(this.getPlayer1() + other.getPlayer1());
        this.setPlayer2(this.getPlayer2() + other.getPlayer2());
    }
}