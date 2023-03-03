
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

/*
 * Constructor for the tile class.
 * For this version it only sets the color and label
 * Takes an int for the label
 */
public Tile(int num) 
{
 super(new BorderLayout());
 super.setName(String.valueOf(num));
 super.addMouseListener(this);
 // Stylizes the number label
 JLabel label = new JLabel(String.format("%02d", num));
 label.setHorizontalAlignment(SwingConstants.CENTER); 
 label.setFont(new Font("Arial", Font.PLAIN, 18));
 // Stylizes the tile background
 super.add(label, BorderLayout.CENTER); 
 super.setBackground(new Color(175,175,175));
 super.setPreferredSize(new Dimension(0, 0));
 super.setForeground(Color.black);
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
