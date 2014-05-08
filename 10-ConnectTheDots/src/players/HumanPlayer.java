package players;

import util.*;
import board.*;

public class HumanPlayer implements Player {

    private String name;
    private java.awt.Color color;
    private int id;

    public HumanPlayer() {
        this.id = 0;
        this.name = "Human";
        this.color = java.awt.Color.orange;
    }
    
    // This is a dummy routine that won't be called.
    //
    public Line makePlay(Board board, Player opponent, Line oppPlay, Clock clock) {
        return new LineC(0, 0, Side.NORTH);
    }
    public String teamName() { return this.name; }
    public java.awt.Color getColor() { return this.color; }
    public int getId() { return this.id; }
    public String toString() { return "Human"; }
    public Line makeRandomPlay(Board b){
        return new LineC(3,3,Side.SOUTH);
    }
}