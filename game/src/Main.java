/**
 * @author Kim Buckner
 * Date: Jan 13, 2019
 * Updated: Jan 12, 2023
 *
 *
 * A starting point for the COSC 3011 programming assignment
 * Probably need to fix a bunch of stuff, but this compiles and runs.
 *
 * This COULD be part of a package but I choose to make the starting point NOT a
 * package. However all other added elements should be sub-packages.
 *
 * Main should NEVER do much more than this in any program that is
 * user-interface intensive, such as this one. If I find that you have chosen
 * NOT to use Object-Oriented design methods, I will take huge deductions. 
 * 
 * 
 */

import javax.swing.*;
import java.awt.*;

public class Main 
{

  public static void main(String[] args)
  {
    // This is the play area
    // Initializes the game window and sets the name
    GameWindow game = new GameWindow("Group Foxtrot aMaze");
    
    // Sets the game window size to 900w by 1000h
    game.setSize(new Dimension(900, 1000));

    game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Sets the gameWindow's background color to pink.
    game.getContentPane().setBackground(Color.pink);
    game.setUp();
    
    // May or may not need this 
    game.setVisible(true);

    // You will HAVE to READ some documentation and catch exceptions so get used
    // to it. 

    try {
      // The 4 that are installed on Linux here
      // May have to test on Windows boxes to see what is there.  
      // And frankly, I do NOT care about what Macs may do. 
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
      // This is the "Java" or CrossPlatform version and the default
      //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      // Linux only
      //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
      // really old style Motif 
      //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
    } 
    catch (UnsupportedLookAndFeelException e) {
     // handle possible exception
    }
    catch (ClassNotFoundException e) {
     // handle possible exception
    }
    catch (InstantiationException e) {
     // handle possible exception
    }
    catch (IllegalAccessException e) {
     // handle possible exception
    }
  
  }
  
};
