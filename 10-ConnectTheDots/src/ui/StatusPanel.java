package ui;

import util.*;
import controller.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;

public class StatusPanel extends JPanel {
    
    private Controller controller;
    private DBG dbg;
    private JTextField statusField;

    public StatusPanel(Controller controller) {
        this.controller = controller;
        this.dbg = new DBG(DBG.UI, "StatusPanel");

        this.statusField = new JTextField(38);  // Size is 38 ??

        setLayout(new BorderLayout());
        add(this.statusField, BorderLayout.SOUTH);

        setBackground(new Color(175, 175, 175));
    }

    public Dimension getPreferredSize() {
        return new Dimension(20, 0);
    }

    public void setStatus(String status) {
        this.statusField.setText(status);
    }
}
