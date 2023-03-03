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
	
	
@Override
public void mouseClicked(MouseEvent e) 
{
}

@Override
public void mousePressed(MouseEvent e) {
// TODO Auto-generated method stub
 GameWindow.playboxClick(this); 

}

@Override
public void mouseReleased(MouseEvent e) {
// TODO Auto-generated method stub
}

@Override
public void mouseEntered(MouseEvent e) {
// TODO Auto-generated method stub
		
}

@Override
public void mouseExited(MouseEvent e) {
// TODO Auto-generated method stub
		
}

	
	
	
}
