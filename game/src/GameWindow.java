/*
 * @author Buck Harris
 * Date: Feb 13, 2023
 * Updated: Feb 19, 2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame implements ActionListener, MouseListener
{
 public static final long serialVersionUID=1;
 public JButton lbutton, rbutton, mbutton;
 public boolean tileFlag;
 public int MoveX, MoveY;
 public TileHolder holders;
 public Board board;
 public JPanel tile;

 public GameWindow(String s)
 {
  super(s);
  GridBagLayout gbl = new GridBagLayout();
  setLayout(gbl);
 }

 public void actionPerformed(ActionEvent e) 
 {
  if("Quit".equals(e.getActionCommand()))  
  System.exit(0);
 }
 public void setUp()
 {
  GridBagConstraints basic = new GridBagConstraints();
  GridBagConstraints basic2 = new GridBagConstraints();

 //___________Adding the Piece Holders___________
  holders = new TileHolder(basic);
  holders.setGameWindow(this);
  basic.ipadx = 1;
  basic.ipady = 1;
  basic.insets = new Insets(1,50,1,50);
  basic.gridx = 0;
  basic.gridy = 0;
  basic.gridheight = 8;
  add(holders.ReturnPanel_L(), basic);
  basic.gridx = 2;
  basic.gridy = 0;
  add(holders.ReturnPanel_R(), basic);
	
  Tile tiles = new Tile(basic, holders);
  tiles.setGameWindow(this);

	
//___________Adding the Board___________
 board = new Board(basic2);
 board.setGameWindow(this);
 basic.ipadx = 0;
 basic.ipady = 0;
 basic.gridx = 1;
 basic.gridy = 1;
 basic.insets = new Insets(75, 10, 10 ,10);
 add(board.ReturnBoard(), basic);
	

//___________Adding the Buttons___________
 basic.gridx=1;
 basic.gridy=0;
 basic.ipadx = 0;
 basic.ipady = 0;
 basic.gridwidth = 1;
 basic.gridheight = 1;
 basic.insets = new Insets(1,1,1,1);
 add(this.addButtons(), basic);

 return;
 }
//___________Function to Add the Button___________
 public JPanel addButtons()
 {
  JPanel ButtonPanel = new JPanel();
  JButton[] buttons = {lbutton, mbutton, rbutton};
  String[] action = {"New Game", "Reset", "Quit"};
	
  for (int i = 0; i < 3; i++)
  {
   buttons[i] = new JButton(action[i]);
   buttons[i].setBorder(BorderFactory.createLineBorder(Color.black));
   buttons[i].setPreferredSize(new Dimension(75, 50));
   buttons[i].setFont(new Font("Arial", Font.PLAIN, 12));
   ButtonPanel.add(buttons[i]);
   buttons[i].addActionListener(this);
   buttons[i].setActionCommand(action[i]);
  }
  return ButtonPanel;
}
  //___________Called when Tile is Clicked___________
public void tileClick(JPanel tile)
{
 tileFlag = true;
 this.tile = tile;

}
//___________Called when Panel is Clicked___________
public void panelClicked(JPanel panel) 
{	  
 Character spot = panel.getName().charAt(0);
 MoveX = Character.getNumericValue(panel.getName().charAt(1));
 MoveY =  Character.getNumericValue(panel.getName().charAt(2));
 
 if (tileFlag == true)
 {
  if (spot == 'g')
  {
   board.moveTile(tile, MoveX, MoveY);
  }
  else if (spot == 'l')
  {
   holders.moveTile(tile, MoveY, 'l');
  }
  else if (spot == 'r')
  {
   holders.moveTile(tile, MoveY, 'r');
  }  
}
 tileFlag = false;
 repaint();
}
  
@Override
public void mouseClicked(MouseEvent e) {
  }

@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
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

};
