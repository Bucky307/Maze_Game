/*
 * @author Buck Harris
 * Date: Feb 13, 2023
 * Updated: Feb 19, 2023
 *
 * The GameWindow for the aMaze project
 * Initializes all the components that will be part of the game
 * Also graphically displays all of them
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.nio.ByteBuffer;

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
 if("Reset".equals(e.getActionCommand()))  
  reset();
}

// Method to set up the game board
public void setUp()
{

// Below take the data from the .mze file
// and turns it into usable coordinates 
// that we will use to paint on top of the 
// tiles
File file = new File("input/default.mze");
FileInputStream inputStream = null;
float[][] lineCoords = null;
try 
{
 inputStream = new FileInputStream(file);

 // read number of tiles (an integer value)
 byte[] numTilesBytes = new byte[4];
 inputStream.read(numTilesBytes);
 int numTiles = ByteBuffer.wrap(numTilesBytes).getInt();
 System.out.println("Number of tiles: " + numTiles);

 //allocate memory for lineCoords array
 lineCoords = new float[numTiles][]; 

 // iterate over each tile
 for (int i = 0; i < numTiles; i++) 
 {
  // read tile number (an integer value)
  byte[] tileNumBytes = new byte[4];
  inputStream.read(tileNumBytes);
  int tileNum = ByteBuffer.wrap(tileNumBytes).getInt();

  // read number of lines on this tile (an integer value)
  byte[] numLinesBytes = new byte[4];
  inputStream.read(numLinesBytes);
  int numLines = ByteBuffer.wrap(numLinesBytes).getInt();

  lineCoords[i] = new float[numLines * 4]; // allocate memory for lineCoords on this tile

  // read each line on this tile (two pairs of floats)
  for (int j = 0; j < numLines; j++) 
  {
   byte[] lineBytes = new byte[16]; // each line contains 2 pairs of 4-byte floats
   inputStream.read(lineBytes);
   ByteBuffer.wrap(lineBytes).asFloatBuffer().get(lineCoords[i], j * 4, 4);

            
  }
 }
} catch (FileNotFoundException e) 
  {
   JOptionPane.showMessageDialog(null, "Error opening .mze file", "Error", JOptionPane.ERROR_MESSAGE);
   System.exit(0);
  } catch (IOException e) 
    {
     e.printStackTrace();
    } finally 
      {
       if (inputStream != null) 
       {
        try 
        {
         inputStream.close();
        } catch (IOException e) 
          {
           e.printStackTrace();
          }
       }
      }
 
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

 // Adds playboxes to the board
 basic.ipadx = 100;
 basic.ipady = 100;
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
 basic.insets = new Insets(5, 5, 5 ,5);
 for (int i = 0; i < 8; i++)
 {
  // Left Panel
  basic.gridy = i;
  tile[i] = new Tile(i, lineCoords[i]);
  LPanel.add(new playbox(0,i), basic);
  ((JPanel)LPanel.getComponent(i)).add(tile[i]); 
  ((JPanel)LPanel.getComponent(i)).setBorder(null);
   
  // Right Panel
  tile[i+8] = new Tile(i+8, lineCoords[i+8]);
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
  buttons[i].setPreferredSize(new Dimension(100, 100));
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
public void reset()
{
 for (int i = 0; i < 8; i++)
 {
  ((JPanel)LPanel.getComponent(i)).add(tile[i]); 
  ((JPanel)LPanel.getComponent(i)).setBorder(null);
  ((JPanel)RPanel.getComponent(i)).add(tile[i+8]); 
  ((JPanel)RPanel.getComponent(i)).setBorder(null);
  ((JPanel)grid.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.black));
  ((JPanel)grid.getComponent(i+8)).setBorder(BorderFactory.createLineBorder(Color.black));
 }
}

};