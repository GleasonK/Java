package players.player1;

import board.Square;
import util.Line;
import util.Side;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Set;


public class ChainC implements Chain{
    //Instance Variables
    private Deque<Square> chain;
    private int N;



    public ChainC(Square s, Square q) {
        this.chain = new ArrayDeque<Square>();
        this.chain.add(s);
        this.chain.add(q);
        this.N=2;
    }

    //West Terminal will be the right most (or just the Square s Terminal)
    public void addTerminalS(Square s){
        this.chain.addFirst(s);
        this.N++;
    }

    public void addTerminalQ(Square s){
        this.chain.addLast(s);
        this.N++;
    }

    public Square getTerminalS(){
        return this.chain.peekFirst();
    }
    public Square getTerminalQ(){
        return this.chain.peekLast();
    }


    public Deque<Square> getChain(){
        return this.chain;
    }

    public boolean chainContains(Square s){
        for (Square squ : this.chain) {
            if (s==squ) return true;
        }
        return false;
    }

    public String toString() {
        return "[" + this.chain + "]";
    }

    public int getChainLength(){
        return this.N;
    }

    public Line hasThreeSidesPlay() {
        for (Square s : this.chain) {
            if (s.hasNMarkedSides(3)) {
                this.chain.remove(s.hasNMarkedSides(3));
                Set<Line> set = s.getOpenLines();
                for (Line l : set)
                    return l;
            }
        }
        throw new NoSuchElementException("Not Existent.");
    }




}
