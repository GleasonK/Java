package util;

import java.util.*;
import board.*;

public class LineC implements Line {
    
    private int row, col;
    private Side side;
    
    public LineC(int row, int col, Side side) {
        this.row = row;
        this.col = col;
        this.side = side;
    }
 
    public Side getSide() { return this.side; }
    public int getRow()   { return this.row; }
    public int getCol()   { return this.col; }
    
    // This is a bug fix.
    //
    @Override
    public int hashCode() { 
        int row = this.getRow(),
            col = this.getCol();
        switch (this.getSide()) {
            case NORTH : return row - 1;  // goes to -1 for row == 0
            case SOUTH : return row;
            case WEST  : return col - 1;
            default    : return col;      // EAST
        }
    }
    
    private int toInt(Side side) {
        switch(side) {
            case NORTH : return 1;
            case SOUTH : return 2;
            case WEST  : return 3;
            default    : return 4;  // EAST
        }
    }
    
    // The equals function equates lines correctly. E.g., [0][0].EAST
    // is equated with [0][1].WEST, etc. this way we can make sets of
    // lines without repeats.
    //
    @Override
    public boolean equals(Object o) {
        Line other = (Line) o;
        int tr = this.getRow(),
            tc = this.getCol(),
            or = other.getRow(),
            oc = other.getCol();
        Side ts = this.getSide(),
            os = other.getSide();
        boolean
            a = tr == or && tc == oc && ts == os,
            b = tc == oc && ts == Side.SOUTH && os == Side.NORTH && tr == (or - 1),
            c = tc == oc && ts == Side.NORTH && os == Side.SOUTH && (tr - 1) == or,
            d = tr == or && ts == Side.EAST && os == Side.WEST && tc == (oc - 1),
            e = tr == or && ts == Side.WEST && os == Side.EAST && (tc - 1) == oc;
             
        return a || b || c || d || e;
    }           
    
    public boolean isLegal(Board board) { return true; }
    
    public boolean isOpen(Board board)  { 
        Square sq = board.getSquare(this.getRow(), this.getCol()); 
        return sq.sideIsMarked(this.getSide());
    }
    
    public Set<Square> getSquares(Board board) {
        Set<Square> answer = new HashSet<Square>();
     
        answer.add(board.getSquare(this.row, this.col));
        
        if(this.side == Side.NORTH && this.row > 0)
            answer.add(board.getSquare(row - 1, col));
        if(this.side == Side.WEST && this.col > 0)
            answer.add(board.getSquare(row, col - 1));
        if(this.side == Side.SOUTH && this.row < Util.N - 1)
            answer.add(board.getSquare(row + 1, col));
        if(this.side == Side.EAST && this.col < Util.N - 1)
            answer.add(board.getSquare(row, col + 1));   
        return answer;
    }

    public String toString() { 
        String 
            rs = "row=" + this.getRow(),
            cs = "col=" + this.getCol(),
            ss = "side=" + this.getSide();
        return "Line{" + rs + ", " + cs + ", " + ss + "}";
    }
    public static void main(String[] args) {
        java.util.Set<LineC> sl = new java.util.HashSet<LineC>();
        LineC 
            l1 = new LineC(2, 2, Side.EAST),
            l2 = new LineC(2, 3, Side.WEST),
            l3 = new LineC(2, 2, Side.EAST),
            l4 = new LineC(2, 2, Side.SOUTH),
            l5 = new LineC(3, 2, Side.NORTH);

        sl.add(l1); sl.add(l2); sl.add(l3); sl.add(l4); sl.add(l5);
        
        for(LineC l : sl)
            System.out.println("hashCode for " + l + " is " + l.hashCode());
        System.out.println(sl.toString());
    }
        
}