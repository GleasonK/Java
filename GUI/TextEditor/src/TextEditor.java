//File: TextEditor.java
//Author: Kevin Gleason
//Date: Monday, April 7, 2014
//Use: Simple implementation of a text editor program

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;



public class TextEditor extends JFrame {
    //Instance Variables
    public JTextArea area = new JTextArea(40,115);
    private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
    private String currentFile = "Untitled";
    public boolean changed = false;
    public JToolBar northBar;

    public TextEditor() {
        //Set up text area space
        this.area.setName("Text Editor");
        this.area.setMargin(new Insets(10,10,10,10));
        this.area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scroll, BorderLayout.CENTER);

        //Make Menu bar
        JMenuBar JMB = new JMenuBar();
        this.setJMenuBar(JMB);
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu mode = new JMenu("Mode");
        JMB.add(file);  JMB.add(edit); JMB.add(mode);

        //Add to file menu then set all their icons to null
        file.add(New);  file.add(Open); file.add(Save);
        file.add(SaveAs); file.add(Quit);
        file.addSeparator();
        for(int i = 0; i < 4; i++) file.getItem(i).setIcon(null);

        //Add to edit menu
        edit.add(Cut); edit.add(Copy); edit.add(Paste);
        edit.getItem(0).setText("Cut");
        edit.getItem(1).setText("Copy");
        edit.getItem(2).setText("Paste");

        mode.add(TPHTML); mode.add(TxtEdit);

        //Make toolbar for top of page
        JToolBar tool = new JToolBar();
        add(tool, BorderLayout.NORTH);
        tool.add(New);  tool.add(Open);  tool.add(Save);
        tool.addSeparator();

        //Add toolbar Buttons
        JButton
                cut = tool.add(Cut),
                cop = tool.add(Copy),
                paste = tool.add(Paste),
                sel = tool.add(SelAll),
                lin = tool.add(SelLin),
                del = tool.add(Del);
        cut.setText(null);
        cut.setIcon(new ImageIcon("icons/cut.gif"));
        cop.setText(null);
        cop.setIcon(new ImageIcon("icons/copy.gif"));
        paste.setText(null);
        paste.setIcon(new ImageIcon("icons/paste.gif"));
        sel.setText(null);
        sel.setIcon(new ImageIcon("icons/select.gif"));
        lin.setText(null);
        lin.setIcon(new ImageIcon("icons/line.gif"));
        del.setText(null);
        del.setIcon(new ImageIcon("icons/delete.gif"));
        Save.setEnabled(false);
        SaveAs.setEnabled(false);
        this.northBar = tool;

        //frame defaults
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        area.addKeyListener(k1);
        this.setTitle(this.currentFile);
        this.setVisible(true);

    }

    private KeyListener k1 = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            change();
        }
    };

    Action New = new AbstractAction("New", new ImageIcon("icons/new.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveOld();
            new TextEditor();
        }
    };

    Action Open = new AbstractAction("Open", new ImageIcon("icons/open.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveOld();
            if(dialog.showOpenDialog(null)  == JFileChooser.APPROVE_OPTION)
                readInFile(dialog.getSelectedFile().getAbsolutePath());
            SaveAs.setEnabled(true);
        }
    };

    Action Save = new AbstractAction("Save", new ImageIcon("icons/save.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!currentFile.equals("Untitled")) saveFile(currentFile);
            else saveFileAs();

        }
    };

    Action SaveAs = new AbstractAction("Save as...", new ImageIcon("saveAs.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveFileAs();
        }
    };

    Action Quit = new AbstractAction("Quit") {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveOld();
            System.exit(0);
        }
    };

    ActionMap aMap = area.getActionMap();
        Action Cut = aMap.get(DefaultEditorKit.cutAction);
        Action Copy = aMap.get(DefaultEditorKit.copyAction);
        Action Paste = aMap.get(DefaultEditorKit.pasteAction);
        Action SelAll = aMap.get(DefaultEditorKit.selectAllAction);
        Action SelLin = aMap.get(DefaultEditorKit.selectLineAction);
        Action Del = aMap.get(DefaultEditorKit.deletePrevCharAction);

    //Opens a new Text Edit
    Action TxtEdit = new AbstractAction("Text Editor") {
        @Override
        public void actionPerformed(ActionEvent e) {
            new TextEditor();
        }
    };

    //Creates new TouchPad HTML class instance
    Action TPHTML = new AbstractAction("TouchPad HTML") {
        @Override
        public void actionPerformed(ActionEvent e) {
            new TouchPadHTML();
        }
    };

    private void saveFileAs(){
        if(dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            saveFile(dialog.getSelectedFile().getAbsolutePath());
    }

    private void saveOld() {
        if (this.changed) {
            if (JOptionPane.showConfirmDialog(this, "Would you like to write to " +
                    this.currentFile + " ?", "Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) ;
            saveFile(this.currentFile);
        }
    }

    private void readInFile(String fin){
        try {
            FileReader r = new FileReader(fin);
            this.area.read(r, null);
            r.close();
            this.currentFile = fin;
            this.setName(this.currentFile);
            this.changed = false;
        }
        catch (IOException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Editor cannot find file named - " + fin);
        }
    }

    private void saveFile(String fin) {
        try{
            FileWriter w = new FileWriter(fin);
            this.area.write(w);
            w.close();
            this.currentFile = fin;
            this.setTitle(fin);
            this.changed = false;
            Save.setEnabled(false);
        }
        catch (IOException e){
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Error - Could not write to " + fin);
        }
    }

    public void change() {
        this.changed = true;
        this.Save.setEnabled(true);
        this.SaveAs.setEnabled(true);
    }

    public static void main(String[] args) {
        new TextEditor();
    }

}
