import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PlayAreas extends JPanel
{
 private JPanel grid;
 private JPanel LPanel;
 private JPanel RPanel;
 private Tile[] tile;
 public static Playbox[][] pboxArr;
 public static int[][] gridData;
    
    
 public PlayAreas()
 {
  GridBagConstraints basic = new GridBagConstraints();
  grid = new JPanel(new GridBagLayout());
  LPanel = new JPanel(new GridBagLayout());
  RPanel = new JPanel(new GridBagLayout());
  
  gridData = new int[4][4];
  pboxArr = new Playbox[4][4];
  createGrid(basic);
  createLRPanels(basic);
 }
    
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
    basic.gridy = j;
    gridData[j][i] = 0;
    pboxArr[j][i] = new Playbox(j, i);
    grid.add(pboxArr[j][i], basic);
   }
  }
 }
    
 private void createLRPanels(GridBagConstraints basic)
 {
  basic.gridx = 0;
  basic.insets = new Insets(5, 5, 5, 5);
  	   
  for (int i = 0; i < 16; i++) 
  {
   basic.gridy = i;
   if (i < 8)
   {
    LPanel.add(new Playbox(), basic);
   }
   else
   {
    RPanel.add(new Playbox(), basic);
   }
  }    
 }
 
 public void addTiles(ArrayList<Integer> indices, int[] rotations)
 {
  FileSetup file = new FileSetup("input/default.mze");
  float[][] lineCoords = file.getLineCoords();
  
  tile = new Tile[16];
  for (int i = 0; i < 16; i++) 
  {
   if (i < 8)
   {
    int leftIndex = indices.get(i);
    tile[i] = new Tile(leftIndex, lineCoords[leftIndex], rotations[leftIndex]);
    ((JPanel) LPanel.getComponent(i)).add(tile[i]);
    ((JPanel) LPanel.getComponent(i)).setBorder(null);

   }
   else
   {   
    int rightIndex = indices.get(i);
    tile[i] = new Tile(rightIndex, lineCoords[rightIndex], rotations[rightIndex]);
    ((JPanel) RPanel.getComponent(i-8)).add(tile[i]);
    ((JPanel) RPanel.getComponent(i-8)).setBorder(null);
   }
  } 
 }
 
 public void updatePboxBorders(int i, int j, int[] val)
 {
  pboxArr[i][j].updateBorders(val);
 }
 public void updatePboxBorders(int i, int j)
 {
  pboxArr[i][j].removeBorders();
 }
 public void repaintBorders()
 {
  grid.repaint();
  LPanel.repaint();
  RPanel.repaint();
 }
 public void resetTiles(ArrayList<Integer> indices, int[] rotations)
 {
  for (int i = 0; i < 16; i++) 
  {
   if (i < 8)
   {
    // Left Panel
    int leftIndex = indices.get(i);
    ((JPanel) LPanel.getComponent(i)).add(tile[i]);
    tile[i].rotate(tile[leftIndex].getOriginalRotation());
    ((JPanel) LPanel.getComponent(i)).setBorder(null);
    ((JPanel) grid.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.BLACK));
   }
   else
   {
    // Right Panel
    int rightIndex = indices.get(i);
    ((JPanel) RPanel.getComponent(i - 8)).add(tile[i]);
    tile[i].rotate(tile[rightIndex].getOriginalRotation());
    ((JPanel) RPanel.getComponent(i - 8)).setBorder(null);
    ((JPanel) grid.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.BLACK));
   }
  }
 }
 
 public Tile[] getTile()
 {
  return tile;
 }
 public JPanel getGrid()
 {
  return grid;
 }  
 public JPanel getLPanel()
 {
  return LPanel;
 } 
 public JPanel getRPanel()
 {
  return RPanel;
 } 

}
