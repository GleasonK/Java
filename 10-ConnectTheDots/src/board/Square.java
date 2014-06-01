package board;

import util.*;
import players.*;

import java.util.*;

public interface Square {

    public int getRow();
    public int getCol();
    public Option<Player> getOwner();
    public boolean get(Side side);
    public void setOwner(Option<Player> p);
    public void setSide(Side side, boolean value);
    public boolean sideIsMarked(Side side);
    public boolean hasFourSides();
    public void mark(Side side);
    public boolean isOwned();
    public Set<Line> getOpenLines();
    public boolean hasNMarkedSides(int n);
    public String toString();
    public Square copy();
}
