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
public class GameWindow extends JFrame implements MouseListener
{
 
private static PlayAreas pArea;
public static LoadTiles loadTiles;

public static int[][] gridData = new int[4][4];
public static Playbox[][] pboxArr = new Playbox[4][4];
public static Tile lastTileClicked = null;
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
 * This method sets up the game board.
 * It takes the data from the .mze file and turns it into usable coordinates
 * that are used to paint on top of the tile objects.
 */
public void setUp(String fileName)
{
 GridBagConstraints basic = new GridBagConstraints();
 
 //creates the 3 play areas
 pArea = new PlayAreas();
 JPanel grid = pArea.getGrid();
 JPanel LPanel = pArea.getLPanel();
 JPanel RPanel = pArea.getRPanel();
 grid.addMouseListener(this);
 LPanel.addMouseListener(this);
 RPanel.addMouseListener(this);
 
 //Generates the Random arrays
 
 //Adds the tiles for default.mze
 loadTiles = new LoadTiles(grid, LPanel, RPanel, fileName, this);
 //pArea.addTiles(indices, rotations);
 
 // Adds the grid
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
 
 // Adds the Buttons
 Buttons buttons = new Buttons(basic, this);
 add(buttons.getButtonPanel(), basic);
 reset();
 return;
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
public static void playboxClick(Playbox pbox) 
{
 Container parent = lastTileClicked.getParent();
 loadTiles.setUnplayed();
 
 int prevRow = ((Playbox) parent).getRow();
 int prevCol = ((Playbox) parent).getCol();

 ((Playbox) parent).setBorder(BorderFactory.createLineBorder(Color.BLACK));
 ((Playbox) parent).rmTile(lastTileClicked);

 if (((Playbox) parent).isSidePanel() == false) 
 {
  pArea.gridData[prevRow][prevCol] = 0;
 }
 
 // Adds the tile to the playbox and sets colors
 pbox.addTile(lastTileClicked);
 lastTileClicked.updatePosition(pbox);
 pbox.setBorder(null);
 lastTileClicked.setBackground(new Color(175, 175, 175));
 lastTileClicked = null;
 
 // updates the gridData
 int newRow = pbox.getRow();
 int newCol = pbox.getCol();
 if (pbox.isSidePanel() == false) 
 {
  pArea.gridData[newRow][newCol] = 1;
 }

 fixBorders();

}

public static void fixBorders()
{
 // Set borders of all grid playboxes to have full borders
 for (int i = 0; i < 4; i++) 
 {
  for (int j = 0; j < 4; j++) 
  {
   pArea.updatePboxBorders(i, j, new int[]{1, 1, 1, 1});
  }
 }
     
 // Remove borders for adjacent playboxes with tiles
 for (int i = 0; i < 4; i++) 
 {
  for (int j = 0; j < 4; j++) 
  {
   if (pArea.gridData[i][j] == 1) 
   {
    pArea.updatePboxBorders(i, j);
   }
  }
 }
 
 pArea.repaintBorders();	
 
}

/**
 * Resets the game to its original state.
 */
public static void reset() 
{
 //Reset Background Colors
 if (lastTileClicked != null) 
 {
  lastTileClicked.setBackground(new Color(175, 175, 175));
 }
 pArea.gridData = new int[4][4];
 lastTileClicked = null;
 
 // Move the tiles to the panels with their original setup   
 loadTiles.resetTiles();
 fixBorders();
 // Repaint the PlayAreas
 pArea.repaintBorders();
}


public static boolean unplayed;
public static int[] tilePositions;
public static int[] tileRotations;
public static float[][] lineCoords;
public static int[] tileNum;

public void getSaveData()
{
 unplayed = loadTiles.isUnplayed();
 tilePositions = loadTiles.getTilePositions();
 tileRotations = loadTiles.getTileRotations();
 lineCoords = loadTiles.getLineCoordsOg();
 tileNum = loadTiles.getTileNum();
}

public void setGrid(int row, int col)
{
 pArea.gridData[row][col] = 1; 
}

public void loadGame(String fileName) 
{
 getContentPane().removeAll(); 
 setUp(fileName); 
 revalidate(); 
 repaint();
}

public static void updatePlayed() {
	loadTiles.setUnplayed();
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
