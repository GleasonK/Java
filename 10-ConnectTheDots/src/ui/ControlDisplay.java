package ui;

import util.*;
import controller.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;

public class ControlDisplay extends JPanel {
    
    private Controller controller;
    private StatusPanel statusPanel;
    private ModePanel modePanel;
    private ControlPanel controlPanel;
    private Mode mode;
    private int height;
    private DBG dbg;
    private Util util;

    public ControlDisplay(Controller controller, int height) {
        this.controller = controller;
        this.height = height;
        this.mode = controller.getMode();
        
        controller.setControlDisplay(this);    // Is this still needed?
        
        this.dbg = new DBG(DBG.UI, "ControlDisplay");
        this.util = new Util(controller);

        this.controlPanel = util.makeControlPanel(controller, mode);
        
        this.modePanel = new ModePanel(controller, mode);
        this.statusPanel = new StatusPanel(controller);
        this.setLayout(new GridLayout(3, 1)); 

        add(this.modePanel);

        if(mode == Mode.INTERACTIVE) {
            if (dbg.debug) dbg.println("in INTERACTIVE clause...");
            InteractiveControlPanel icp = (InteractiveControlPanel) this.controlPanel;
            add(icp);
        }
        else if(mode == Mode.GAME) 
            add((GameControlPanel) this.controlPanel);
        else 
            add((MatchControlPanel) this.controlPanel);

        add(this.statusPanel);

        controlPanel.setGameScore(new ScoreC(0, 0));
        
        setStatus("A " + milliToSeconds(controller.getTimeout()) + " second game.");
    }

    // Getters
    //
    public ModePanel getModePanel()       { return this.modePanel; }
    public ControlPanel getControlPanel() { return this.controlPanel; }
    public StatusPanel getStatusPanel()   { return this.statusPanel; }
    public int getHeight()                { return this.height; }
    public int getGames()                 { return this.controlPanel.getGames(); }

    // Setters
    //
    // Of the three panels, only the control panel can be replaced.
    //
    public void setControlPanel(ControlPanel cp) { this.controlPanel = cp; }
    
    public void setStatus(String status)  { this.statusPanel.setStatus(status); }
    public void setProgress(Clock clock)  { this.controlPanel.setProgress(clock); }
    public void setGameScore(Score score) { this.controlPanel.setGameScore(score); }
    public void setMatchScore(Score score) { this.controlPanel.setMatchScore(score); }
    
    // The board display should be square so we'll make this control display
    // consume the remainder of the width of the rectangle.
    //
    public Dimension getPreferredSize() {
        return new Dimension((int) (this.height / 1.618), 0);
    }
    public static String milliToSeconds(long milliseconds) {
        return String.format("%4.1f", ((double) milliseconds / 1000.));
    }
}

