package ui;

import util.*;
import players.*;
import controller.*;
import javax.swing.*;
import java.awt.*;

public class Util {

    private Controller controller;
    private DBG dbg;

    public Util(Controller controller) {
        this.controller = controller;
        this.dbg = new DBG(DBG.UI, "Util");
    }

    public ControlPanel makeControlPanel(Controller controller, Mode mode) {

        if(dbg.debug) 
            dbg.println("makeControlPanel: making a " + mode + " control panel.");

        switch(mode) {
        case INTERACTIVE : return new InteractiveControlPanel(controller); 
        case GAME        : return new GameControlPanel(controller);
        case MATCH       : return new MatchControlPanel(controller);
        default          : return new InteractiveControlPanel(controller);
        }
    }

    // A score panel for an INTERACTIVE game.
    //
    public JPanel makeScorePanel(JTextField p1sqc, JTextField p2sqc) {
        Player
            player1 = controller.getPlayer(1),
            player2 = controller.getPlayer(2);
        
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
        JPanel p1 = makePlayerPanelEntry(player1, p1sqc);
        JPanel p2 = makePlayerPanelEntry(player2, p2sqc);
        scorePanel.add(p1);
        scorePanel.add(p2);
        return scorePanel;
    }

    // A score panel for a GAME or MATCH.
    //
    public JPanel makeScorePanel(JTextField p1sqc, JTextField t1GCount, JProgressBar pb1,
                                 JTextField p2sqc, JTextField t2GCount, JProgressBar pb2) {
        Player
            player1 = controller.getPlayer(1),
            player2 = controller.getPlayer(2);
        
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
        JPanel p1 = makePlayerPanelEntry(player1, p1sqc, t1GCount, pb1);
        JPanel p2 = makePlayerPanelEntry(player2, p2sqc, t2GCount, pb2);
        scorePanel.add(p1);
        scorePanel.add(p2);
        return scorePanel;
    }

    // A player entry panel for INTERACTIVE mode.
    //
    public JPanel makePlayerPanelEntry(Player player, JTextField tf) {
        JPanel jp = new JPanel();
        String playerName = player.teamName();
        
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        
        jp.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel l = new JLabel(playerName);
        l.setForeground(player.getColor());
        
        l.setAlignmentX(Component.CENTER_ALIGNMENT);

        jp.add(l);
        jp.add(tf);
        
        return jp;
    }

    public JPanel makePlayerPanelEntry(Player player, JTextField tf, JTextField gc, JProgressBar pb) {
        JPanel jp = new JPanel();
        String playerName = player.teamName();
        
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        
        jp.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel l = new JLabel(playerName);
        l.setForeground(player.getColor());
        
        l.setAlignmentX(Component.CENTER_ALIGNMENT);

        jp.add(l);
        jp.add(tf);
        if(gc != null) jp.add(gc);
        jp.add(pb);
        
        return jp;
    }
}