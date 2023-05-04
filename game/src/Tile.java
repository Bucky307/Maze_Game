/**
 * Tile class for the aMaze project.
 * This class handles all the piece functionality.
 * @author Buck Harris
 * Date: Feb 13, 2023
 * Updated: Feb 19, 2023
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Tile class represents individual cells within the game grid, handling their visual appearance and interaction.
 * It is responsible for rendering lines based on the provided coordinates, and for handling mouse events
 * to notify the GameWindow of any user interactions with the tiles.
 */
public class Tile extends JPanel implements MouseListener 
{
 private float[] lineCoords;
 private float[] lineCoordsOg;
 private int tilePositionOg;
 private int tileRotationOg;
 private int tilePosition;
 private int tileRotation = 0;
 private int tileNum;

 /**
  * Constructor for the Tile class.
  * For this version, it only sets the color and label.
  * @param tileNum The integer used for the label.
  * @param lineCoordsOg The array of floats representing the line coordinates.
  * @param tileRotationOg The integer that is the original rotation.
  * @param tilePositionOg The integer that is the original position.
  */
public Tile(int tileNum, float[] lineCoordsOg, int tileRotationOg, int tilePositionOg)
{
 super(new BorderLayout());
 this.tileNum = tileNum;
 this.lineCoordsOg = lineCoordsOg;
 lineCoords = lineCoordsOg;
 this.tileRotationOg = tileRotationOg;
 this.tilePositionOg = tilePositionOg;
 lineCoords = Arrays.copyOf(lineCoordsOg, lineCoordsOg.length);
 super.addMouseListener(this);
 super.setBackground(new Color(175,175,175));
 super.setPreferredSize(new Dimension(0, 0));
 rotate(tileRotationOg);
}

/**
 * Paints the lines from the .mze file on the tile.
 * @param g The Graphics object used to draw the lines.
 */
@Override
protected void paintComponent(Graphics g) 
{
  super.paintComponent(g);
   
 // Draw the black lines 

 ((Graphics2D) g).setStroke(new BasicStroke(2));
 ((Graphics2D) g).setColor(Color.BLACK);
 
 for (int i = 0; i < lineCoords.length; i += 4) 
 {
  int x1 = (int)lineCoords[i];
  int y1 = (int)lineCoords[i + 1];
  int x2 = (int)lineCoords[i + 2];
  int y2 = (int)lineCoords[i + 3];
  g.drawLine(x1, y1, x2, y2);
 }
}
/**
 * Gets the current rotation of this Tile object.
 * @return An integer value representing the current rotation
 */
public int getRotation()
{
 return tileRotation;
}
/**
 * Gets the current position of this Tile object.
 * @return An integer value representing the current position
 */
public int getPosition()
{
 return tilePosition;
}
/**
 * Gets the original line coordinates of this Tile object.
 * @return An array of floats representing the original line coordinates
 */
public float[] getlineCoordsOg()
{
 return lineCoordsOg;
}
/**
 * Gets the tile number of this Tile object.
 * @return An integer value representing the tile number
 */
public int getTileNum()
{
 return tileNum;
}


/**
 * Gets the original rotation of this Tile object.
 * @return An integer value representing the original rotation
 */
public int getOriginalRotation()
{
 return tileRotationOg;
}
/**
 * Gets the original position of this Tile object.
 * @return An integer value representing the original position
 */
public int getOriginalPosition()
{
	return tilePositionOg;
}

/**
 * Rotates the tile 90 degrees clockwise, updating the line coordinates
 * and repainting the tile.
 */
public void rotate()
{
 // Rotate the lines by swapping coordinates
 for (int i = 0; i < lineCoords.length; i += 4) 
 {
  int x1 = (int)lineCoords[i];
  int y1 = (int)lineCoords[i + 1];
  int x2 = (int)lineCoords[i + 2];
  int y2 = (int)lineCoords[i + 3];

  // Calculate the new coordinates after rotation
  int newX1 = 100 - y1;
  int newY1 = x1;
  int newX2 = 100 - y2;
  int newY2 = x2;

  // Update the line coordinates
  lineCoords[i] = newX1;
  lineCoords[i + 1] = newY1;
  lineCoords[i + 2] = newX2;
  lineCoords[i + 3] = newY2;
 }
 // Repaint the tile to show the updated lines and rotation
 repaint();
 
 //Increment the rotation value
 tileRotation ++;
 tileRotation %= 4;
}

/**
 * Rotates the tile to its original rotation.
 * @param originalRotation The integer value of the original rotation
 */
public void rotate(int tileRotationOg)
{
 while(tileRotation != tileRotationOg)
  rotate();
}
/**
 * Updates the position of this Tile object based on the given Playbox object.
 * @param pbox The Playbox object used to update the position
 */
public void updatePosition(Playbox pbox)
{
  tilePosition = pbox.getPosition();
}

/**
 * Handles the MouseEvent for when the mouse is pressed on this Tile object.
 * If the right mouse button is clicked, the tile is rotated, and the GameWindow is updated.
 * If the left mouse button is clicked, the tileClick method is called on the GameWindow.
 * @param e The MouseEvent object representing the event
 */
@Override
public void mousePressed(MouseEvent e) 
{
 //System.out.println("Tile: " + tileNum + " Rot: " + tileRotation);
	
 GameWindow.gTime.start();
 if (e.getButton() == MouseEvent.BUTTON3)
 {
  if(GameWindow.lastTileClicked != null)
   GameWindow.lastTileClicked.setBackground(new Color(175, 175, 175));
   GameWindow.lastTileClicked = null;
   GameWindow.setEdited();
   this.rotate();
   GameWindow.winChecker();
 }
 else
 {
  GameWindow.tileClick(this);
 }
 
 
}

/**
 * Other methods of mouseListener
 * Need to be added or there are issues
 * Do nothing for the project
 */
@Override
public void mouseClicked(MouseEvent e) {}
@Override
public void mouseReleased(MouseEvent e) {}
@Override
public void mouseEntered(MouseEvent e) {}
@Override
public void mouseExited(MouseEvent e) {}
}