/*
 * @author Buck Harris
 * Date: Feb 13, 2023
 * Updated: Feb 19, 2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Board extends JFrame implements MouseListener{


 private GridBagConstraints basic;
 public JPanel board;
 public GameWindow gameWindow;

	
 public Board(GridBagConstraints b)
{
		
		
 basic = b;
 board = new JPanel(new GridBagLayout());

 //Initializes all the grid spots
 JPanel e11 = new JPanel();
 JPanel e12 = new JPanel();
 JPanel e13 = new JPanel();
 JPanel e14 = new JPanel();
 
 JPanel e21 = new JPanel();
 JPanel e22 = new JPanel();
 JPanel e23 = new JPanel();
 JPanel e24 = new JPanel();

 JPanel e31 = new JPanel();
 JPanel e32 = new JPanel();
 JPanel e33 = new JPanel();
 JPanel e34 = new JPanel();

 JPanel e41 = new JPanel();
 JPanel e42 = new JPanel();
 JPanel e43 = new JPanel();
 JPanel e44 = new JPanel();

 JPanel[][] grid ={{e11,e12,e13,e14},
				   {e21,e22,e23,e24},	
				   {e31,e32,e33,e34},				
				   {e41,e42,e43,e44}};
//Sets a name to each of the grid spots
 String r, c;
 for (int row = 0; row < 4; row++)
  {
   r = String.valueOf("g"+row);
   for (int col = 0; col < 4; col++)
    {
	 c = String.valueOf(col);
	 grid[row][col].setName(r+c);
	}
  }
 
 basic.ipadx = 50;
 basic.ipady = 50;
 basic.insets = new Insets(0,0,0,0);

 //Places grid squares in their correct positions
 for (int row = 0; row < 4; row++)
 {
  basic.gridy = row;
  for (int col = 0; col < 4; col++)
   {
    basic.gridx = col;
	grid[row][col].setBorder(BorderFactory.createLineBorder(Color.black));
	grid[row][col].addMouseListener(this);
	board.add(grid[row][col], basic);
   }
  }
		
}
	
//Takes a tile and moves it to the correct grid position
public void moveTile(JPanel tile, int x, int y)
{
 basic.gridx = y;
 basic.gridy = x;
 basic.gridwidth = 1;
 basic.gridheight = 1;
		
 tile.setFont(new Font("Arial", Font.PLAIN, 12));
 tile.setPreferredSize(new Dimension(12, 12));
 tile.setBackground(Color.magenta);
 tile.setForeground(Color.black);
		
 board.add(tile, basic);
 Container container = tile.getParent();
 container.setComponentZOrder(tile, 0);	
}

//Return the board
public JPanel ReturnBoard() 
{
 return board;
}
	
@Override
public void mouseClicked(MouseEvent e) 
{
 JPanel clickedPanel = (JPanel)e.getSource(); 
  gameWindow.panelClicked(clickedPanel);
}

public void setGameWindow(GameWindow gameWindow) 
{
 this.gameWindow = gameWindow;
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


