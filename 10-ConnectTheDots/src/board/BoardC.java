package board;

import util.*;
import players.*;
import players.player1.*;
import players.player2.*;
import controller.*;

import java.util.*;

public class BoardC implements Board {
 
    private DBG dbg;
    private Controller controller;
    private Square[][] board;

    public BoardC(Controller controller, Square[][] squares) {
        this.controller = controller;
        this.dbg = new DBG(DBG.BOARD, "BoardC");
        this.board = new Square[Util.N][Util.N];
        
        for(int row = 0; row < Util.N; row++)
            for(int col = 0; col < Util.N; col++)
                this.board[row][col] = squares[row][col];
    }
    
    public BoardC(Controller controller) {
        this.controller = controller;
         
        this.dbg = new DBG(DBG.BOARD, "BoardC");      
        this.board = new Square[Util.N][Util.N];
        
        for(int row = 0; row < Util.N; row++)
            for(int col = 0; col < Util.N; col++)
                this.board[row][col] = new SquareC(row, col);
    }

    public Score getScore() {
        Score score = new ScoreC(0, 0);
        for(int row = 0; row < Util.N; row++)
            for(int col = 0; col < Util.N; col++) {
                Square sq = board[row][col];
                Option<Player> op = sq.getOwner();
                if(op.hasValue())
                    score.add(op.valueOf(), 1);
            }
        return score;
    }

    private boolean inRange(int i) { return (0 <= i) && (i < Util.N); }
    
    public Square getSquare(int row, int col) { 
        if (inRange(row) && inRange(col))
            return this.board[row][col];
        else {
            String msg = "bad index row=" + row + ", col=" + col + ", you lose.";          
            throw new RuntimeException("getSquare: " + msg);
        }
    }
    
    public Set<Square> markLine(Player player, Line line) {
        int row = line.getRow(),
            col = line.getCol();
        Side side = line.getSide();
        
        Square 
            square = this.getSquare(row, col),
            above, below, right, left;
        if(dbg.debug)
            dbg.println("markLine: working on square=" + square.toString());

        Set<Square> claimedSquares = new HashSet<Square>();
        boolean completedASquare;
        
        if (square.sideIsMarked(side)) {
            String msg = "board[" + row + "][" + col + "]." + side + " already marked, you lose.";
            throw new RuntimeException("markLine: " + msg);
        }
        else {
            square.mark(side);
            Option<Player> thisPlayer = new Some<Player>(player);
            completedASquare = square.hasFourSides();
           
            if (completedASquare) {
                square.setOwner(thisPlayer);
                claimedSquares.add(square);
                if(dbg.debug)
                    dbg.println("claiming center" + square);
            }
            if (side == Side.NORTH && row > 0) {
                above = this.getSquare(row - 1, col);
                above.setSide(Side.SOUTH, true);
                if(above.hasFourSides()) {
                    above.setOwner(thisPlayer);
                    claimedSquares.add(above);
                    if(dbg.debug)
                        dbg.println("claiming northerly" + above);
                }
            }
            else if (side == Side.SOUTH && row < (Util.N - 1)) {
                below = this.getSquare(row + 1, col);
                below.setSide(Side.NORTH, true);
                if(below.hasFourSides()) {
                    below.setOwner(thisPlayer);
                    claimedSquares.add(below);
                    if(dbg.debug)
                        dbg.println("claiming southerly" + below);
                }
            }
            else if (side == Side.EAST && col < (Util.N - 1)) {
                right = this.getSquare(row, col + 1);
                right.setSide(Side.WEST, true);
                if(right.hasFourSides()) {
                    right.setOwner(thisPlayer);
                    claimedSquares.add(right);
                    if(dbg.debug)
                        dbg.println("claiming easterly" + right);
                }
            }
            else if (side == Side.WEST && col > 0) {
                left = this.getSquare(row, col - 1);
                left.setSide(Side.EAST, true);
                if(left.hasFourSides()) {
                    left.setOwner(thisPlayer);
                    claimedSquares.add(left);
                    if(dbg.debug)
                        dbg.println("claiming westerly" + left);
                }
            }
            return claimedSquares;
        }
    }
     
    public void removeLine(Line line) {
        int row = line.getRow(),
            col = line.getCol();

        Side side = line.getSide();
        Square 
            square = this.getSquare(row, col),
            above, below, left, right;
        
        if (!square.sideIsMarked(side)) {
            String msg = "board[" + row + "][" + col + "]." + side + " isn't marked.";
            throw new NoSuchElementException("removeLine: " + msg);
        }
        else {
            square.setSide(side, false);
            Option<Player> noOne = new None<Player>();
            square.setOwner(noOne);
            
            if (side == Side.NORTH && row > 0) {
                above = this.getSquare(row - 1, col);
                above.setSide(Side.SOUTH, false);
                above.setOwner(noOne);
                }
            else if (side == Side.SOUTH && row < (Util.N - 1)) {
                below = this.getSquare(row + 1, col);
                below.setSide(Side.NORTH, false);
                below.setOwner(noOne);
            }
            else if (side == Side.EAST && col < (Util.N - 1)) {
                right = this.getSquare(row, col + 1);
                right.setSide(Side.WEST, false);
                right.setOwner(noOne);
            }
            else if (side == Side.WEST && col > 0) {
                left = this.getSquare(row, col - 1);
                left.setSide(Side.EAST, false);
                left.setOwner(noOne);
            }
        }
    }
    
    public boolean isFull() {
        int 
            p1s = this.getScore().getPlayer1(),
            p2s = this.getScore().getPlayer2();    
            return (p1s + p2s) == Util.N * Util.N; 
    }
    public boolean gameOver() { return this.isFull(); }
    
    public Set<Line> openLines() {
        Set<Line> lineSet = new HashSet<Line>();
        // NB: The Line type has a reasonable equivalence relation
        //     that equates e.g., [0][0].EAST with [0][1].WEST so
        //     no worries RE redundant lines in this set.
        for(int row = 0; row < Util.N; row++) 
            for(int col = 0; col < Util.N; col++) {
                Set<Line> sqLineSet = this.getSquare(row, col).getOpenLines();
                if(dbg.debug)
                    dbg.println("openLines: adding group " + sqLineSet);
                lineSet.addAll(sqLineSet);
            }
        return lineSet;
    }

    public Set<Square> squaresWithMarkedSides(int n) {

        Set<Square> theSet = new HashSet<Square>();
        for(int row = 0; row < Util.N; row++)
            for(int col = 0; col < Util.N; col++) {
                Square square = getSquare(row, col);
                if (square.hasNMarkedSides(n))
                    theSet.add(square);
            }
        return theSet;
    }

    // export returns a 2D array containing -copies- of the Squares in
    // the board.
    //
    public Square[][] export() {
     
        Square[][] out = new Square[Util.N][Util.N];
        for(int row = 0; row < Util.N; row++) {
            for(int col = 0; col < Util.N; col++)
                out[row][col] = this.getSquare(row, col).copy();
        }
        return out;
    }
    
    public String toString() { 
        StringBuilder sb = new StringBuilder("[");
        for (int row = 0; row < Util.N; row++) {
            sb.append("[");
            for(int col = 0; col < Util.N; col++)
                sb.append(this.getSquare(row, col).toString() + ", ");
            sb.append("],\n");
        }
        return sb.toString() + "]";
    }
}