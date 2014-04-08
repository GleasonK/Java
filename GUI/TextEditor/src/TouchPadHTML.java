//File: TouchPadHTML.java
//Author: Kevin Gleason
//Date: Monday, April 7, 2014
//Use: Simple implementation of a TouchPad HTML editor

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.Deque;
import java.util.ArrayDeque;

public class TouchPadHTML {
    //Instance Variables
    private JToolBar HTMLe;
    private JToolBar HTMLs;
    private TextEditor TE;
    private TPTags tags;

    public TouchPadHTML(){ //int w, int h) {
        this.TE = new TextEditor(); //w, h);
        this.TE.setName("TouchPad HTML");
        this.HTMLe = this.makeEast();
        this.HTMLs = this.makeSouth();
        this.TE.add(this.HTMLe, BorderLayout.WEST);
        this.TE.add(this.HTMLs, BorderLayout.EAST);
        this.tags = new TPTagsC(0);

        //Add New Line button
        fixToolBarNorth();

    }

    private void fixToolBarNorth(){
        JButton nl = this.TE.northBar.add(newLine);
        nl.setText(null);
        nl.setIcon(new ImageIcon("icons/newline.gif"));
    }

    private JToolBar makeEast() {
        JToolBar HTMLbarE = new JToolBar("TouchPad HTML",SwingConstants.VERTICAL);

        //Add toolbar Buttons
        JButton
                html = HTMLbarE.add(HTMLact),
                body = HTMLbarE.add(bodyAct),
                div  = HTMLbarE.add(divAct),
                h1   = HTMLbarE.add(h1Act),
                p    = HTMLbarE.add(pAct),
                a    = HTMLbarE.add(aAct),
                img = HTMLbarE.add(imgAct);
        HTMLbarE.addSeparator();
        JButton close = HTMLbarE.add(closeAct);

        //Configure Buttons
        html.setText(null);

        html.setIcon(new ImageIcon("HTMLi/html.gif"));
        body.setText(null);
        body.setIcon(new ImageIcon("HTMLi/body.gif"));
        div.setText(null);
        div.setIcon(new ImageIcon("HTMLi/div.gif"));
        h1.setText(null);
        h1.setIcon(new ImageIcon("HTMLi/h1.gif"));
        p.setText(null);
        p.setIcon(new ImageIcon("HTMLi/p.gif"));
        a.setText(null);
        a.setIcon(new ImageIcon("HTMLi/a.gif"));
        img.setText(null);
        img.setIcon(new ImageIcon("HTMLi/img.gif"));
        close.setText(null);
        close.setIcon(new ImageIcon("HTMLi/close.gif"));
        closeAct.setEnabled(false);
        return HTMLbarE;
    }

    private JToolBar makeSouth() {
        JToolBar HTMLbarS = new JToolBar("TouchPad HTML",SwingConstants.VERTICAL);

        //Add toolbar Buttons
        JButton
                form = HTMLbarS.add(formAct),
                input = HTMLbarS.add(inputAct),
                ul = HTMLbarS.add(ulAct),
                li = HTMLbarS.add(liAct),
                b = HTMLbarS.add(bAct),
                i = HTMLbarS.add(iAct),
                br = HTMLbarS.add(brAct),
                close = HTMLbarS.add(closeAct);
        HTMLbarS.addSeparator();

        //Configure Buttons
        form.setText(null);
        form.setIcon(new ImageIcon("HTMLi/form.gif"));
        input.setText(null);
        input.setIcon(new ImageIcon("HTMLi/input.gif"));
        ul.setText(null);
        ul.setIcon(new ImageIcon("HTMLi/ul.gif"));
        li.setText(null);
        li.setIcon(new ImageIcon("HTMLi/li.gif"));
        b.setText(null);
        b.setIcon(new ImageIcon("HTMLi/b.gif"));
        i.setText(null);
        i.setIcon(new ImageIcon("HTMLi/i.gif"));
        br.setText(null);
        br.setIcon(new ImageIcon("HTMLi/br.gif"));
        close.setText(null);
        close.setIcon(new ImageIcon("HTMLi/close.gif"));
        closeAct.setEnabled(false);
        return HTMLbarS;
    }



    Action HTMLact = new AbstractAction("HTML", new ImageIcon("HTMLi/html.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append("<html>\n<head><title>TITLE</title></head>\n");
            //TE.area.setCaretPosition(TE.area.getSelectionEnd());
            HTMLact.setEnabled(false);
            tags.push("</html>");
            closeAct.setEnabled(true);
            TE.change();

        }
    };

    Action bodyAct = new AbstractAction("HTML", new ImageIcon("HTMLi/body.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append("<body>\n");
            tags.push("</body>");
            closeAct.setEnabled(true);
            bodyAct.setEnabled(false);
            TE.change();

        }
    };

    Action divAct = new AbstractAction("DIV", new ImageIcon("HTMLi/div.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.getBuffer() + "<div class = \"\">\n");
            tags.push("</div>");
            closeAct.setEnabled(true);
            TE.change();
        }
    };

    Action h1Act = new AbstractAction("H1", new ImageIcon("HTMLi/h1.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.getBuffer() + "<h1>");
            tags.push("</h1>");
            closeAct.setEnabled(true);
            TE.change();
        }
    };

    Action aAct = new AbstractAction("A", new ImageIcon("HTMLi/h1.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.getBuffer() + "<a href = \"LINK\">");
            tags.push("</a>");
            closeAct.setEnabled(true);
            TE.change();
        }
    };

    Action pAct = new AbstractAction("P", new ImageIcon("HTMLi/p.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.getBuffer() + "<p>");
            tags.push("</p>");
            closeAct.setEnabled(true);
            TE.change();

        }
    };

    Action imgAct = new AbstractAction("IMG", new ImageIcon("HTMLi/img.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.getBuffer() + "<img src = \"SOURCE\"/>\n");
            TE.change();
        }
    };

    Action closeAct = new AbstractAction("CLOSE", new ImageIcon("HTMLi/close.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.rip());
            TE.change();
            if (tags.isEmpty()) closeAct.setEnabled(false);
        }
    };

    Action formAct = new AbstractAction("FORM", new ImageIcon("HTMLi/form.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.getBuffer() + "<form action = \"ACTION\">\n");
            tags.push("</form>");
            closeAct.setEnabled(true);
            TE.change();
        }
    };
    Action inputAct = new AbstractAction("INPUT", new ImageIcon("HTMLi/input.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.getBuffer() + "<input action = \"TYPE\" value = \"VALUE\" />\n");
            closeAct.setEnabled(true);
            TE.change();
        }
    };

    Action ulAct = new AbstractAction("UL", new ImageIcon("HTMLi/ul.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.getBuffer() + "<ul>\n");
            tags.push("</ul>");
            closeAct.setEnabled(true);
            TE.change();
        }
    };

    Action liAct = new AbstractAction("LI", new ImageIcon("HTMLi/li.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.getBuffer() + "<li>LIST_ELEMENT");
            tags.push("</li>");
            closeAct.setEnabled(true);
            TE.change();
        }
    };

    Action bAct = new AbstractAction("B", new ImageIcon("HTMLi/b.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.getBuffer() + "<b>TEXT");
            tags.push("</b>");
            closeAct.setEnabled(true);
            TE.change();
        }
    };

    Action iAct = new AbstractAction("I", new ImageIcon("HTMLi/i.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.getBuffer() + "<i>TEXT");
            tags.push("</i>");
            closeAct.setEnabled(true);
            TE.change();
        }
    };

    Action brAct = new AbstractAction("BR", new ImageIcon("HTMLi/br.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append(tags.getBuffer() + "<br/>\n");
            closeAct.setEnabled(true);
            TE.change();
        }
    };

    Action newLine = new AbstractAction("New Line", new ImageIcon("HTMLi/newline.gif")) {
        @Override
        public void actionPerformed(ActionEvent e) {
            TE.area.append("\n" + tags.getBuffer());
        }
    };

    public static void main(String[] args) {
        new TouchPadHTML();
    }


    //Make toolbar for top of page

}
