//File: MonteCarlo.java
//Name: Kevin Gleason & Andrew Francl
//Date: May 2, 2014
//Use: The Monte Carlo Tree Search. Found simple implementation (not for Dots, or any game, simply example) on mcts.com
//      Edited it for use with Dots and boxes, didnt use since it wouldnt work with the game mode.

package players.player2;

import board.Board;
import players.Player;
import util.Line;
import util.LineC;
import util.Side;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class MonteCarlo {
    //Instance Variables
    Line line;
    static Random r = new Random();

    // private int nActions = openLines.size();  //Num open spots
    static double epsilon = 1e-6;

    //box
    //side //[][]
    //Chains list !list.isEmpty();

    MonteCarlo[] children;
    double nVisits, totValue;

    public MonteCarlo(){}

    //Use a monte Carlo random probability tree to select the move that most won after n number of playouts
    public MonteCarlo findMove(Player p1, Player p2, Board b){//, Line lastPlay) {
        //this.openLines = b.openLines();
        System.out.println(b.openLines().size());
        //Expand the board one time per move.
        this.expand(b);
        int n = 200;
        for (int i=0; i<n; i++) {
            //this.openLines = b.openLines();
            this.selectAction(p1, p2, b);
        }

        System.out.println("Found move");
        //System.out.println(this.toString());

        //Use this to return the MC tree with all children explored.
        //new MCTest(p1,p2,b);
        return this.select();
    }


    public void selectAction(Player p1, Player p2, Board b) {
        //Problem arises when switching to the first leaf that was made and running the trials
        //Once it switches leaves then declares: !isleaf() if cant expand properly. Weird results

        List<MonteCarlo> visited = new LinkedList<MonteCarlo>();
        MonteCarlo cur = this;
        visited.add(this);

        //Trouble traversing the tree
//        while (!cur.isLeaf()) {
//            cur = cur.select();
//            //System.out.println("Adding: " + cur.line);
//            visited.add(cur);
//        }

        System.out.println("CURR.Exp");
        visited.add(this);
        MonteCarlo newNode = cur.select();
        visited.add(newNode);
        double value = rollOut(newNode, p1, p2, b);//newNode, p1, p2, b);

        //Adds visits to everything that has been visited this iteration
        for (MonteCarlo node : visited) {

            // would need extra logic for n-player game
            //System.out.println("UPDATING NODES");  //DEBUG

            // System.out.println(node);
            node.updateStats(value);
        }

        System.out.println("SelectAction Completed");  //DEBUG
    }

    public void expand(Board b) {

       // System.out.println("Lines Expanding " + b.openLines().size());
        //BREAKS HERE

        this.children = new MonteCarlo[b.openLines().size()];
        int i = 0;
        for (Line line : b.openLines()) {
            //System.out.println("Expanding"); //DEBUG
            children[i] = new MonteCarlo();
            children[i].setLine(line);
            i++;
        }
    }

    private void setLine(Line line){
        this.line = line;
    }

    private MonteCarlo select() {
        // vi+C x sqrt(lnN/ni)
        // vi is value of node
        // C is tunable bias parameter
        // N is number of times the parent was visited
        // ni is the number of times node visited
        MonteCarlo selected = null;
        double bestValue = Double.MIN_VALUE;
        for (MonteCarlo c : children) {
            double uctValue =   //Value equals totValue / nVisited
                    c.totValue / (c.nVisits + epsilon) +
                            Math.sqrt(Math.log(nVisits+1) / (c.nVisits + epsilon)) +
                            r.nextDouble() * epsilon;

            // small random number to break ties randomly in unexpanded nodes

            System.out.println("Value " + c.showValue() + " -- UCT value = " + uctValue + " -- Line = " + c.line); //DEBUG
            if (uctValue > bestValue) {
                selected = c;
                bestValue = uctValue;
            }
        }
        System.out.println("Selected: " + selected.line);
        return selected;
    }

    //Used to select the move with the highest ratio at its node
    public MonteCarlo selectBest(){
        MonteCarlo selected = null;
        return null;
    }

    public boolean isLeaf() {
        return children == null;
    }

    //Take a leaf and flesh it out a little.
    public double rollOut(MonteCarlo nextMove, Player p1, Player p2, Board b) {
        //Play out game, return 1 (win) or 0 (loss).
        //Monte Carlo kicks into random probability algorithm to calculate win/loss probability
        System.out.println("Simulation of " + nextMove.line);  //DEBUG

        b.markLine(p1, nextMove.line);
        System.out.println("Took Line " + nextMove.line.toString() + b.getSquare(nextMove.line.getRow(),nextMove.line.getCol()).hasNMarkedSides(4));  //DEBUG

        int gameStat =
                b.getSquare(nextMove.line.getRow(),nextMove.line.getCol()).hasNMarkedSides(4) ?
                        this.makeRollMove(p1,p2,b) : this.makeRollMove(p2,p1,b);
        System.out.println("Game Stats " + gameStat);
        b.removeLine(nextMove.line);
        //System.out.println("Removed All Lines"); //DEBUG
        return gameStat;
    }


    //WHEN IT SEES SOMETHING (NON LEAF) IT SKIPS OVER IT
    private int makeRollMove(Player turn, Player waiting, Board b){
        //System.out.println("Make Roll "); //DEBUG
        int WoL;
        if (b.gameOver()) {
            System.out.println(b.getScore());
            return b.getScore().getPlayer1() > b.getScore().getPlayer2() ? 1 :
                    b.getScore().getPlayer1() == b.getScore().getPlayer2() ? 0 : -1;
        }
        Line rLine = rollOutRandomize(b);
        System.out.print(turn.getId() + " Marked " + rLine);
        b.markLine(turn, rLine);

        //Cycle turns unless box just acted upon is taken.
        if(b.getSquare(rLine.getRow(),rLine.getCol()).hasNMarkedSides(4))
            WoL = this.makeRollMove(turn, waiting, b);
        else
            WoL = this.makeRollMove(waiting, turn, b);
        b.removeLine(rLine);
        //System.out.println("Removing Rolls--"); System.out.print(b.openLines().size()); //DEBUG
        return WoL;
    }

    private Line rollOutRandomize(Board b) {
//        this.currAvailable = b.openLines();

        int pick = (int) (Math.random() * b.openLines().size()),
                i = 0;
        for(Line line : b.openLines()) {
            if(i == pick) {return line;}
            i = i + 1;
        }
        return new LineC(0, 0, Side.NORTH);
    }

    public void updateStats(double value) {
        nVisits++;
        totValue += value;
    }

    public int arity() {
        return children == null ? 0 : children.length;
    }

    private String showValue(){
        return this.totValue + "/" + this.nVisits;
    }
}