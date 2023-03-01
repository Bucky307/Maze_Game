
/*
 * @author Buck Harris
 * Date: Feb 13, 2023
 * Updated: Feb 19, 2023
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tile extends JPanel implements MouseListener 
{

public Tile(int num) 
{
 super(new BorderLayout());
 super.setName(String.valueOf(num));
 super.addMouseListener(this);
 JLabel label = new JLabel(String.format("%02d", num));
 label.setHorizontalAlignment(SwingConstants.CENTER); 
 label.setFont(new Font("Arial", Font.PLAIN, 18));
 super.add(label, BorderLayout.CENTER); 
 super.setBackground(new Color(175,175,175));
 super.setPreferredSize(new Dimension(0, 0));
 super.setForeground(Color.black);
}
//Passes which tile is was pressed to gameWindow
@Override
public void mouseClicked(MouseEvent e) 
{

}

@Override
public void mousePressed(MouseEvent e) {
 GameWindow.tileClick(this); 
}

@Override
public void mouseReleased(MouseEvent e) {
      	
}

@Override
public void mouseEntered(MouseEvent e) {
}

@Override
public void mouseExited(MouseEvent e) {
    	
}
}
