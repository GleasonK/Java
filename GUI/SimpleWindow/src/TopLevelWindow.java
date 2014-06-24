/**
 * Created by GleasonK on 4/7/14.
 *
 */

import java.awt.*;
import javax.swing.*;

public class TopLevelWindow {

    private static void createWindow(){
        //Create and set up the JFrame that stops running on close
        JFrame frame = new JFrame("Simple GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JLabel can have image or text, Dimensions are set in pixles
        JLabel textLabel = new JLabel("I'm a label in a window!", SwingConstants.CENTER);
        textLabel.setPreferredSize(new Dimension(300,100));

        //Add textLabel to frame
        frame.add(textLabel, BorderLayout.CENTER);

        //Display Window in the CENTER of the screen
        frame.setLocationRelativeTo(null);

        //Pack the frame size into its elements and show the window
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args){
        createWindow();
    }
}
