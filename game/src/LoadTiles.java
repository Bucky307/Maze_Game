/**
 * LoadTiles class for the aMaze project.
 * This class handles either the placement of tiles
 * either from randomization or from the file 
 * itself.
 * @author Buck Harris
 * Date: Mar 30, 2023
 * Updated: Apr 02, 2023
 */
 

import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
 
/**
 * This class handles loading tiles either 
 * from a default file or from a played maze file
 */
public class LoadTiles
{
 private Tile[] tile;
 private JPanel grid, LPanel, RPanel;
 private String fileName;
 private Randomizer rand;
 private FileSetup file;
 private boolean unplayed;
 private GameWindow gWindow;
 private static boolean isValid;

 /**
  * Constructor for the LoadTiles class.
  * Initializes the LoadTiles object with the given parameters.
  * @param grid The JPanel representing the grid.
  * @param LPanel The JPanel representing the left panel.
  * @param RPanel The JPanel representing the right panel.
  * @param fName The name of the file to be loaded.
  * @param gWindow The GameWindow object.
  */
 public LoadTiles(JPanel grid, JPanel LPanel, JPanel RPanel, String fName, GameWindow gWindow)
 {
  this.grid = grid;
  this.LPanel = LPanel;
  this.RPanel = RPanel;
  fileName = fName;
  this.gWindow = gWindow;
  file = new FileSetup(fileName, gWindow);
  unplayed = file.isUnplayed();
  isValid = file.isValid();

  tile = new Tile[16];

  if(isValid == false)
   return;

  if (unplayed)
   addTilesDefault();
  else
   addTiles();
 }
 /**
  * Returns whether the file is valid or not.
  * @return true if the file is valid, false otherwise.
  */
 public boolean getisValid()
 {
  return isValid;
 }
 /**
  * Sets the unplayed status of the LoadTiles object.
  */
 public void setUnplayed()
 {
  unplayed = false;
 }
 /**
  * Returns whether the LoadTiles object is unplayed or not.
  * @return true if the object is unplayed, false otherwise.
  */
 public boolean isUnplayed()
 {
  return unplayed;
 }
 /**
  * Returns an array containing the tile positions.
  * @return An integer array containing the tile positions.
  */
 public int[] getTilePositions()
 {
  int[] x = new int[16];
  for (int i = 0; i < 16; i++)
  {
	x[i] = tile[i].getPosition();
  }
  return x;
 }
 /**
  * Returns an array containing the tile rotations.
  * @return An integer array containing the tile rotations.
  */
 public int[] getTileRotations()
 {
  int[] x = new int[16];
  for (int i = 0; i < 16; i++)
  {
   x[i] = tile[i].getRotation();
  }
  return x;
 }
 /**
  * Returns a 2D array containing the line coordinates of the original tiles.
  * @return A 2D float array containing the line coordinates of the original tiles.
  */
 public float[][] getLineCoordsOg()
 {
  float[][] x = new float[16][16];
  for (int i = 0; i < 16; i++)
  {
   x[i] = tile[i].getlineCoordsOg();
  }
  return x;
 }
 /**
  * Returns an array containing the tile numbers.
  * @return An integer array containing the tile numbers.
  */
 public int[] getTileNum()
 {
  int[] x = new int[16];
  for (int i = 0; i < 16; i++)
  {
   x[i] = tile[i].getTileNum();
  }
  return x;
 }
 
 
 /**
  * Adds default tiles to the left and right panels for unplayed files.
  */
 public void addTilesDefault()
 {
  rand = new Randomizer();
  float[][] lineCoords = file.getLineCoords();
  int[] rotations = rand.getRotation();
  ArrayList<Integer> indices = rand.getIndice();
  
  
  for (int i = 0; i < 16; i++) 
  {
   if (i < 8)
   {
	int leftIndex = indices.get(i);
	tile[i] = new Tile(leftIndex, lineCoords[leftIndex], rotations[leftIndex], i);
	((Playbox) LPanel.getComponent(i)).addTile(tile[i]);
    ((JPanel) LPanel.getComponent(i)).setBorder(null);  
   }
   else
   {   
	int rightIndex = indices.get(i);
	tile[i] = new Tile(rightIndex, lineCoords[rightIndex], rotations[rightIndex], i);
	((Playbox) RPanel.getComponent(i-8)).addTile(tile[i]);
	((JPanel) RPanel.getComponent(i-8)).setBorder(null);
   }
  } 
 }
 /**
  * Resets the tiles to their original state.
  */
 public void resetTiles()
 {
  if(isValid)
  {
   for (int i = 0; i < 16; i++)
   {
    int originalPosition = tile[i].getOriginalPosition();

    if (originalPosition < 8)
    {
     ((JPanel) LPanel.getComponent(originalPosition)).removeAll();
     ((JPanel) LPanel.getComponent(originalPosition)).add(tile[i]);
     tile[i].rotate(tile[i].getOriginalRotation());
     tile[i].updatePosition((Playbox)LPanel.getComponent(originalPosition));
     ((JPanel) LPanel.getComponent(originalPosition)).setBorder(null);
    }
    else if (originalPosition < 16)
    {
     ((JPanel) RPanel.getComponent(originalPosition - 8)).removeAll();
     ((JPanel) RPanel.getComponent(originalPosition - 8)).add(tile[i]);
     tile[i].rotate(tile[i].getOriginalRotation());
     tile[i].updatePosition((Playbox)RPanel.getComponent(originalPosition - 8));
     ((JPanel) RPanel.getComponent(originalPosition - 8)).setBorder(null);
    }
    else
    {
     int col = (originalPosition - 16) % 4;
     int row = (originalPosition - 16) / 4; 

     int gridIndex = col * 4 + row;

     ((JPanel) grid.getComponent(gridIndex)).removeAll();
     ((JPanel) grid.getComponent(gridIndex)).add(tile[i]);
     tile[i].rotate(tile[i].getOriginalRotation());
     tile[i].updatePosition((Playbox)grid.getComponent(gridIndex));
     gWindow.setGrid(row, col);
    }
   }
   
  //fixes problem with L/RPannels having wrong borders
   for (int i = 0; i < 8; i++) 
   {
	     
    if (((JPanel) LPanel.getComponent(i)).getComponentCount() == 0)
    {
	 ((JPanel) LPanel.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}
    else
    {
	 ((JPanel) LPanel.getComponent(i)).setBorder(null);
	}
	if (((JPanel) RPanel.getComponent(i)).getComponentCount() == 0)
	{
	 ((JPanel) RPanel.getComponent(i)).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}
	else
	{
	 ((JPanel) RPanel.getComponent(i)).setBorder(null);
	}
   }
  }
  GameWindow.fixBorders();
 }
 /**
  * Adds tiles to the grid, left, and right panels based on the loaded file.
  */
 public void addTiles()
 {
  int[] pos = file.getTilePositionsOg();
  int[] rot = file.getTileRotationsOg();
  float[][] lc = file.getLineCoords();
  tile = new Tile[16];
  for (int i = 0; i <16; i++)
  {
   if (pos[i] < 8)
   {
	tile[i] = new Tile(i, lc[i], rot[i], pos[i]);
	((JPanel) LPanel.getComponent(pos[i])).add(tile[i]);  
    tile[i].updatePosition((Playbox)LPanel.getComponent(pos[i]));
   }
   else if (pos[i] < 16)
   {
	tile[i] = new Tile(i, lc[i], rot[i], pos[i]);
	((JPanel) RPanel.getComponent(pos[i]-8)).add(tile[i]);
    tile[i].updatePosition((Playbox)RPanel.getComponent(pos[i]-8));
   }
   else if (pos[i] < 32)
   {
	int col = (pos[i] - 16) % 4;
    int row = (pos[i] - 16) / 4;

    int gridIndex = col * 4 + row;

    tile[i] = new Tile(i, lc[i], rot[i], pos[i]);
    ((JPanel) grid.getComponent(gridIndex)).add(tile[i]);
    tile[i].updatePosition((Playbox)grid.getComponent(gridIndex));
    gWindow.setGrid(row, col);  
   }
  }	 
 }
 /**
  * Returns an array containing the tiles.
  * @return A Tile array containing the tiles.
  */
 public Tile[] getTiles()
 {
  return tile;
 }
 /**
  * Returns a long containing the original time.
  * @return A long array containing the original time.
  */
 public long getTimeOg()
 {
  return file.getTimeOg();
 }
 
}

