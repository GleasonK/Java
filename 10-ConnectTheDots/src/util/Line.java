package util;

import board.*;
import java.util.*;

public interface Line {
    
    public Side getSide();
    public int getRow();
    public int getCol();
    
    public boolean isLegal(Board board);
    public boolean isOpen(Board board);
    public Set<Square> getSquares(Board board);

    @Override
    public boolean equals(Object other);
    @Override
    public int hashCode();
    
    public String toString();
}