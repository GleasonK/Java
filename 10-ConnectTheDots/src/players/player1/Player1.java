//File Player1.java
//Name: Kevin Gleason & Andrew Francl
//Date: May 3, 2014 (Comment out System.out commands)
//Use: Main player class

package players.player1;

import players.*;
import board.*;
import util.*;
import java.util.*;
import java.awt.Color;

public class Player1 implements Player {

    private MonteCarlo MC = new MonteCarlo();
    private Chain PriorityMoves;
    private boolean takePriority = false;

    private DBG dbg;

    public Player1() {
        dbg = new DBG(true, "Player1");//DBG.PLAYERS, "Player1");
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
    public Color getColor()  { return Util.PLAYER1_COLOR; }
    public int getId()       { return 1; }
    public String toString() { return teamName(); }

    private Line findMCMove(Player opponent, Board b){//, Line lastPlay){
        return this.MC.line;
    }

    //Make a random play for Monte Carlo
    public Line makeRandomPlay(Board board) {
        Set<Line> lines = board.openLines();
        //if(dbg.debug)
        //    dbg.println("Player 1 Thinks there are " + lines.size() + " open lines");

        int pick = (int) (Math.random() * lines.size()),
                i = 0;
        for(Line line : lines) {
            if(i == pick) return line;
            i = i + 1;
        }
        return new LineC(0, 0, Side.NORTH);
    }




}