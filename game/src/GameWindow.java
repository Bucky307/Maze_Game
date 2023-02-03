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
    GridBagLayout gbl = new GridBagLayout();
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
	basic.gridx=0;
	basic.gridy=1;
	basic.gridwidth=1;
	basic.gridheight=1;
	basic.ipadx = 100;
	basic.ipady = 100;
	basic.insets = new Insets(2,2,2,2);
	// This is really a constant in the GrdiBagConstraints. This way we 
	// don't need to know what type/value "BOTH" is
	    
	basic.fill=GridBagConstraints.BOTH;
	
	//Here I should create 16 -Elements- to put into my gameBoard
	//THE ELEMENTS CANNOT BE BUTTONS!!!!!!!!
	//I can also just arrange things as I like then have methods, or an
	//argument to the constructor that adds elements. 
 

	JPanel e11 = new JPanel();
	JPanel e21 = new JPanel();
	JPanel e31 = new JPanel();
	JPanel e41 = new JPanel();
	
	JPanel e12 = new JPanel();
	JPanel e22 = new JPanel();
	JPanel e32 = new JPanel();
	JPanel e42 = new JPanel();
	
	JPanel e13 = new JPanel();
	JPanel e23 = new JPanel();
	JPanel e33 = new JPanel();
	JPanel e43 = new JPanel();
	
	JPanel e14 = new JPanel();
	JPanel e24 = new JPanel();
	JPanel e34 = new JPanel();
	JPanel e44 = new JPanel();
	
	JPanel[][] board ={{e11,e21,e31,e41},
					  {e12,e22,e32,e42},	
					  {e13,e23,e33,e43},				
					  {e14,e24,e34,e44}};
	
	
	for (int row = 2; row <=5; row++)
	{
		for (int col = 1; col <=4; col++)
		{
			basic.gridx = col;
			basic.gridy = row;
			add(board[col-1][row-2], basic);
		}
	}
	
	
	
	JPanel p00 = new JPanel();
	JPanel p01 = new JPanel();
	JPanel p02 = new JPanel();
	JPanel p03 = new JPanel();	
	JPanel p04 = new JPanel();
	JPanel p05 = new JPanel();
	JPanel p06 = new JPanel();
	JPanel p07 = new JPanel();
	
	JPanel p10 = new JPanel();
	JPanel p11 = new JPanel();
	JPanel p12 = new JPanel();
	JPanel p13 = new JPanel();
	JPanel p14 = new JPanel();
	JPanel p15 = new JPanel();
	JPanel p16 = new JPanel();
	JPanel p17 = new JPanel();
	
	
	JPanel[][] pieces = {{p00,p01,p02,p03,p04,p05,p06,p07},
						 {p10,p11,p12,p13,p14,p15,p16,p17}};
			
		
	
	basic.insets = new Insets(2,100,2,100);
	for (int row = 0; row <=7; row++)
	{
		basic.gridx = 0;
		basic.gridy = row;
		add(pieces[0][row], basic);
		basic.gridx = 5;
		basic.gridy = row;
		add(pieces[1][row], basic);
	}
	

	// Now I add each one, modifying the default gridx/y and add
	// it along with the modified constraint
	    

	// And of course I have to add the buttons. Simple but not trivial.


	basic.gridx=1;
	basic.gridy=0;
	basic.ipadx = 10;
	basic.ipady = 10;
	basic.gridwidth = 4;
	basic.gridheight = 1;
	basic.insets = new Insets(2,2,2,2);
	add(this.addButtons(), basic);
	
	
	return;
  }

  /**
   * Used by setUp() to configure the buttons on a button bar and
   * add it to the gameBoard
   */

  public JPanel addButtons(){

    // Does nothing right now.
	JPanel panel = new JPanel();
	
    lbutton = new JButton("new");
	panel.add(lbutton);
    lbutton.addActionListener(this);
    lbutton.setActionCommand("new");
	
    mbutton = new JButton("reset");
	panel.add(mbutton);
    mbutton.addActionListener(this);
    mbutton.setActionCommand("reset");
	
    rbutton = new JButton("exit");
	panel.add(rbutton);
    rbutton.addActionListener(this);
    rbutton.setActionCommand("exit");


	// Add button to JPanel

    add(panel);

    
    //setVisible(true);
    //setDefaultCloseOperation(EXIT_ON_CLOSE);    
    return panel;
  }

};
