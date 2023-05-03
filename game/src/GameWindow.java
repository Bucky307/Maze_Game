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
import javax.swing.JOptionPane;
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
public static boolean unplayed;
public static int[] tilePositions;
public static int[] tileRotations;
public static float[][] lineCoords;
public static long timeOg;
public static int[] tileNum;
public static GameTime gTime = new GameTime();
//public static int[][] gridData = new int[4][4];
//public static Playbox[][] pboxArr = new Playbox[4][4];
public static Tile lastTileClicked = null;
public static final long serialVersionUID=1;

private static boolean edited;
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
 edited = false;
}

/**
 * Sets the edited flag to true.
 */
public static void setEdited() {
 edited = true;
 return;
}
/**
 * Sets the edited flag to false.
 */
public static void setUnedited() {
 edited = false;
 return;
}
/**
 * Returns the value of the edited flag.
 *
 * @return boolean value indicating whether the game has been edited.
 */
public static boolean getEdited() {
 return edited;
}

/**
 * Sets up the game board.
 * 
 * @param fileName The name of the .mze file containing the game data.
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
 
 //Adds the tiles for default.mze
 loadTiles = new LoadTiles(grid, LPanel, RPanel, fileName, this);
 //pArea.addTiles(indices, rotations);

 // Adds the grid
 basic.ipadx = 0; basic.ipady = 0; 
 basic.gridx = 1; basic.gridy = 2;
 basic.insets = new Insets(75, 10, 10 ,10);
 add(grid, basic);
	  
 // Adds the L&R Panels
 basic.ipadx = 1; basic.ipady = 2;
 basic.insets = new Insets(1,50,1,50);
 basic.gridx = 0; basic.gridy = 0;
 basic.gridheight = 8;
 add(LPanel,basic);
 basic.gridx = 2; basic.gridy = 0;
 add(RPanel, basic);
 
 // Adds the Buttons
 Buttons buttons = new Buttons(basic, this);
 add(buttons.getButtonPanel(), basic);

 // Add Timer
 basic.gridx = 1; basic.gridy = 0;
 add(gTime, basic);
		 
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
 
 int prevRow = ((Playbox) parent).getRow();
 int prevCol = ((Playbox) parent).getCol();

 ((Playbox) parent).setBorder(BorderFactory.createLineBorder(Color.BLACK));
 ((Playbox) parent).rmTile(lastTileClicked);

 if (((Playbox) parent).isSidePanel() == false) 
 {
  PlayAreas.gridData[prevRow][prevCol] = 0;
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
  PlayAreas.gridData[newRow][newCol] = 1;
 }
 winChecker();
 fixBorders();
 setEdited();
}

/**
 * Fixes the borders of the playboxes based on whether they have tiles or not.
 */
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
   if (PlayAreas.gridData[i][j] == 1) 
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
 PlayAreas.gridData = new int[4][4];
 lastTileClicked = null;
 
 // Move the tiles to the panels with their original setup   
 loadTiles.resetTiles();
 fixBorders();
 // Repaint the PlayAreas
 pArea.repaintBorders();
 // Fix time
 gTime.stop();
 gTime.setTime(loadTiles.getTimeOg());
 setUnedited();
}

/**
 * Retrieves data for saving the game.
 */
public void getSaveData()
{
 tilePositions = loadTiles.getTilePositions();
 tileRotations = loadTiles.getTileRotations();
 lineCoords = loadTiles.getLineCoordsOg();
 tileNum = loadTiles.getTileNum();
 timeOg = gTime.getTime();
}
/**
 * Sets a grid location to have a tile.
 * 
 * @param row The row of the grid location.
 * @param col The column of the grid location.
 */
public void setGrid(int row, int col)
{
 PlayAreas.gridData[row][col] = 1; 
}
/**
 * Loads a game from a specified file.
 * 
 * @param fileName The name of the file containing the game data.
 */
public void loadGame(String fileName) 
{
 getContentPane().removeAll(); 
 setUp(fileName); 
 gTime.setTime(loadTiles.getTimeOg());
 revalidate();
 repaint();
}
/**
 * Checks if the game has been won by verifying three conditions:
 * 1. All grid cells in the PlayAreas.gridData array are set to 1.
 * 2. All tiles have a rotation of 0.
 * 3. All tiles are in their correct position.
 *
 * If all conditions are met, a dialog is displayed showing the completion time
 * and a message that the game has been won.
 */
public static void winChecker()
{
 Tile[] tile = loadTiles.getTiles();
 for (int i = 0; i < PlayAreas.gridData.length; i++)
 {
  for (int j = 0; j < PlayAreas.gridData[i].length; j++)
  {
	if (PlayAreas.gridData[i][j] != 1)
    return;
  }
 }
 
 for (int i = 0; i < 16; i++)
 {
  if (tile[i].getRotation() != 0)
   return;
 }
 for (int i = 0; i < 16; i++)
 {
  if (tile[i].getPosition()-16 != i)
	return;
 }  
 gTime.stop();
 long seconds = gTime.getTime();
 long hours = seconds / 3600;
 long minutes = (seconds % 3600) / 60;
 long secs = seconds % 60;
 
 JOptionPane.showMessageDialog(null, "You won the game!\nTime: " + String.format("%02d:%02d:%02d", hours, minutes, secs)
                              , "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
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