/**
 * PlayArea class for the aMaze project.
 * This class handles all panels on the 
 * main frame
 * @author Buck Harris
 * Date: Mar 30, 2023
 * Updated: Apr 02, 2023
 */
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/**
 * This class represents the main game area, which includes the main grid,
 * and the left and right panels containing tiles that can be placed on the grid.
 * It also handles the updating and resetting of tiles and their borders.
 */
public class PlayAreas extends JPanel
{
 private JPanel grid;
 private JPanel LPanel;
 private JPanel RPanel;
 public static Playbox[][] pboxArr;
 public static int[][] gridData;
 
    
 /**
  * Constructor for PlayAreas.
  */  
 public PlayAreas()
 {
  GridBagConstraints basic = new GridBagConstraints();
  grid = new JPanel(new GridBagLayout());
  LPanel = new JPanel(new GridBagLayout());
  RPanel = new JPanel(new GridBagLayout());
  
  grid.setBackground(new Color(255, 255, 255));
  LPanel.setBackground(new Color(255, 255, 255));
  RPanel.setBackground(new Color(255, 255, 255));
  
  gridData = new int[4][4];
  pboxArr = new Playbox[4][4];
  createGrid(basic);
  createLRPanels(basic);
 }
 /**
  * Creates the main grid of the game.
  *
  * @param basic The GridBagConstraints object to set the layout of the grid.
  */ 
 private void createGrid(GridBagConstraints basic)
 {
  basic.ipadx = 100;
  basic.ipady = 100;
  basic.insets = new Insets(0, 0, 0, 0);
  for (int i = 0; i < 4; i++) 
  {
   basic.gridx = i;
   for (int j = 0; j < 4; j++)
   {
	int position = 16 + (j * 4) + i;
    basic.gridy = j;
    gridData[j][i] = 0;
    pboxArr[j][i] = new Playbox(j, i, position);
    grid.add(pboxArr[j][i], basic);
    position++;
   }
  }
 }
 /**
  * Creates the left and right panels containing tiles.
  *
  * @param basic The GridBagConstraints object to set the layout of the panels.
  */  
 private void createLRPanels(GridBagConstraints basic)
 {
  basic.gridx = 0;
  basic.insets = new Insets(5, 5, 5, 5);
  for (int i = 0; i < 16; i++) 
  {
   basic.gridy = i;
   if (i < 8)
   {
    LPanel.add(new Playbox(i), basic);
   }
   else
   {
    RPanel.add(new Playbox(i), basic);
   }
  }    
 }

 /**
  * Updates the borders of the Playbox at the specified grid position.
  *
  * @param i   The row index of the Playbox.
  * @param j   The column index of the Playbox.
  * @param val The array of border values to set.
  */
 public void updatePboxBorders(int i, int j, int[] val)
 {
  pboxArr[i][j].updateBorders(val);
 }
 /**
  * Removes borders of the Playbox at the specified grid position.
  *
  * @param i The row index of the Playbox.
  * @param j The column index of the Playbox.
  */
 public void updatePboxBorders(int i, int j)
 {
  pboxArr[i][j].removeBorders();
 }
 /**
  * Repaints the grid and left and right panels.
  */
 public void repaintBorders()
 {
  grid.repaint();
  LPanel.repaint();
  RPanel.repaint();
 }

 /**
  * Returns the Grid object for the grid.
  *
  * @return The Grid object for the grid.
  */
 public JPanel getGrid()
 {
  return grid;
 }  
 /**
  * Returns the LPanel object for the left panel.
  *
  * @return The LPanel object for the left panel.
  */
 public JPanel getLPanel()
 {
  return LPanel;
 } 
 /**
  * Returns the RPanel object for the right panel.
  *
  * @return The RPanel object for the right panel.
  */
 public JPanel getRPanel()
 {
  return RPanel;
 } 
 

 

}