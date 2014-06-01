package board;

import util.*;
import players.*;
import java.util.*;

public class SquareC implements Square {

    private final Option<Player> noOne = new None<Player>();
    
    private Option<Player> owner;
    private boolean north;
    private boolean west;
    private boolean south;
    private boolean east;
    
    private int row, col;
    
    public SquareC(int row, int col, Option<Player> owner, 
                   boolean north, boolean west, boolean south, boolean east) {
        this.row = row;
        this.col = col;
        this.owner = owner;
        this.north = north;
        this.west  = west;
        this.south = south;
        this.east  = east;
    }
    
    public SquareC(int row, int col) {
        this(row, col, new None<Player>(), false, false, false, false);
    }
    
    // Getters
    //
    public int getRow() { return this.row; }
    public int getCol() { return this.col; }

    public Option<Player> getOwner() { return this.owner; }
    public boolean get(Side side) {
        switch (side) {
          case NORTH : return this.north; 
          case WEST  : return this.west;
          case SOUTH : return this.south;
          default    : return this.east;
        }
    }
    
    // Setters
    //
    public void setOwner(Option<Player> po) {
        this.owner = po;
    }
    
    public void setSide(Side side, boolean value) {
        switch (side) {
        case NORTH : this.north = value; break;
        case WEST  : this.west  = value; break;
        case SOUTH : this.south = value; break;
        default    : this.east  = value;
        }
    }
    
    public boolean sideIsMarked(Side side) { return get(side); }
    
    public boolean hasFourSides() {
        return (sideIsMarked(Side.NORTH) && sideIsMarked(Side.WEST) &&
                sideIsMarked(Side.SOUTH) && sideIsMarked(Side.EAST));
    }
    
    public void mark(Side side) {
        // The logic preventing double marking of a side is handled
        // in the mark function of the Board type.
        //
        this.setSide(side, true);
    }
       
    public boolean isOwned() { return !(this.getOwner().equals(this.noOne)); }
    
    public Set<Line> getOpenLines() {
        int row = this.getRow(),
            col = this.getCol();
        Set<Line> lineSet = new HashSet<Line>();
        
        if(!sideIsMarked(Side.NORTH)) lineSet.add(new LineC(row, col, Side.NORTH));
        if(!sideIsMarked(Side.WEST))  lineSet.add(new LineC(row, col, Side.WEST));
        if(!sideIsMarked(Side.SOUTH)) lineSet.add(new LineC(row, col, Side.SOUTH));
        if(!sideIsMarked(Side.EAST))  lineSet.add(new LineC(row, col, Side.EAST));
        
        return lineSet;
    }
    public String toString() {
        String
            a = "{row =" + this.row + ", col =" + this.col,
            b = ", north=" + this.north + ", west=" + this.west,
            c = ", south=" + this.south + ", east=" + this.east,
            d = ", owner=" + this.owner.toString() + "}";
        return a + b + c + d;
    }

    public boolean hasNMarkedSides(int n) {
        int count = 0;
        if(this.sideIsMarked(Side.NORTH)) count++;
        if(this.sideIsMarked(Side.WEST)) count++;
        if(this.sideIsMarked(Side.SOUTH)) count++;
        if(this.sideIsMarked(Side.EAST)) count++;
        return n == count;
    }

    public Square copy() {
        return new SquareC(this.row, this.col, this.owner, 
                           this.north, this.west, this.south, this.east);
    }
        
}