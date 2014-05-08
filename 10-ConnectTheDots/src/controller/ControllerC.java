package controller;

import ui.BoardDisplay;
import ui.ControlDisplay;
import util.*;
import board.*;
import players.*;
import players.player1.*;
import players.player2.*;

import java.util.*;
import java.awt.event.*;

public class ControllerC implements Controller {

    private Mode mode;
    private Board board;
    private Player player1, player2;
    private boolean interactive;
    private Set<board.Square> claimedSquares;
    private Line oppPlay;
    private Score score;
    private Score games;
    private boolean stop;
    private DBG dbg;
    private java.awt.Container contentPane;
    private BoardDisplay boardDisplay;
    private ControlDisplay controlDisplay;
    private int pace;
    private long timeout;

    public ControllerC(Mode mode) {
        this.mode = mode;
        this.dbg = new DBG(DBG.CONTROL, "ControllerC");

        this.pace = Util.DEFAULT_PACE;
        this.timeout = Util.DEFAULT_TIMEOUT;
        
        // The players are set by the reset function but we have to
        // have them preset here because they need to be set when the
        // ControlDisplay is made and that is made -before- we can call
        // reset.
        //
        this.player1 = new Player1();
        setPlayer2(mode);
        this.setBoard(new BoardC(this));

        this.score = new ScoreC(0, 0);
        this.games = new ScoreC(0, 0);
    }

    // Getters
    //
    public Mode getMode()                     { return this.mode; }
    public long getTimeout()                  { return this.timeout; }
    public ControlDisplay getControlDisplay() { return this.controlDisplay; }
    public Player getPlayer(int i) {
        if (i == 1 && this.player1 != null)
            return this.player1;
        else if (i == 2 && this.player2 != null)
            return this.player2;
        else
            throw new RuntimeException("getPlayer: there is no player=" + i);
    }
    
    // Various setters.
    //
    public void setBoardDisplay(BoardDisplay bd)        { this.boardDisplay = bd; }
    public void setControlDisplay(ControlDisplay cd)    { this.controlDisplay = cd; }
    public void setContentPane(java.awt.Container pane) { this.contentPane = pane; }    
    public void setBoard(Board board)                   { this.board = board; }
    public void setPace(int sliderPace)                 { this.pace = sliderPace; } 
    public void setPlayer2(Mode mode) {
        player2 = (mode == Mode.INTERACTIVE) ? new HumanPlayer() : new Player2();
    }
    
    // This is the action listener that is called by the UI in the case
    // of a mode reset.
    //
    public void reset(Mode mode) {
        this.mode = mode;
        String message = "resetting to " + mode + " mode.";
        
        if(dbg.debug) dbg.println(message);
        controlDisplay.setStatus(message);

        // Make a fresh board and player2, we're leaving player1 as is.
        //
        this.setBoard(new BoardC(this));
        setPlayer2(mode);

        this.score = new ScoreC(0, 0);
        this.games = new ScoreC(0, 0);
        
        // Reset the UI, first refresh the board display.
        //
        this.boardDisplay.reset();
        
        // Now for the control display. Need to replace it with a new
        // one customized for the newly selected mode. NB: the newly
        // made control display will retrieve the new mode from the
        // controller (which is passed to it as this).
        //
        int height = this.controlDisplay.getHeight();
        this.contentPane.remove(this.controlDisplay);
        
        this.controlDisplay.removeAll();
        
        ControlDisplay newCD = new ControlDisplay(this, height);
        this.contentPane.add(newCD, java.awt.BorderLayout.EAST);
        
        this.contentPane.invalidate();
        this.contentPane.validate();

        this.oppPlay = null;
    }

    /////////////////////////////////////////////////////////////////////////
    //
    // INTERACTIVE MODE
    //
    // This is the action listener that is called by the UI when the human
    // being uses the mouse to select a line.
    //
    public void makeHumanPlay(Line line) {
        if(this.dbg.debug)
            dbg.println("makeHumanPlay: attempting to play " + line);

        this.oppPlay = line;
        try {
            // One line can claim 0, 1 or 2 squares.
            //
            this.claimedSquares = board.markLine(this.player2, line);

            if(this.dbg.debug)
                dbg.println("makeHumanPlay: claiming squares " + this.claimedSquares);

            boardDisplay.selectLine(line);
            for(board.Square square : this.claimedSquares)
                boardDisplay.colorSquare(square, this.player2.getColor());
 
            score.add(this.player2, this.claimedSquares.size());
            controlDisplay.setGameScore(score);
            if(this.dbg.debug) dbg.println("makeHumanPlay: score=" + score);
        }
        catch (RuntimeException msg) {
            reset(this.mode);
            controlDisplay.setStatus(msg.getMessage());
        }
        if(board.isFull()) showWinner(this.score);
    }

    // This is the action listner for the step button in INTERACTIVE mode. 
    // It plays the Player1's turn in interacive mode.
    //
    public void stepResponder() {
        
        dbg.println("stepResponder: I made it here.");
        
        boolean filledASquare = true;
        Line line = null;

        while(filledASquare && !board.isFull()) {
            try {
                line = this.player1.makePlay(this.board, player2, this.oppPlay, null);
                if(this.dbg.debug)
                    dbg.println("stepResponder: player1 attempting to play " + line);
            }
            catch (RuntimeException msg) {
                controlDisplay.setStatus(msg.getMessage());
            }
            try {
                this.claimedSquares = board.markLine(this.player1, line);
                    
                if(this.dbg.debug)
                    dbg.println("stepResponder: " + this.claimedSquares.size() + " squares claimed.");
                boardDisplay.selectLine(line);

                filledASquare = !this.claimedSquares.isEmpty();

                if (filledASquare) {
                    score.add(this.player1, this.claimedSquares.size());

                    controlDisplay.setGameScore(score);
                        
                    dbg.println("stepResponder: score=" + score);
                        
                    for(board.Square square : this.claimedSquares)
                        boardDisplay.colorSquare(square, this.player1.getColor());
                }
            }
            catch (RuntimeException msg) {
                reset(this.mode);
                controlDisplay.setStatus(msg.getMessage());
            }
        }
        if(board.isFull()) showWinner(this.score);
    }
    
    private void showWinner(Score score) {
        int p1 = score.getPlayer1(),
            p2 = score.getPlayer2();
        if (p1 > p2)
            controlDisplay.setStatus(player1.teamName() + " win.");
        else if (p1 < p2)
            controlDisplay.setStatus(player2.teamName() + " win.");
        else
            controlDisplay.setStatus("This game ended in a tie.");
    }

    ////////////////////////////////////////////////////////////////////////
    //
    // GAME MODE
    //
    // Action listeners for GAME mode.
    //
    
    public Score theScore;
        
    class PlayOneGame extends javax.swing.SwingWorker<Score, Object> {
        @Override
        public Score doInBackground() { return doPlayOneGame(); }

        @Override
        protected void done() {
            try {
                theScore = get();
            } catch (Exception ignore) {}
        }
   }
    // This is the action listner for the Start button in GAME mode.
    //
    public Score playOneGame() {
        (new PlayOneGame()).execute();
        return theScore;
    }
    public Score doPlayOneGame() {
            
        Player playingFirst, playingSecond;
        boolean player1GoesFirst = true;  ///////////////Math.random() < .5;
        
        //        this.setBoard(new BoardC(this));
        
        Clock 
            clock1 = new Clock(player1, getTimeout()),
            clock2 = new Clock(player2, getTimeout()),
            pFirstClock, pSecondClock;
        
        if (player1GoesFirst) {
            if(dbg.debug)
                dbg.println(player1.teamName() + " playing first");
            playingFirst  = this.player1;
            playingSecond = this.player2;
            //
            pFirstClock  = clock1; 
            pSecondClock = clock2;
        }
        else {
            if(dbg.debug)
                dbg.println(player2.teamName() + " playing first");
            playingFirst  = this.player2;
            playingSecond = this.player1;
            //
            pFirstClock  = clock2; 
            pSecondClock = clock1;
        }
        this.oppPlay = null;
        boolean gameOver = false;
        
        while(!gameOver) {
            
            Util.wait(this.pace);    // The pace is set by the slider in the UI.
            
            try {
                gameOver = takeATurn(playingFirst, pFirstClock);
                controlDisplay.setProgress(pFirstClock);
            }
            catch (Exception msg) {
                // First to play did something wrong, want to return 
                // a score with zero for firstToPlay
                //
                if(dbg.debug)
                    dbg.println("playOneGame: caught exception: " + msg + " from takeATurn");
                return gameOverByRuleViolation(player1GoesFirst);
            }
            if(gameOver) break;
            
            Util.wait(this.pace);
            
            try {
                gameOver = takeATurn(playingSecond, pSecondClock);
                controlDisplay.setProgress(pSecondClock);
            }
            catch (Exception msg) {
                // Second to play did something wrong, want to return 
                // a score with zero for secondToPlay
                //
                return gameOverByRuleViolation(!player1GoesFirst);            
            }
        }
        Score score = board.getScore();
        showWinner(score);
        return score;
    }

    // This isn't implemented yet.
    //
    public void stopResponder() { this.stop = true; }
    
    private Score gameOverByRuleViolation(boolean player1GoesFirst) {
        Score score;
        if (player1GoesFirst)
            score = new ScoreC(0, Util.ALL);
        else
            score = new ScoreC(Util.ALL, 0);
        showWinner(score);
        return score;
    }
    
    //////////////////////////////////////////////////////////////////////////////
    
 
    // The TakeATurn class is implemented as an extension of SwingWorker
    // because we want to be able to put the player on a clock that can
    // be interrupted if the clock expires.
    //
    class TakeATurn extends javax.swing.SwingWorker<Boolean, Object> {
        
        Player player;
        Clock clock;
        TakeATurn(Player p, Clock c) { this.player = p; this.clock = c; }
        
        @Override
        public Boolean doInBackground() throws Exception {
            if(dbg.debug)
                dbg.println("doInBackground: " + player + " taking a turn."); 
            clock.restart();
            return doTakeATurn(player, clock);}
    }
   
    public boolean takeATurn(Player player, Clock clock) {
        TakeATurn worker = new TakeATurn(player, clock);
        Boolean theAnswer = new Boolean(false);
        worker.execute();      
        try {
            theAnswer = worker.get(clock.timeRemaining(), 
                                   java.util.concurrent.TimeUnit.MILLISECONDS);
            clock.stop();
            if(dbg.debug) {
                long et = clock.elapsedTime(), tr = clock.timeRemaining();
                dbg.println("takeATurn: " + player + ", elapsedTime=" + et + ", timeRemaining=" + tr + ".\n");
            }
        } catch (java.util.concurrent.TimeoutException msg)
            {
                String 
                    message = "takeATurn: game over. " + player + " ran out of time.";
                if (dbg.debug) dbg.println(message);
                controlDisplay.setStatus(message);
                throw new RuntimeException(message);
            } catch (Exception ignored) {}
            
        return theAnswer.booleanValue();
    }
    
    private boolean doTakeATurn(Player player, Clock clock) {
        if(dbg.debug)
            dbg.println("doTakeATurn: " + player.toString() + " playing.");
 
        boolean
            filledASquare = true, //,
        gameOver = board.isFull();
        java.awt.Color color = player.getColor();
        Line line;
        
        while(!gameOver && filledASquare) {
        
            line = player.makePlay(this.board, null, this.oppPlay, clock);
            this.claimedSquares = board.markLine(player, line);
 
            score.add(player, this.claimedSquares.size());
            
            // Update the display. NB: one line may claim up to 2 squares.
            //
            controlDisplay.setGameScore(score);
            boardDisplay.selectLine(line);
            for(board.Square square : this.claimedSquares) 
                boardDisplay.colorSquare(square, color);
 
            filledASquare = !this.claimedSquares.isEmpty();
            gameOver = board.isFull();
        }
        return gameOver;
    }
    
    ///////////////////////////////////////////////////////////////////////
    //
    // MATCH MODE
    //
    // This is the action listener for the Start button in MATCH mode.
    //
    public void playMatch() {}
}
   
