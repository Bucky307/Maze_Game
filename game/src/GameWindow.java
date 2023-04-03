
/**
* GameWindow class is the primary class for the aMaze project.
 * It initializes all the components that will be part of the game and
 * graphically displays them.
 *
 * @author Buck Harris
 * @version 3.0
 * @since 2023-02-13
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.Timer;

/**
 * GameWindow class serves as the main container for the game grid and its components.
 * It is responsible for coordinating the interaction between playboxes and tiles, 
 * as well as managing the overall game state and user input through mouse events.
 */
public class GameWindow extends JFrame implements ActionListener, MouseListener
{

/**
 * Holds all 16 tiles
 */
private static Tile[] tile;
private JButton lbutton, rbutton, mbutton; 
private static JPanel grid, LPanel, RPanel;
private static  ArrayList<Integer> tileIndices;
private static float[][] lineCoords = null;


/**
 * Holds 0 or 1 if there is a tile in that grid
 * space.
 */
public static int[][] gridData = new int[4][4];
/**
 * Holds each playbox in the grid
 */
public static playbox[][] pboxArr = new playbox[4][4];
/**
 * Holds the tile that was clicked last
 */
public static Tile lastTileClicked = null;
/**
 * Needed for the program
 */
public static final long serialVersionUID=1;
/**
 * Constructor for GameWindow.
 * Takes a string to set the window name.
 *
 * @param s The string which is passed into the method.
 */
public GameWindow(String s)
{
 super(s);
 super.addMouseListener(this);
 GridBagLayout gbl = new GridBagLayout();
 setLayout(gbl);
}

/**
 * This method uses actionListener to call button functions.
 *
 * @param e the class which is used to return a result of a button action.
 */
public void actionPerformed(ActionEvent e) 
{
 if("Quit".equals(e.getActionCommand()))  
  System.exit(0);
 if("Reset".equals(e.getActionCommand()))  
  reset();
 if("New".equals(e.getActionCommand()))
  ;
}
/**
 * This method sets up the game board.
 * It takes the data from the .mze file and turns it into usable coordinates
 * that are used to paint on top of the tile objects.
 */
public void setUp()
{
 GridBagConstraints basic = new GridBagConstraints();
 
 fileSetup();
 gridAndPanelSetup();
 addPlayboxesToGrid(basic);
 int[] rotations = generateRandomRotations();
 addTilesToPanels(basic, rotations);
 addGridAndLRPanels(basic);
 addButtons(basic);
 reset();
 return;
}

/**
 * Sets up the file input and reads the data from the .mze file,
 * converting it into usable coordinates to paint on top of the tiles.
 */
private void fileSetup()

{
 // Below take the data from the .mze file
 // and turns it into usable coordinates 
 // that we will use to paint on top of the 
 // tiles.
 File file = new File("input/default.mze");
 FileInputStream inputStream = null;
 try 
 {
  inputStream = new FileInputStream(file);

  // read number of tiles (an integer value)
  byte[] numTilesBytes = new byte[4];
  inputStream.read(numTilesBytes);
  int numTiles = ByteBuffer.wrap(numTilesBytes).getInt();

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
 } 
 ///
 /// This catch block stops the program if the maze file is missing
 ///	or inaccessible.
 /// @throws FileNotFoundException if the expected .mze file
 /// cannot be reached
 ///

 catch (FileNotFoundException e) 
 {
  JOptionPane.showMessageDialog(null, "Error opening .mze file", "Error", JOptionPane.ERROR_MESSAGE);
  System.exit(0);
 }
 catch (IOException e) 
 {
  e.printStackTrace();
 } 
 finally  
 {
  if (inputStream != null) 
  {
   try 
   {
	inputStream.close();
   }
   catch (IOException e) 
   {
	e.printStackTrace();
   }
  }
 }	 
}

/**
 * Sets up the grid and panels, creating an array of Tile objects.
 */
private void gridAndPanelSetup()
{
 grid = new JPanel(new GridBagLayout());
 LPanel = new JPanel(new GridBagLayout());
 RPanel = new JPanel(new GridBagLayout());
 grid.addMouseListener(this);
 LPanel.addMouseListener(this);
 RPanel.addMouseListener(this);
 tile = new Tile[16];
}

/**
 * Adds playboxes to the grid and initializes the gridData and pboxArr arrays.
 * @param basic The GridBagConstraints object for setting up the grid layout
 */
private void addPlayboxesToGrid(GridBagConstraints basic) 
{
 basic.ipadx = 100;
 basic.ipady = 100;
 basic.insets = new Insets(0, 0, 0, 0);
 for (int i = 0; i < 4; i++) 
 {
  basic.gridx = i;
  for (int j = 0; j < 4; j++) 
  {
   basic.gridy = j;
   gridData[j][i] = 0;
   pboxArr[j][i] = new playbox(j, i);
   grid.add(pboxArr[j][i], basic);
  }
 }
}

/**
 * Generates an array of random rotations for the tiles.
 * @return An array of 16 integers representing the rotations of the tiles
 */
private int[] generateRandomRotations() 
{
 int[] rotations = new int[16];
 int zeroCount = 0;

 // Ensure at least one tile gets each of the three rotations
 rotations[0] = 1;
 rotations[1] = 2;
 rotations[2] = 3;

 for (int i = 3; i < 16; i++) 
 {
  int randomRotation = (int) (Math.random() * 4);

  if (randomRotation == 0 && zeroCount < 4) 
  {
   rotations[i] = randomRotation;
   zeroCount++;
  }
  else if (randomRotation != 0) 
  {
   rotations[i] = randomRotation;
  } 
  else i--;       
 }

 // Shuffle the rotations to distribute them randomly among the tiles
 Collections.shuffle(Arrays.asList(rotations));

 return rotations;
}

/**
 * Adds the tiles to the left and right panels.
 * @param basic The GridBagConstraints object for setting up the grid layout
 * @param rotations An array of 16 integers representing the rotations of the tiles
 */
private void addTilesToPanels(GridBagConstraints basic, int[] rotations) 
{
 basic.gridx = 0;
 basic.insets = new Insets(5, 5, 5, 5);

 // Create a list of indices and shuffle it
 tileIndices = new ArrayList<>();
 for (int i = 0; i < 16; i++) 
 {
  tileIndices.add(i);
 }
 Collections.shuffle(tileIndices);
 
 for (int i = 0; i < 8; i++) 
 {
  // Left Panel
  basic.gridy = i;
  int leftIndex = tileIndices.get(i);
  tile[i] = new Tile(leftIndex, lineCoords[leftIndex], rotations[leftIndex]);
  LPanel.add(new playbox(), basic);
  ((JPanel) LPanel.getComponent(i)).add(tile[i]);
  ((JPanel) LPanel.getComponent(i)).setBorder(null);

  // Right Panel
  int rightIndex = tileIndices.get(i + 8);
  tile[i+8] = new Tile(rightIndex, lineCoords[rightIndex], rotations[rightIndex]);
  RPanel.add(new playbox(), basic);
  ((JPanel) RPanel.getComponent(i)).add(tile[i+8]);
  ((JPanel) RPanel.getComponent(i)).setBorder(null);
 }
 
}

/**
 * Adds the grid and left and right panels to the board.
 * @param basic The GridBagConstraints object for setting up the grid layout
 */
private void addGridAndLRPanels(GridBagConstraints basic)
{

 // Adds grid to the board
 basic.ipadx = 0; basic.ipady = 0; 
 basic.gridx = 1; basic.gridy = 1;
 basic.insets = new Insets(75, 10, 10 ,10);
 add(grid, basic);
	  
 // Adds the L&R Panels
 basic.ipadx = 1; basic.ipady = 1;
 basic.insets = new Insets(1,50,1,50);
 basic.gridx = 0; basic.gridy = 0;
 basic.gridheight = 8;
 add(LPanel,basic);
 basic.gridx = 2; basic.gridy = 0;
 add(RPanel, basic);
}

/**
 * Function to generate the buttons.
 *
 * @return JPanel containing the buttons.
 */
private void addButtons(GridBagConstraints basic)
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
  buttons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
  buttons[i].setPreferredSize(new Dimension(100, 100));
  buttons[i].setFont(new Font("Arial", Font.PLAIN, 18));
  ButtonPanel.add(buttons[i]);
  buttons[i].addActionListener(this);
  buttons[i].setActionCommand(action[i]);
 }
 //Creates space for buttons and adds them
 basic.gridx=1; basic.gridy=0;
 basic.ipadx = 0; basic.ipady = 0;
 basic.gridwidth = 1; basic.gridheight = 1;
 basic.insets = new Insets(10,10,10,10);
 add(ButtonPanel, basic);
}

/**
 * Sets lastTileClicked when a tile is clicked.
 *
 * @param tile the Tile object that was clicked.
 */
public static void tileClick(Tile tile)
{	 
 if (lastTileClicked != null)
 {
  // Returns tile clicked before to its original color
  TileFlasher flasher = new TileFlasher(lastTileClicked);
  lastTileClicked.setBackground(new Color(175,175,175));
  lastTileClicked = null;
 }
 else
 {
  lastTileClicked = tile;
  lastTileClicked.setBackground(new Color(100, 100, 100));
 }
}

/**
 * Moves the tile to the playbox that was clicked.
 *
 * @param pbox the playbox object that was clicked.
 */
public static void playboxClick(playbox pbox) 
{
 Container parent = lastTileClicked.getParent();

 if (parent instanceof playbox) 
 {
  int prevRow = ((playbox) parent).getRow();
  int prevCol = ((playbox) parent).getCol();

  ((playbox) parent).setBorder(BorderFactory.createLineBorder(Color.BLACK));
  ((playbox) parent).remove(lastTileClicked);

  if (((playbox) parent).isSidePanel() == false) 
  {
   gridData[prevRow][prevCol] = 0;
  }
 }

 // Adds the tile to the playbox and sets colors
 pbox.add(lastTileClicked);
 pbox.setBorder(null);
 lastTileClicked.setBackground(new Color(175, 175, 175));
 lastTileClicked = null;

 // updates the gridData
 int newRow = pbox.getRow();
 int newCol = pbox.getCol();
 if (pbox.isSidePanel() == false) 
 {
  gridData[newRow][newCol] = 1;
 }

 // Set borders of all grid playboxes to have full borders
 for (int i = 0; i < 4; i++) 
 {
  for (int j = 0; j < 4; j++) 
  {
   pboxArr[i][j].updateBorders(new int[]{1, 1, 1, 1});
  }
 }
    
 // Remove borders for adjacent playboxes with tiles
 for (int i = 0; i < 4; i++) 
 {
  for (int j = 0; j < 4; j++) 
  {
   if (gridData[i][j] == 1) 
   {
    pboxArr[i][j].removeBorders();
   }
  }
 }
 grid.repaint();
 LPanel.repaint();
 RPanel.repaint();
}

/**
 * Resets the game to its original state.
 */
public void reset() 
{
 //Reset Background Colors
 if (lastTileClicked != null) 
 {
  lastTileClicked.setBackground(new Color(175, 175, 175));
 }
 gridData = new int[4][4];
 lastTileClicked = null;
 
 // Move the tiles to the panels with their original setup   
 for (int i = 0; i < 8; i++) 
 {
  // Left Panel
  int leftIndex = tileIndices.get(i);
  ((JPanel) LPanel.getComponent(i)).add(tile[i]);
  tile[i].rotate(tile[leftIndex].getOriginalRotation());
  ((JPanel) LPanel.getComponent(i)).setBorder(null);
  ((JPanel) grid.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.BLACK));

  // Right Panel
  int rightIndex = tileIndices.get(i + 8);
  ((JPanel) RPanel.getComponent(i)).add(tile[i+8]);
  tile[i+8].rotate(tile[rightIndex].getOriginalRotation());
  ((JPanel) RPanel.getComponent(i)).setBorder(null);
  ((JPanel) grid.getComponent(i+8)).setBorder(BorderFactory.createLineBorder(Color.BLACK));

 }
    
 // Repaint the panels
 grid.repaint();
 LPanel.repaint();
 RPanel.repaint();
}


/**
 * Needed so that if something other than a playbox is pressed,
 * it un-selects the playbox.
 *
 * @param e the MouseEvent object.
 */
@Override
public void mousePressed(MouseEvent e) 
{
 if (lastTileClicked != null)
 {
  TileFlasher flasher = new TileFlasher(lastTileClicked);
  lastTileClicked.setBackground(new Color(175,175,175));
 }
 lastTileClicked = null;
}
@Override
public void mouseClicked(MouseEvent e) {}
@Override
public void mouseReleased(MouseEvent e) {}
@Override
public void mouseEntered(MouseEvent e) {}
@Override
public void mouseExited(MouseEvent e) {}
};
