package ui;

import util.*;
import controller.*;
import players.*;
import players.player1.*;
import players.player2.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;

public class InteractiveControlPanel extends JPanel implements ControlPanel {
     
    private Controller controller;
    private DBG dbg;

    private JPanel buttonPanel, scorePanel;
    public JTextField team1SQCount, team2SQCount;

    public InteractiveControlPanel(Controller controller) {
        this.controller = controller;
        this.dbg = new DBG(DBG.UI, "IControlPanel");
        Util util = new Util(controller);

        this.team1SQCount = new JTextField("0", 8);

        if(dbg.debug)
            dbg.println("team1SQCount hashCode at creation = " + team1SQCount.hashCode());

        this.team2SQCount = new JTextField("0", 8);

        this.buttonPanel = makeButtonPanel();

        this.scorePanel =  util.makeScorePanel(team1SQCount, team2SQCount);

        setLayout(new GridLayout(2, 1));
        add(this.buttonPanel);
        add(this.scorePanel);
    }

    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton stepButton = new JButton("Step");

        stepButton.addActionListener(new StepListener());
        buttonPanel.add(stepButton);
        return buttonPanel;
    }
    
    public void actionPerformed(ActionEvent e) {
         dbg.println("stepButton just clicked in INTERACTIVE mode.");
        controller.stepResponder();
        }

    public void setButtonPanel(JPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }
    public void setScorePanel(JPanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    public void setGameScore(Score score) {

        if(dbg.debug) {
            dbg.println("setGameScore: attempting to set " + score);
            dbg.println("setGameScore: team1SQCount hashCode at setText = " + team1SQCount.hashCode());
        }
        Integer p1 = new Integer(score.getPlayer1());
        Integer p2 = new Integer(score.getPlayer2());
        
        team1SQCount.setText(p1.toString());
        team2SQCount.setText(p2.toString());
    }

    public void setMatchScore(Score gs) {}   // There is only one game.
    public int getGames() { return 0; }
    public void setProgress(Clock nothing) {}
   

    class StepListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            controller.stepResponder();
        }
    }
}

