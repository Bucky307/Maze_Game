/*
 * @author Buck Harris
 * Date: Feb 13, 2023
 * Updated: Feb 19, 2023
 *
 * Class used to show where tiles can be placed in the GameWindow
 * Has very little functionality outside of visual interaction
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class playbox extends JPanel implements MouseListener
{

//Constructor that takes a row and column and sets border for tile spots
public playbox(int row, int col)
{
 super(new BorderLayout());
 super.setBorder(BorderFactory.createLineBorder(Color.black));
 super.setPreferredSize(new Dimension(0, 0));
 super.addMouseListener(this);
}

// Passes which playbox is clicked to GameWindow
@Override
public void mousePressed(MouseEvent e) {
 GameWindow.playboxClick(this); 
}

/*
 * Again these are added to avoid errors for now 
 */

@Override
public void mouseClicked(MouseEvent e){}

@Override
public void mouseReleased(MouseEvent e) {}

@Override
public void mouseEntered(MouseEvent e) {}

@Override
public void mouseExited(MouseEvent e) {}
	
}