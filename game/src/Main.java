/**
 * The Main class serves as the entry point for the aMaze project.
 * It initializes and displays all game components within the user interface.
 *
 * @author Buck Harris
 * @version 3.0
 * @since 2023-02-13
 */
import javax.swing.*;
import java.awt.*;
/**
 * This is the Main class that instantiates the
 * GameWindow and handles possible errors.
 */
public class Main 
{
 /**
 *	main creates a GameWindow object which allows the rest
 *	of the program to function.
 *	@param args standard parameter for main, allows console commands to be used.
 */
  public static void main(String[] args)
  {
    // This is the play area

    GameWindow game = new GameWindow("Group Foxtrot aMaze");
    

    game.setSize(new Dimension(900, 1000));

    game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    game.getContentPane().setBackground(Color.PINK);

    game.setUp("default.mze");
   
    // May or may not need this 
    game.setVisible(true);

    // You will HAVE to READ some documentation and catch exceptions so get used
    // to it. 

    try {
      // The 4 that are installed on Linux here
      // May have to test on Windows boxes to see what is there.  
      // And frankly, I do NOT care about what Macs may do. 
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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