package controller;

import ui.*;
import util.*;
import board.*;
import players.*;

public interface Controller {
    
    public void setBoardDisplay(BoardDisplay bd);
    public ControlDisplay getControlDisplay();
    public void setControlDisplay(ControlDisplay cd);
    public void setBoard(Board board);
    public void reset(Mode mode);
    public Mode getMode();
    public Player getPlayer(int i);
    public void setPace(int sliderPace);
    public long getTimeout();

    // This one comes from the UI as the result of mouse click.
    //
    public void makeHumanPlay(Line line);

    // This routine is called by the button actionListener in
    // the UI. This routine should confirm with the board that
    // the play is playable given the board
    //
    //    public void makePlay(Player player, int row, int col, Side side);
    
    // This routine reinitializes the board. If the radio button
    // is selected, it sets the controller into Interactive Mode.
    // This creates one player which will be called on to make a
    // play in response to the step button. Other buttons are 
    // deactivated.
    // 
    public void stepResponder();
    public void stopResponder();
//    public Score playOneGame(Score overAll);
    public Score playOneGame();
    public void playMatch();
    public void setContentPane(java.awt.Container pane);
}