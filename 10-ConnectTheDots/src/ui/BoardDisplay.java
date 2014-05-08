package ui;

import controller.*;
import util.Side;
import util.Line;
import util.LineC;
import util.DBG;
import util.Util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.util.*;

public class BoardDisplay extends JPanel {

    private int displaySide;
    Controller controller;
    private Square[][] squares;    // NB: This version of Square is different 
                                   //     than the one in the board package!
    private DBG dbg;

    public BoardDisplay(Controller controller, int displaySide) {
        this.controller = controller;
        this.displaySide = displaySide;

        controller.setBoardDisplay(this);

        this.dbg = new DBG(DBG.UI, "BoardDisplay");
        
        // NB: ui.Square is different than board.Square!!
        //
        this.squares = new Square[Util.N][Util.N];
        
        setLayout(new GridLayout(Util.N, Util.N));
        
        int squareSide = (int) (displaySide * (1.0 - 2 * .05) / Util.N);
        
        for(int row = 0; row < Util.N; row++) {
            for(int col = 0; col < Util.N; col++) {
                Square sq = new Square(controller, squareSide, row, col);
                this.squares[row][col] = sq;
                add(sq);
            }
        } 
    }
    
    public void color(int row, int col, Color color) {
        Square square = this.squares[row][col];
        square.setBackground(color);
    }
    
    public void selectLine(Line line) {
        doSelectLine(line, true);
    }
    
    // This function is called as part of a reset after a new mode
    // selection by the UI.
    //
    public void deselectLine(Line line) {
        doSelectLine(line, false);
    }
    
    public void doSelectLine(Line line, boolean status) {
        int  row  = line.getRow(),
             col  = line.getCol();
        Side side = line.getSide();

        if(dbg.debug) dbg.println("selectLine: first col=" + col);

        if(dbg.debug) dbg.println("selectLine: line=" + line);
        
        Square square = this.squares[row][col];

        square.getSide(side).setSelected(status);

        switch(side) {
        case NORTH : 
            if (row > 0)
                squares[row - 1][col].getSide(Side.SOUTH).setSelected(status);
            return;
        case WEST :
            if (col > 0)
                squares[row][col - 1].getSide(Side.EAST).setSelected(status);
            return;
        case SOUTH :
            if (row < Util.N - 1)
                squares[row + 1][col].getSide(Side.NORTH).setSelected(status);
            return;
        default:
            if (col < Util.N - 1)
                squares[row][col + 1].getSide(Side.WEST).setSelected(status);            
        }
    }

    public void colorSquare(board.Square square, java.awt.Color color) {
        int row = square.getRow(),
            col = square.getCol();
        Square uiSquare = this.squares[row][col];
        uiSquare.setColor(color);
        uiSquare.revalidate();
        this.revalidate();
    }

    public void reset() {
        for(int row = 0; row < Util.N; row++) {
            for(int col = 0; col < Util.N; col++) {
                Square uiSquare = this.squares[row][col];
                uiSquare.setColor(Util.NOCOLOR);
                deselectLine(new LineC(row, col, Side.NORTH));
                deselectLine(new LineC(row, col, Side.WEST));
                deselectLine(new LineC(row, col, Side.SOUTH));
                deselectLine(new LineC(row, col, Side.EAST));
            }
        }
    }
    public Dimension getPreferredSize() { return new Dimension(this.displaySide, 0); }
}