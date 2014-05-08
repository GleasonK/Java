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

public class MatchControlPanel extends JPanel implements ControlPanel {
    
    private Controller controller;
    private DBG dbg;

    private JPanel buttonPanel, scorePanel;
    private JTextField team1SQCount, team2SQCount;
    private JTextField t1GCount, t2GCount;
    private JTextField games;
    private JProgressBar team1Progress, team2Progress;
    
    private JSlider speedControl;
    
    public MatchControlPanel(Controller controller) {
        Player
            player1 = controller.getPlayer(1),
            player2 = controller.getPlayer(2);

        this.controller = controller;
        this.dbg = new DBG(DBG.UI, "MControlPanel");
        Util util = new Util(controller);

        this.team1SQCount = new JTextField("0", 10);
        this.team2SQCount = new JTextField("0", 10);

        this.t1GCount = new JTextField("0", 10);
        this.t2GCount = new JTextField("0", 10);
        
        team1Progress = new JProgressBar(0, (int) controller.getTimeout()); 
        team2Progress = new JProgressBar(0, (int) controller.getTimeout());

        this.buttonPanel = makeButtonPanel();
        this.scorePanel = util.makeScorePanel(team1SQCount, t1GCount, team1Progress,
                                              team2SQCount, t2GCount, team2Progress);

        setLayout(new BorderLayout());
        add(this.buttonPanel, BorderLayout.NORTH);
        add(this.scorePanel, BorderLayout.SOUTH);
    }

    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        JPanel subButtonPanel = new JPanel();
        subButtonPanel.setLayout(new FlowLayout());
        
        games = new JTextField(new Integer(util.Util.DEFAULT_GAMES).toString(), 10);
        
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new StartListener());
        
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new StopListener());
        
//        JButton stepButton = new JButton("Step");
//        stepButton.addActionListener(new StepListener());

        subButtonPanel.add(games);
        subButtonPanel.add(startButton, BorderLayout.WEST);
        subButtonPanel.add(stopButton, BorderLayout.CENTER);
//        subButtonPanel.add(stepButton, BorderLayout.EAST);

        buttonPanel.add(subButtonPanel, BorderLayout.NORTH);

        this.speedControl = new JSlider(JSlider.HORIZONTAL, 0, util.Util.SLIDER_MAX, 
                                        util.Util.SLIDER_MAX);
        speedControl.addChangeListener(new SpeedControlListener());

        buttonPanel.add(this.speedControl, BorderLayout.SOUTH);

        return buttonPanel;
    }

    public JPanel makePlayerPanel(String playerName, JTextField fc, JTextField gc) {
        JPanel outer = new JPanel();
        
        outer.setLayout(new GridLayout(2,1));
        
        outer.add(new JLabel(playerName, JLabel.CENTER));
        
        JPanel pe1 = makePlayerPanelEntry("Squares",fc);
        JPanel pe2 = makePlayerPanelEntry("Games",gc);
        
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(1,2));
        
        inner.add(pe1);
        inner.add(pe2);
        
        outer.add(inner);
        
        return outer;
    }
    
    public JPanel makePlayerPanelEntry(String fieldName, JTextField tf) {
        JPanel jp = new JPanel();
        
        jp.setLayout(new GridLayout(2,1));
        
        JLabel l = new JLabel(fieldName);
        
        jp.add(l);
        jp.add(tf);
        
        return jp;
    }

    public void setButtonPanel(JPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }
    public void setScorePanel(JPanel scorePanel) {
        this.scorePanel = scorePanel;
    }

    public void setGameScore(Score score) {
        Integer p1 = new Integer(score.getPlayer1());
        Integer p2 = new Integer(score.getPlayer2());
        
        team1SQCount.setText(p1.toString());
        team2SQCount.setText(p2.toString());
    }
    
    public void setProgress(Clock clock) {
        Player player = clock.getPlayer();
        JProgressBar pb = player.getId() == 1 ? team1Progress : team2Progress;
        long newValue = clock.elapsedTime();
        pb.setValue((int) newValue);
    }                       
     
    public int getGames() { return Integer.parseInt(games.getText()); }
 
    public void setMatchScore(Score games) {
        Integer p1 = new Integer(games.getPlayer1());
        Integer p2 = new Integer(games.getPlayer2());
        
        t1GCount.setText(p1.toString());
        t2GCount.setText(p2.toString());
    }

    class SpeedControlListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            int pace = util.Util.SLIDER_MAX - ((JSlider) e.getSource()).getValue();
            controller.getControlDisplay().setStatus("pace set to " + (util.Util.SLIDER_MAX - pace));
            controller.setPace(pace);
        }
    }
    class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("in action listener for Match start button.");
            controller.playMatch();
        }
    }
    class StopListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        controller.stopResponder();
        }
    }
//    class StepListener implements ActionListener {
  //      public void actionPerformed(ActionEvent e) {
    //    controller.stepResponder();
      //  }
    //}
}