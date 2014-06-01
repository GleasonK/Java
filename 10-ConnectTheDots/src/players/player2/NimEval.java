//File NimEval.java
//Name: Kevin Gleason & Andrew Francl
//Date: May 3, 2014 (Comment out System.out commands)
//Use: Find chains and evaluate a good move.


package players.player2;

import board.Board;
import board.Square;
import players.Player;
import util.Line;
import util.LineC;
import util.Side;
import util.Util;

import java.util.*;

public class NimEval {
    //Instance Variables
    //private Board board;
    private List<Square> twoSide;
    private PriorityQueue<Chain> chainList;
    private PriorityQueue<Chain> longChainList;
    private PriorityQueue<Chain> readyToTake;
    private int boardDimensions;
    int dots;
    int totalMoves;
    int movesLeft;
    int movesMade;
    int boxes;
    boolean first;



    //Constructor
    public NimEval(Board b) {
        //this.board = b;
        //this.twoSide = this.board.squaresWithMarkedSides(2);

        Comparator<Chain> comp = new ChainsComparator();
        this.chainList = new PriorityQueue<Chain>(10, comp); /// PriorityQueue<Chain>();
        this.longChainList = new PriorityQueue<Chain>(10, comp); /// PriorityQueue<Chain>();
        this.readyToTake = new PriorityQueue<Chain>(10, comp);


        //this.findChains();
        //System.out.println(chainList.size());

        this.twoSide = new LinkedList<Square>();
        this.twoSide = makeSquares2Side(b);
        this.boardDimensions = Util.N;
        this.dots = (Util.N + 1) ^ 2;
        this.boxes = Util.N * 2;
        this.totalMoves = 2 * this.dots - 2 * (Util.N + 1);
        this.movesMade = movesMade(b);
        this.movesLeft = movesLeft(b);

    }


//    //USE THE SQUARES FUNCTION THIS FOR EXPORT TO 2 SIDED

    //Create a list of Squares with two sides using the export method
    public List<Square> makeSquares2Side(Board b) {
        Set<Square> ss = b.squaresWithMarkedSides(2);
        List<Square> out = new LinkedList<Square>();
        int i = 0;
        for (Square s : ss) {
            out.add(s.copy());
        }
        return out;
    }

    public List<Square> makeSquares3Side(Board b) {
        Set<Square> ss = b.squaresWithMarkedSides(3);
        List<Square> out = new LinkedList<Square>();
        int i = 0;
        for (Square s : ss) {
            out.add(s.copy());
        }
        return out;
    }

    public void findGoodMove(Board b) {

        //System.out.print(this.chainList.toString());
        //System.out.print("GOOD MOVES:" + this.readyToTake.toString());
    }


    //Find patterns of chains on the board
    //Chains will have 2 sides
    public String findChains() {

        for (Square s : this.twoSide) {
            //System.out.println("Finding Chains...");
            for (Square q : this.twoSide) {
                //System.out.println("Cycling Squares");
                checkChainList(q);

                //Make new Chains. Remove from the twoSet list once used, access via Chain class
                //First be sure that rows match
                if (s.getRow() == q.getRow()) {
                    if (s.getCol() == q.getCol() + 1 && shareSideW(s)) { //Left
                        //System.out.println("I'm removed WEST");
                        twoSide.remove(s);
                        twoSide.remove(q);

                        //Elongate to take whole chain.
                        if (!this.checkChainList(s) && !this.checkChainList(q)) {
                            Chain c = elongate(new ChainC(s, q));
                            this.chainList.add(c);
                            return findChains();
                        }
                    }
                    //Box to Right of S
                    if (s.getCol() == q.getCol() - 1 && shareSideE(s)) {
                        //System.out.println("I'm removed EAST");
                        twoSide.remove(s);
                        twoSide.remove(q);
                        if (!this.checkChainList(s) && !this.checkChainList(q)) {
                            Chain c = elongate(new ChainC(s, q));
                            this.chainList.add(c);
                            return findChains();
                        }
                    }
                }

                //System.out.println("Second IF");
                if (s.getCol() == q.getCol()) {
                    //Box to bottom of S
                    if (s.getRow() == q.getRow() + 1 && shareSideS(s)) {
                        //System.out.println("I'm removed SOUTH");

                        twoSide.remove(s);
                        twoSide.remove(q);
                        if (!this.checkChainList(s) && !this.checkChainList(q)) {
                            Chain c = elongate(new ChainC(s, q));
                            //elongate(c);
                            this.chainList.add(c);
                            //System.out.println("Added " + this.chainList.size());
                            return findChains();
                        }
                    }
                    //Box to top of s
                    if (s.getRow() == q.getRow() - 1 && shareSideN(s)) {
                        //System.out.println("I'm removed NORTH");

                        this.twoSide.remove(s);
                        twoSide.remove(q);
                        if (!this.checkChainList(s) && !this.checkChainList(q)) {
                            Chain c = elongate(new ChainC(s, q));
                            this.chainList.add(c);
                            return findChains();
                        }
                    }
                }
            }
        }
        this.makeLongChains();
//        System.out.println(this.twoSide.toString());
//        System.out.println("Chains " + this.chainList);
//        System.out.println("Long Chains " + this.longChainList);
        return "";
    }

    //Share side with box to left
    private boolean shareSideW(Square s) {
        return !s.sideIsMarked(Side.WEST);
    }

    private boolean shareSideE(Square s) {
        return !s.sideIsMarked(Side.EAST);
    }

    private boolean shareSideN(Square s) {
        return !s.sideIsMarked(Side.NORTH);
    }

    private boolean shareSideS(Square s) {
        return !s.sideIsMarked(Side.SOUTH);
    }

    private boolean checkChainList(Square s) {
        //System.out.println("CHAIN LIST CHECK");
        if (this.chainList.isEmpty()) {
            //System.out.print("EMPTY ");
            return false;
        }
        for (Chain c : this.chainList) {
            if (c.chainContains(s)) return true;
        }
        return false;
    }

    private Deque<Square> getChain(Square s) {
        for (Chain c : this.chainList) {
            if (c.chainContains(s)) return c.getChain();
        }
        throw new NoSuchElementException("Chain Not Found");
    }

    private Chain elongate(Chain c) {
        //System.out.println("Elongating");
        Square S = c.getTerminalS();
        Square Q = c.getTerminalQ();

        for (Square square : this.twoSide) {
            //Make new Chains

            if (S.getRow() == square.getRow() && S.getCol() == square.getCol() + 1 && shareSideW(S)) {
                //System.out.println("EL - W");

                //System.out.println("");
                this.twoSide.remove(square);
                c.addTerminalS(square);
                return elongate(c);
            } else if (Q.getRow() == square.getRow() && Q.getCol() == square.getCol() + 1 && shareSideW(Q)) {
                //System.out.println("EL - W");

                //Elongate to take whole chain.
                this.twoSide.remove(square);
                c.addTerminalQ(square);
                return elongate(c);
            }

            //Box to right of S
            if (S.getRow() == square.getRow() && S.getCol() == square.getCol() - 1 && shareSideE(S)) {
                //System.out.println("EL - E");

                this.twoSide.remove(square);
                c.addTerminalS(square);
                return elongate(c);
            } else if (Q.getRow() == square.getRow() && Q.getCol() == square.getCol() - 1 && shareSideE(Q)) {
                //System.out.println("EL - E");

                this.twoSide.remove(square);
                c.addTerminalQ(square);
                return elongate(c);
            }

            //Box Below S
            if (S.getCol() == square.getCol() && S.getRow() == square.getRow() - 1 && shareSideS(S)) {
                //System.out.println("EL - S");

                this.twoSide.remove(square);
                c.addTerminalS(square);
                return elongate(c);
            } else if (Q.getCol() == square.getCol() && Q.getRow() == square.getRow() - 1 && shareSideS(Q)) {
                //System.out.println("EL - S");

                this.twoSide.remove(square);
                c.addTerminalQ(square);
                return elongate(c);
            }

            //Box on top of S
            //System.out.println("Checking this one, should find it");

            if (S.getCol() == square.getCol() && S.getRow() == square.getRow() + 1 && shareSideN(S)) {
                //System.out.println("EL - N");

                //System.out.println("Check");

                this.twoSide.remove(square);
                c.addTerminalS(square);
                return elongate(c);
            } else if (Q.getCol() == square.getCol() && Q.getRow() == square.getRow() + 1 && shareSideN(Q)) {
                // System.out.println("EL - N");

                //System.out.println("Check");

                this.twoSide.remove(square);
                c.addTerminalQ(square);
                return elongate(c);
            }
        }
        //System.out.println("Returning Chain list");
        //c.toString();
        return c;
    }

    public int nChains() {
        return this.chainList.size();
    }

    //Filters the shorter chains and takes all greater than 3.
    private void makeLongChains() {
        for (Chain c : this.chainList) {
            if (c.getChainLength() > 2) {
                this.longChainList.add(c);
            }
        }
    }

    public int movesLeft(Board b) {
        // #dots + #long chains - 1 = #moves made + #moves left
        int moves = this.dots + this.longChainList.size() - 1;
        int movesMade = movesMade(b);
        return moves - movesMade;
    }

    //Moves made on the board
    private int movesMade(Board b) {
        int squaresTaken = b.squaresWithMarkedSides(4).size();
        int linesTaken = totalMoves - b.openLines().size();
        int movesMade = linesTaken - squaresTaken;
        return movesMade;
    }


    //FILTER OUT ALL SQUARES IN LONG CHAINS
    private boolean squareInLongChain(Square s) {
        for (Chain c : this.longChainList) {
            if (c.chainContains(s)) return true;
        }
        return false;
    }

    //Tell if we are up first
    public boolean first() {
        return this.movesMade % 2 == 0;
    }


    //Finds a chain then elongates it and takes it out of the list so they aren't recounted
    public PriorityQueue<Chain> findReadytoTake(Board b) {
        for (Chain c : this.chainList) {
            Square S = c.getTerminalS();
            Square Q = c.getTerminalQ();
            Set<Square> ssThree = b.squaresWithMarkedSides(3);
            for (Square square : ssThree) {
                if (S.getRow() == square.getRow() && S.getCol() == square.getCol() + 1 && shareSideW(S)) {
                    //System.out.println("EL - W");
                    c.addTerminalS(square);
                    this.readyToTake.add(c);
                    continue;
                } else if (Q.getRow() == square.getRow() && Q.getCol() == square.getCol() + 1 && shareSideW(Q)) {
                    //System.out.println("EL - W");
                    //Elongate to take whole chain.
                    c.addTerminalQ(square);
                    this.readyToTake.add(c);
                    continue;
                }

                //Box to right of S
                if (S.getRow() == square.getRow() && S.getCol() == square.getCol() - 1 && shareSideE(S)) {
                    //System.out.println("EL - E");
                    c.addTerminalS(square);
                    this.readyToTake.add(c);
                    continue;
                } else if (Q.getRow() == square.getRow() && Q.getCol() == square.getCol() - 1 && shareSideE(Q)) {
                    // System.out.println("EL - E");
                    c.addTerminalQ(square);
                    this.readyToTake.add(c);
                    continue;
                }

                //Box Below S
                if (S.getCol() == square.getCol() && S.getRow() == square.getRow() - 1 && shareSideS(S)) {
                    // System.out.println("EL - S");
                    c.addTerminalS(square);
                    this.readyToTake.add(c);
                    continue;
                } else if (Q.getCol() == square.getCol() && Q.getRow() == square.getRow() - 1 && shareSideS(Q)) {
                    //System.out.println("EL - S");
                    c.addTerminalQ(square);
                    this.readyToTake.add(c);
                    continue;
                }
                if (S.getCol() == square.getCol() && S.getRow() == square.getRow() + 1 && shareSideN(S)) {
                    // System.out.println("EL - N");
                    c.addTerminalS(square);
                    this.readyToTake.add(c);
                    continue;
                } else if (Q.getCol() == square.getCol() && Q.getRow() == square.getRow() + 1 && shareSideN(Q)) {
                    // System.out.println("EL - N");
                    c.addTerminalQ(square);
                    this.readyToTake.add(c);
                    continue;
                }
            }
        }
        return this.readyToTake;

    }

    private Line breakChain() {
        if (longChainList.size() != 0){
            Chain smallChain = longChainList.poll();
            Square termQ = smallChain.getTerminalQ();
            Set<Line> lineChoice = termQ.getOpenLines();
            for (Line l : lineChoice)
                return l;

        }
        throw new NoSuchElementException();
    }

    private List<Square> notInChains(Board b){
        List<Square> safe = new LinkedList<Square>();
        Set<Square> oSquares = b.squaresWithMarkedSides(1);
        oSquares.addAll(b.squaresWithMarkedSides(2));
        oSquares.addAll(b.squaresWithMarkedSides(3));
        for (Square sq : oSquares ){
            if (!squareInLongChain(sq)) {
                safe.add(sq);
            }
        }
        return safe;
    }

    public Line makeRandomPlay(Board b, Player p1, Player opp) {
        //System.out.println("Hello");
        List<Square> decentMoves = notInChains(b);
        int choice = (int) Math.random() * decentMoves.size();
        int i = 0;
        for (Square sq : decentMoves) {
            if (i == choice) {
                Square ChoiceSquare = sq;
                int lineChoice = (int) Math.random() * ChoiceSquare.getOpenLines().size();
                int j = 0;
                for (Line l : ChoiceSquare.getOpenLines()) {
                    if (j == lineChoice) {
                        if (evaluate(b, l, p1)) return l; //evaluate(b, l, p1)
                    }
                    j = j + 1;
                }
            }
            i = i + 1;
        }


        //Sacrifice move.
        if (this.chainList.size() > 0) {
            Chain ch = this.chainList.poll();
            for (Square s : ch.getChain()) {
                Set<Line> ll = s.getOpenLines();
                for (Line l : ll)
                    return l;
            }
        }
        //Forced to play random, or break a chain to find a smarter move. No smart moves.
        if (this.longChainList.size() > 0)
            return breakChain();
        return makeRandomPlay1(b);

        //Monte Carlo Broken in game mode: causes player 2 to move in game simulations
        //return new MonteCarlo().findMove(p1, opp, b).line;//makeRandomPlay1(b);
        //Shouldn't hit here
        //throw new NoSuchElementException("Oops.");
    }


    //Check if playing the move gives the board back with an odd number of moves.
    public boolean evaluate(Board b, Line l, Player p1) {
        b.markLine(p1, l);
        NimEval goodMove = new NimEval(b);
        goodMove.findChains();
        b.removeLine(l);
        if (goodMove.movesLeft % 2 == 0) return true;
        return false;
    }

    //Copied from Player1 (Running out of time)
    public Line makeRandomPlay1(Board board) {
        Set<Line> lines = board.openLines();
        int pick = (int) (Math.random() * lines.size()),
                i = 0;
        for(Line line : lines) {
            if(i == pick) return line;
            i = i + 1;
        }
        return new LineC(0, 0, Side.NORTH);
    }


    public int getMovesLeft(){ return this.movesLeft; }
}
