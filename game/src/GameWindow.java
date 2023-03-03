/*
 * @author Buck Harris
 * Date: Feb 13, 2023
 * Updated: Feb 19, 2023
 *
 * The GameWindow for the aMaze project
 * Holds all the components that will be part of the game
 * Also graphically displays all of them
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame implements ActionListener
{
//Serializes game window. If this isn't included, javac complains
public static final long serialVersionUID=1;

public JButton lbutton, rbutton, mbutton; 
private static JPanel grid, LPanel, RPanel;
private static Tile[] tile;
private static Tile lastTileClicked = null;

/*
 * Constructor for GameWindow
 * Takes a string to set the window name
 */
public GameWindow(String s)
{
 super(s);
 GridBagLayout gbl = new GridBagLayout();
 setLayout(gbl);
}

// Uses actionListener to call button functions
public void actionPerformed(ActionEvent e) 
{
 if("Quit".equals(e.getActionCommand()))  
 System.exit(0);
}

// Method to set up the game board
public void setUp()
{
/* Initializing the class variables
 * Creates a grid for the play area
 * Creates the left and right panels
 * Creates an array of 16 tiles
 */
 GridBagConstraints basic = new GridBagConstraints();
 grid = new JPanel(new GridBagLayout());
 LPanel = new JPanel(new GridBagLayout());
 RPanel = new JPanel(new GridBagLayout());
 tile = new Tile[16];

 // Generates each tile and gives it a number
 for (int i = 0; i < 16; i++)
 {
  tile[i] = new Tile(i);
 }
  
 // Adds playboxes to the board
 basic.ipadx = 75;
 basic.ipady = 75;
 basic.insets = new Insets(0, 0, 0, 0);
 for (int i = 0; i < 4; i ++)
 {
  basic.gridx = i;
  for (int j = 0; j < 4; j++)
  {
   basic.gridy = j;
   grid.add(new playbox(i,j), basic);
  }
 }
  
 // Adds the playboxes and tiles to L&R Panels
 basic.gridx = 0;
 basic.insets = new Insets(10, 10, 10 ,10);
 for (int i = 0; i < 8; i++)
 {
  // Left Panel
  basic.gridy = i;
  tile[i] = new Tile(i);
  LPanel.add(new playbox(0,i), basic);
  ((JPanel)LPanel.getComponent(i)).add(tile[i]); 
  ((JPanel)LPanel.getComponent(i)).setBorder(null);
   
  // Right Panel
  tile[i+8] = new Tile(i+8);
  RPanel.add(new playbox(0,i), basic);
  ((JPanel)RPanel.getComponent(i)).add(tile[i+8]);
  ((JPanel)RPanel.getComponent(i)).setBorder(null);
 }

 // Adds grid to the board
 basic.ipadx = 0;
 basic.ipady = 0; 
 basic.gridx = 1;
 basic.gridy = 1;
 basic.insets = new Insets(75, 10, 10 ,10);
 add(grid, basic);
  
 // Adds the L&R Panels
 basic.ipadx = 1;
 basic.ipady = 1;
 basic.insets = new Insets(1,50,1,50);
 basic.gridx = 0;
 basic.gridy = 0;
 basic.gridheight = 8;
 add(LPanel,basic);
 basic.gridx = 2;
 basic.gridy = 0;
 add(RPanel, basic);
  
 // Creates space for buttons and adds them
 basic.gridx=1;
 basic.gridy=0;
 basic.ipadx = 0;
 basic.ipady = 0;
 basic.gridwidth = 1;
 basic.gridheight = 1;
 basic.insets = new Insets(10,10,10,10);
 add(this.addButtons(), basic);
  
 return;
 
}

// Function to generate the buttons
public JPanel addButtons()
{
 JPanel ButtonPanel = new JPanel();
 JButton[] buttons = {lbutton, mbutton, rbutton};
 String[] action = {"New Game", "Reset", "Quit"};

 /* Goes through array of button names to name each,
  * gives each a command, and gives it an actionListener
  */
 for (int i = 0; i < 3; i++)
 {
  buttons[i] = new JButton(action[i]);
  buttons[i].setBorder(BorderFactory.createLineBorder(Color.black));
  buttons[i].setPreferredSize(new Dimension(100, 75));
  buttons[i].setFont(new Font("Arial", Font.PLAIN, 18));
  ButtonPanel.add(buttons[i]);
  buttons[i].addActionListener(this);
  buttons[i].setActionCommand(action[i]);
 }
 return ButtonPanel;
}

// Sets lastTileClicked when a tile is clicked
public static void tileClick(Tile tile)
{	 
 if (lastTileClicked != null)
  // Returns tile clicked before to its original color
  lastTileClicked.setBackground(new Color(175,175,175));

 // Changes the color of the most recently clicked tile
 lastTileClicked = tile;
 lastTileClicked.setBackground(new Color(100, 100, 100));
}

// Moves the tile to the playbox that was clicked
public static void playboxClick(playbox pbox) 
{
 if (lastTileClicked != null) 
 {
  Container parent = lastTileClicked.getParent();
   if (parent instanceof playbox) 
   {
    //Adds black border to spot tile used to be in
    ((playbox) parent).setBorder(BorderFactory.createLineBorder(Color.BLACK));
    ((playbox) parent).remove(lastTileClicked);
   }
  
  //Adds the clicked tile to the layered Jpanel and removes border
  pbox.add(lastTileClicked);
  pbox.setBorder(null);
  //Resets the tile's background color
  lastTileClicked.setBackground(new Color(175,175,175));
  //Resets the tile clicked instance
  lastTileClicked = null;
  //Redraws or updates all the grids
  grid.repaint();
  LPanel.repaint();
  RPanel.repaint();
 }
}

};
