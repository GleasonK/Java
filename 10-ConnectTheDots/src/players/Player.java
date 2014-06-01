

package players;

import util.*;
import board.*;

public interface Player {
    
    public Line makePlay(Board board, Player opponent, Line oppPlay, Clock clock);
    public String teamName();
    public java.awt.Color getColor();
    public int getId();
    public String toString();
    public Line makeRandomPlay(Board b);
}