package ui;

import util.Side;
import util.Line;
import util.LineC;
import controller.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;

public class Square extends JPanel {
    private HorizontalSide nSide, sSide;
    private VerticalSide   wSide, eSide;
    private Controller controller;
    
    public Square(Controller controller, int squareWidth, int row, int col) {
        this.controller = controller;
        setLayout(new BorderLayout());
        Line
            north = new LineC(row, col, Side.NORTH),
            west  = new LineC(row, col, Side.WEST),
            south = new LineC(row, col, Side.SOUTH),
            east  = new LineC(row, col, Side.EAST);
        
        this.sSide = new HorizontalSide(controller, squareWidth, south);
        add(this.sSide, BorderLayout.SOUTH);
        
        this.nSide = new HorizontalSide(controller, squareWidth, north);
        add(this.nSide, BorderLayout.NORTH);
           
        this.eSide = new VerticalSide(controller, squareWidth, east);
        add(this.eSide, BorderLayout.EAST);
        
        this.wSide = new VerticalSide(controller, squareWidth, west);
        add(this.wSide, BorderLayout.WEST);
    }

    public JButton getSide(Side side) {
        switch (side) {
            case NORTH: return this.nSide;
            case SOUTH: return this.sSide;
            case WEST:  return this.wSide;
            default:    return this.eSide;
        }
    }

    public void setColor(java.awt.Color color) {
        this.setBackground(color);
        this.repaint();
    }
}