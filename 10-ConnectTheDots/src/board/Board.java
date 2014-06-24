package board;

import java.util.*;
import players.*;
import util.*;

public interface Board {
    public boolean isFull();
    public boolean gameOver();
    public Score getScore();
    public Square getSquare(int row, int col);
    public Set<Square> markLine(Player player, Line line);
    public void removeLine(Line line);    
    public Set<Line> openLines();

    // The following function returns the set of all Squares with
    // n marked sides. The integer n should be in the range 0..4.
    //
    public Set<Square> squaresWithMarkedSides(int n);

    // Exports copy of Board to 2D array of Square.
    //
    public Square[][] export();
    public String toString();
}
