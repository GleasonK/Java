package ui;

import util.*;
import controller.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;

public class ModePanel extends JPanel implements ActionListener {
    
    private Controller controller;
    private Mode mode;
    private DBG dbg;
    private Color color;

    private static int modeLabelIndexHack(Mode mode) {
        if (mode == Mode.INTERACTIVE)
            return 0;
        else if (mode == Mode.GAME)
            return 1;
        else
            return 2;
    }
    public ModePanel(Controller controller, Mode mode) {
        this.controller = controller;
        this.mode = mode;
        this.dbg = new DBG(DBG.UI, "ModePanel");
        this.color = new Color(200, 200, 200);

        this.setBackground(color);
        String[] modeStrings = { "Interactive", "Game", "Match" };
 
        JComboBox modeList = new JComboBox(modeStrings);
        
        modeList.setSelectedIndex(modeLabelIndexHack(mode));

        modeList.setAlignmentY(Component.CENTER_ALIGNMENT);
        setLayout(new FlowLayout()); 
        add(modeList);

        modeList.addActionListener(this);
    }

    // This is the actionListener for the ComboBox.
    //
    public void actionPerformed(ActionEvent e) {

        JComboBox cb = (JComboBox)e.getSource();
        String modeName = (String)cb.getSelectedItem();

        if(modeName.equals("Interactive"))
            controller.reset(Mode.INTERACTIVE);
        else if (modeName.equals("Game"))
            controller.reset(Mode.GAME);
        else 
            controller.reset(Mode.MATCH);
    }
}
