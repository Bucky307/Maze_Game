/**
 * @author Kim Buckner
 * Date: Jan 13, 2019
 * Updated: Jan 12, 2023
 *
 * This is the actual "game". Will have to make some major changes.
 * This is just a "hollow" shell.
 *
 * When you get done, I should see the buttons at the top in the "play" area
 * (NOT a pull-down menu). The only one that should do anything is Quit.
 *
 * Should also see something that shows where the 4x4 board and the "spare"
 * tiles will be when we get them stuffed in.
 *
 * This COULD be part of a package but I choose to make the starting point NOT a
 * package. However all other added elements should certainly sub-packages.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame implements ActionListener
{
  /**
   * because it is a serializable object, need this or javac
   * complains <b>a lot</b>, the ID can be any integer.
   */
  public static final long serialVersionUID=1;

  /*
   * This is so I can try changing the starting point easily. Can certainly be
   * left out altogether. 
   */
  private int startAt=1;

  /**
   *  The control  buttons that will part of the Game Window
   *  left, right, middle.
   */
  public JButton lbutton, rbutton, mbutton;


  /**
   * Constructor sets the window name using super(), changes the layout,
   * which you really need to read up on, and maybe you can see why I chose
   * this one.
   *
   * If you don't want to use this layout, feel free to change it. Or get rid of
   * it altogether. You might also want to look at things like "layered"
   * panes/frames and using an InnerFrame. It is all up to you. 
   *
   * @param s string which is the name of the window. 
   */

  public GameWindow(String s)
  {
    super(s);
    GridBagLayout gbl=new GridBagLayout();
    setLayout(gbl);
  }

  /**
   * For the buttons
   * @param e is the ActionEvent
   * 
   * BTW you can ask the event for the name of the object generating event.
   * The odd syntax for non-java people is that "exit" for instance is
   * converted to a String object, then that object's equals() method is
   * called.
   */

  public void actionPerformed(ActionEvent e) {
    if("exit".equals(e.getActionCommand()))
      System.exit(0);
    if("reset".equals(e.getActionCommand()))
      System.out.println("reset pressed\n");
    if("new".equals(e.getActionCommand()))
      System.out.println("new pressed\n");
    }

  /**
   *  Establishes the inital board
   */

  public void setUp()
  {
    // actually create the array for elements, make sure it is big enough
    
    // Need to play around with the dimensions and the gridx/y values
    // These constraints are going to be added to the pieces/parts I 
    // stuff into the "GridBag".
    // YOU CAN USE any type of constraints you like. Just make it work.
    
    GridBagConstraints basic = new GridBagConstraints();
    basic.gridx=startAt;
    basic.gridy=0;
    basic.gridwidth=1;
    basic.gridheight=1;

    // This is really a constant in the GrdiBagConstraints. This way we 
    // don't need to know what type/value "BOTH" is
    
    basic.fill=GridBagConstraints.BOTH;

    //Here I should create 16 -Elements- to put into my gameBoard
    //THE ELEMENTS CANNOT BE BUTTONS!!!!!!!!
    //I can also just arrange things as I like then have methods, or an
    //argument to the constructor that adds elements. 




    // Now I add each one, modifying the default gridx/y and add
    // it along with the modified constraint
    




    // And of course I have to add the buttons. Simple but not trivial.
    this.addButtons();
    return;
    
    
  }

  /**
   * Used by setUp() to configure the buttons on a button bar and
   * add it to the gameBoard
   */

  public void addButtons(){

    // Does nothing right now.

    return;
  }

};
