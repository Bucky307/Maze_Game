
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

/**
 * Tile class represents individual cells within the game grid, handling their visual appearance and interaction.
 * It is responsible for rendering lines based on the provided coordinates, and for handling mouse events
 * to notify the GameWindow of any user interactions with the tiles.
 */
public class Tile extends JPanel implements MouseListener 
{

 private float[] lineCoords;
 private int rotation = 0, originalRotation;

 /**
  * Constructor for the Tile class.
  * For this version, it only sets the color and label.
  * @param num The integer used for the label.
  * @param lineCoords The array of floats representing the line coordinates.
  */
public Tile(int num, float[] lineCoords, int originalRotation) 
{
 super(new BorderLayout());
 this.lineCoords = lineCoords;
 this.originalRotation = originalRotation;
 super.setName(String.valueOf(num));
 super.addMouseListener(this);
 super.setBackground(new Color(175,175,175));
 super.setPreferredSize(new Dimension(0, 0));
 rotate(originalRotation);
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
 * Gets the original rotation of this Tile object.
 * @return An integer value representing the original rotation
 */
public int getOriginalRotation()
{
 return originalRotation;
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
 rotation ++;
 rotation %= 4;
}

/**
 * Rotates the tile to its original rotation.
 * @param originalRotation The integer value of the original rotation
 */
public void rotate(int originalRotation)
{
 while(rotation != originalRotation)
  rotate();
}

/**
 * Passes which tile is pressed to GameWindow.
 * @param e The MouseEvent object representing the event.
 */
@Override
public void mousePressed(MouseEvent e) 
{
 if (e.getButton() == MouseEvent.BUTTON3)
 {
  if(GameWindow.lastTileClicked != null)
   GameWindow.lastTileClicked.setBackground(new Color(175, 175, 175));
  GameWindow.lastTileClicked = null;
  this.rotate();
 }
 else GameWindow.tileClick(this);
 
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