
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
private int num;

public Tile(int num) 
{
 super(new BorderLayout());
 this.num = num;
 super.setName(String.valueOf(num));
 super.addMouseListener(this);
 JLabel label = new JLabel(String.format("%02d", num));
 label.setHorizontalAlignment(SwingConstants.CENTER); 
 label.setFont(new Font("Arial", Font.PLAIN, 12));
 super.add(label, BorderLayout.CENTER); 
 super.setBackground(Color.magenta);
 super.setPreferredSize(new Dimension(0, 0));
 super.setForeground(Color.black);
}
//Passes which tile is was pressed to gameWindow
@Override
public void mouseClicked(MouseEvent e) 
{
	GameWindow.tileClick(this); 
}

@Override
public void mousePressed(MouseEvent e) {
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
