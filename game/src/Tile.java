
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
	
public GridBagConstraints basic;
public GameWindow gameWindow;
public TileHolder tholder;
	
public Tile(GridBagConstraints b, TileHolder t) 
{
 basic = b;
 tholder = t;
 
//Initializes the tile pieces
 JPanel t00 = new JPanel();
 JPanel t01 = new JPanel();
 JPanel t02 = new JPanel();
 JPanel t03 = new JPanel();	
 JPanel t04 = new JPanel();
 JPanel t05 = new JPanel();
 JPanel t06 = new JPanel();
 JPanel t07 = new JPanel();
		
 JPanel t08 = new JPanel();
 JPanel t09 = new JPanel();
 JPanel t10 = new JPanel();
 JPanel t11 = new JPanel();
 JPanel t12 = new JPanel();
 JPanel t13 = new JPanel();
 JPanel t14 = new JPanel();
 JPanel t15 = new JPanel();

 JPanel[][] tiles = {{t00,t01,t02,t03,t04,t05,t06,t07},
					 {t08,t09,t10,t11,t12,t13,t14,t15}};
 
 //Sets specific name to the tiles
 String num;
 for (int i = 0; i < 8; i++)
 {
   num = String.valueOf(i);
   tiles[0][i].setName("0"+num);
 if (i < 2)
 {
  num = String.valueOf(i+8);
  tiles[1][i].setName("0"+num);	
 }
 else
 {
  num = String.valueOf(i+8);
  tiles[1][i].setName(num);	
 }
}
		
 //Adds the mouse listener and other attributes to the tiles
 for (int col = 0; col < 2; col ++)
 {    
  for (int row = 0; row < 8; row++)
  {
   tiles[col][row].addMouseListener(this);
   tiles[col][row].add(new JLabel(tiles[col][row].getName(), SwingConstants.CENTER));
   tiles[col][row].setFont(new Font("Arial", Font.PLAIN, 12));
   tiles[col][row].setPreferredSize(new Dimension(62, 62));
   tiles[col][row].setBackground(Color.magenta);
   tiles[col][row].setForeground(Color.black);
   
   //Calls tile holder to place the tiles on the right and or left panel
   tholder.AddTiles(tiles[col][row],row, col);
  }
 }
}
//Passes which tile is was pressed to gameWindow
@Override
public void mouseClicked(MouseEvent e) 
{
 JPanel clickedPanel = (JPanel)e.getSource(); 
 String panelName = clickedPanel.getName();
 gameWindow.tileClick(clickedPanel);
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


=======
/*
 * @author: Chester Townsend
 * Date: 2/21/2023
 * Updated: 2/23/2023
 * 
 * Code used to generate and manipulate
 * game tiles for the aMaze group project
 * */


public class Tile {
	/*
	 * May need more numbers in the future
	 * for now we're only worried about the
	 * rotation and position
	 * */
	
	private int xPos;
	private int yPos;
	private int rotate;
	
	/*
	 * Constructor for Tile
	 * For now it just takes a number to display and
	 * the piece's coordinates
	 * */
	public Tile(int pieceNum, int x, int y){
		xPos = x;
		yPos = y;
		rotate = 0;
		
	}
	
	/*
	 * Used to change the x and y coordinates
	 * of a tile.
	 * */
	public void setPosition (int x, int y) {
		xPos = x;
		yPos = y;
		return;
	}
	
	/*
	 * Methods aren't useful yet, but will be used to 
	 * change the rotation of the object in the future
	 * 
	 * Will likely need to be changed as more is revealed about
	 * the tiles. These are mostly place holders
	 * */
	public void incRotation () {
		if(rotate < 3) {
			rotate++;
			return;
		}else{
			rotate = 0;
			return;
		}
	}
	
	public void decRotation () {
		if(rotate > 0) {
			rotate--;
			return;
		}else{
			
			rotate = 3;
			return;
		}
	}
	
	
	/*
	 * Used to get positions
	 * Will be used by the pieceMover class
	 * */
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
}
