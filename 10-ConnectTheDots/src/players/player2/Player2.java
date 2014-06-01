//File Player1.java
//Name: Kevin Gleason & Andrew Francl
//Date: May 3, 2014 (Comment out System.out commands)
//Use: Main player class

package players.player2;

import players.*;
import board.*;
import util.*;
import java.util.*;
import java.awt.Color;

public class Player2 implements Player {
 
    private DBG dbg;

    private players.player1.MonteCarlo MC = new players.player1.MonteCarlo();
    private Chain PriorityMoves;
    private boolean takePriority = false;

    public Player2() {
        dbg = new DBG(true, "Player2");//DBG.PLAYERS,"Player2");
    }

    public Line makePlay(Board board, Player opponent, Line oppPlay, Clock clock) {

        //Find the Priority Move on the board
        //If there are chains free to take and they wont harm us to grab, take them.
        if (takePriority) {
            System.out.println("PRIORITY");
            for (Square s : this.PriorityMoves.getChain()) {
                Square tmp = board.getSquare(s.getRow(),s.getCol());
                if (tmp.hasNMarkedSides(3)) {
                    //Run through pick the three sider, if none then set to false and move on.
                    Set<Line> setTmp = tmp.getOpenLines();
                    System.out.println(this.PriorityMoves.getChain().size());
                    //this.PriorityMoves.getChain().remove(tmp);
                    //if (PriorityMoves.getChain().size() < 1) this.takePriority=false;
                    for(Line l : setTmp) {
                        return l;
                    }

                }

            }
            //Clear Priority
            this.PriorityMoves = null;
            this.takePriority = false;
        }


        //If there are two boxes side with two sides there can be a small chain at least
        if (board.squaresWithMarkedSides(2).size() > 1) {
            NimEval goodMove = new NimEval(board);
            goodMove.findChains();
            System.out.println(goodMove.movesLeft);
            PriorityQueue<Chain> priMoves = goodMove.findReadytoTake(board);
            System.out.println(priMoves);
            if (goodMove.movesLeft % 2 == 0 && priMoves.size() > 0) {

                System.out.println("Priority " + this.PriorityMoves);

                //Set Priority Moves and take the three sider.
                this.PriorityMoves = priMoves.poll();
                this.PriorityMoves.hasThreeSidesPlay();
                this.takePriority=true;
            }
            System.out.println("MAKING GOOD RANDOM");
            return goodMove.makeRandomPlay(board, this, opponent);
        }

        System.out.println("PLAYER1 MOVED RANDOM" );

        return makeRandomPlay(board);

        //had a Monte Carlo tree running, but it doesn't work with game mode, only Step
        //Changed over to using Chain count and a minor Nim-Theory
        //return new MonteCarlo().findMove(this, opponent, board).line;

    }
    public String teamName() { return "Problem Set 10"; }
    public Color getColor()  { return Util.PLAYER2_COLOR; }    
    public int getId()       { return 2; }
    public String toString() { return teamName(); }

    public Line makeRandomPlay(Board board) {
        Set<Line> lines = board.openLines();

        System.out.println("Pre For");

        int pick = (int) (Math.random() * lines.size()),
                i = 0;
        //never returns
        for (Line line : lines) {
            if (i == pick){System.out.println("Return line " + line.toString()); return line;}
            i = i + 1;
        }
        System.out.println("Return new LineC");
        return new LineC(0, 0, Side.NORTH);
    }
}