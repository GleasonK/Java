package ui;

import controller.*;
import util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;


public class VerticalSide extends JButton implements ActionListener {
    private Line side;
    Controller controller;
    
    public VerticalSide(Controller c, int squareWidth, Line side) {
        this.controller = c;
        this.side = side;
        int 
            barHeight = squareWidth,
            barWidth = (int) (.05 * barHeight);
        
        Dimension size = getPreferredSize();
        size.width = barWidth;
        size.height = barHeight; 
        setPreferredSize(size);
        
        setContentAreaFilled(false);
        addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        this.controller.makeHumanPlay(this.side);
        setSelected(true);
    }
}
