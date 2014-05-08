package players.player1;

import board.Square;
import util.Line;

import java.util.Deque;

/**
 * Created by GleasonK on 5/2/14.
 */
public interface Chain {

    public void addTerminalS(Square s);
    public void addTerminalQ(Square s);
    public Square getTerminalS();
    public Square getTerminalQ();
    public Deque<Square> getChain();
    public boolean chainContains(Square s);
    public int getChainLength();
    public Line hasThreeSidesPlay();
}
