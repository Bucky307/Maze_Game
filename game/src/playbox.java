/**
 * playbox class for the aMaze project.
 * This class handles all playboxes in the 
 * grid and the side panels
 * @author Buck Harris
 * Date: Feb 13, 2023
 * Updated: Feb 19, 2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.MatteBorder;

/**
 * playbox class represents individual boxes in the game grid.
 * It is responsible for handling box functionality including borders and mouse events.
 */
public class playbox extends JPanel implements MouseListener 
{

 private int[] borders = {1, 1, 1, 1}; // top, right, bottom, left (1 = on 0 = off)
 private int row;
 private int col;
 private boolean sidePanel;

 /**
  * Constructor for the playbox class that doesn't take row and column data.
  * This constructor is used for side panels which don't need row and column data.
  */
 public playbox() 
 {
  super(new BorderLayout());
  this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  super.setPreferredSize(new Dimension(0, 0));
  super.addMouseListener(this);
  sidePanel = true;
 }

 /**
  * Constructor for the playbox class that takes row and column data.
  * This constructor is used for grid playboxes.
  * @param row The row index of the playbox in the grid.
  * @param col The column index of the playbox in the grid.
  */
public playbox(int row, int col)
{
 super(new BorderLayout());
 this.row = row;
 this.col = col;
 this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
 super.setPreferredSize(new Dimension(0, 0));
 super.addMouseListener(this);
 sidePanel = false;
}

/**
 * Returns the row index of the playbox in the grid.
 * @return The row index.
 */public int getRow() 
{
 return row;
}

 /**
  * Returns the column index of the playbox in the grid.
  * @return The column index.
  */
public int getCol() 
{
 return col;
}

/**
 * Returns whether the playbox is a side panel playbox.
 * @return true if the playbox is a side panel, false otherwise.
 */
public boolean isSidePanel() 
{
 return sidePanel;
}

/**
 * Passes which playbox is clicked to GameWindow.
 * @param e The MouseEvent object representing the event.
 */
@Override
public void mousePressed(MouseEvent e) 
{
 if (GameWindow.lastTileClicked != null) 
 { 
  if (!isSidePanel()) 
  {
   GameWindow.gridData[row][col] = 1;
  }
  GameWindow.playboxClick(this);
 }
}

/**
 * Updates the individual borders of a playbox.
 * Acts as a helper function for removeBorders().
 * @param bdrs The array of integers representing border thickness.
 */
public void updateBorders(int[] bdrs)
{
 for (int i = 0; i < 4; i++)
 {
  if (bdrs[i] != -1)
  {
   borders[i] = bdrs[i];
  }
 }
 this.setBorder(new MatteBorder(borders[0], borders[3], borders[2], borders[1], Color.BLACK));
}

/**
 * Removes the borders of the playbox and adjacent sides of the adjacent playboxes.
 */
public void removeBorders() 
{
    	
 playbox pArr[][] = GameWindow.pboxArr;
 this.updateBorders(new int[]{0, 0, 0, 0});

 if (row > 0) 
 { // Update the playbox above
  pArr[row - 1][col].updateBorders(new int[]{-1, -1, 0, -1}); 
 }
 if (col > 0) 
 { // Update the playbox to the left
  pArr[row][col - 1].updateBorders(new int[]{-1, 0, -1, -1}); 
 }
 if (row < 3) 
 { // Update the playbox below
  pArr[row + 1][col].updateBorders(new int[]{0, -1, -1, -1}); 
 }
 if (col < 3) 
 { // Update the playbox to the right
  pArr[row][col + 1].updateBorders(new int[]{-1, -1, -1, 0}); 
 }
}
  
// Other methods of MouseListener
@Override
public void mouseClicked(MouseEvent e) {}

@Override
public void mouseReleased(MouseEvent e) {}

@Override
public void mouseEntered(MouseEvent e) {}

@Override
public void mouseExited(MouseEvent e) {}

}

