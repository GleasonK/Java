import util.*;
import controller.*;
import ui.BoardDisplay;
import ui.ControlDisplay;
import board.*;
import players.*;
import javax.swing.*;
import java.awt.*;

public class Squares {

    public static void addComponentsToPane(Container pane, Controller controller, int height) {
            
        // Provide the present ContentPane to the controller so that it
        // can replace the ControlDisplay when a mode switch occurs.
        //
        controller.setContentPane(pane);
        
        BoardDisplay   bd = new BoardDisplay(controller, height);
        ControlDisplay cd = new ControlDisplay(controller, height);

        pane.add(bd, BorderLayout.WEST);
        pane.add(cd, BorderLayout.EAST);
    }

    private static void createAndShowGUI() {         
        Controller controller = new ControllerC(Mode.GAME);
        //Create and set up the window.
        JFrame frame = new JFrame("CS102 Squares Tournament");
        int height = Util.DEFAULT_DISPLAY_HEIGHT,
            width =  (int) (height * Util.ASPECT_RATIO);
        
        frame.setSize(width, height);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Set up the content pane.
        //
        addComponentsToPane(frame.getContentPane(), controller, height);

        //Display the window.
        //
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        
        // As of now, one optional command line argument: an integer N
        // specifying the board dimensions. The default is 4.
        //
        //if (args.length > 0)
        Util.N = Integer.parseInt("8");//args[0]);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {                    
                createAndShowGUI();
            }
        });
    }
}