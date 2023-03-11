
/*
 * @author Buck Harris
 * Date: Feb 13, 2023
 * Updated: Feb 19, 2023
 *
 * Tile class for the aMaze project
 * This will handle all the piece functionality
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tile extends JPanel implements MouseListener 
{

 private float[] lineCoords;
/*
 * Constructor for the tile class.
 * For this version it only sets the color and label
 * Takes an int for the label
 */
public Tile(int num, float[] lineCoords) 
{
 super(new BorderLayout());
 this.lineCoords = lineCoords;
 super.setName(String.valueOf(num));
 super.addMouseListener(this);
 super.setBackground(new Color(175,175,175));
 super.setPreferredSize(new Dimension(0, 0));
 
}

//Paints the lines from the .mze on the tile
@Override
protected void paintComponent(Graphics g) 
{
  super.paintComponent(g);
    
 // Draw the black lines
 g.setColor(Color.BLACK);
 for (int i = 0; i < lineCoords.length; i += 4) 
 {
  int x1 = (int)lineCoords[i];
  int y1 = (int)lineCoords[i + 1];
  int x2 = (int)lineCoords[i + 2];
  int y2 = (int)lineCoords[i + 3];
  g.drawLine(x1, y1, x2, y2);
 }
}

// Passes which tile is pressed to gameWindow
@Override
public void mousePressed(MouseEvent e) {
 GameWindow.tileClick(this); 
}

/*
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