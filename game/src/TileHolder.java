/*
 * @author Buck Harris
 * Date: Feb 13, 2023
 * Updated: Feb 19, 2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TileHolder extends JFrame implements MouseListener
{
	
 public GridBagConstraints basic;
 public JPanel panel_L, panel_R;
 public GameWindow gameWindow;

public TileHolder(GridBagConstraints b) 
{
 basic = b;
	
 panel_L = new JPanel(new GridBagLayout());
 panel_R = new JPanel(new GridBagLayout());
 panel_L.setBorder(BorderFactory.createLineBorder(Color.black));
 panel_R.setBorder(BorderFactory.createLineBorder(Color.black));
 
 //Initialize all the "tile spots"
 JPanel s00 = new JPanel();
 JPanel s01 = new JPanel();
 JPanel s02 = new JPanel();
 JPanel s03 = new JPanel();	
 JPanel s04 = new JPanel();
 JPanel s05 = new JPanel();
 JPanel s06 = new JPanel();
 JPanel s07 = new JPanel();
		
 JPanel s08 = new JPanel();
 JPanel s09 = new JPanel();
 JPanel s10 = new JPanel();
 JPanel s11 = new JPanel();
 JPanel s12 = new JPanel();
 JPanel s13 = new JPanel();
 JPanel s14 = new JPanel();
 JPanel s15 = new JPanel();
		
		
 JPanel[][] pieces = {{s00,s01,s02,s03,s04,s05,s06,s07},
					  {s08,s09,s10,s11,s12,s13,s14,s15}};
	
//Sets a name to each of the tile spots
 String num;
 for (int i = 0; i < 8; i++)
 {
  num = String.valueOf(i);
  pieces[0][i].setName("l0"+num);
  pieces[1][i].setName("r0"+num);	
 }
		
 //Places pieces in their correct positions	
 basic.gridx = 0;
 basic.ipadx = 50;
 basic.ipady = 50;
 basic.insets = new Insets(10,10,10,10);
 for (int row = 0; row < 8; row++)
 {
		    
  basic.gridy = row;
  pieces[0][row].setBorder(BorderFactory.createLineBorder(Color.black));
  pieces[0][row].addMouseListener(this);

  panel_L.add(pieces[0][row], basic);
  pieces[1][row].setBorder(BorderFactory.createLineBorder(Color.black));
  pieces[1][row].addMouseListener(this);

  panel_R.add(pieces[1][row], basic);
 }
		   
}
//Return the left panel
public JPanel ReturnPanel_L()
{
 return panel_L;
}
//Return the right panel
public JPanel ReturnPanel_R()
{
 return panel_R;
}
//Adds the movable tiles on top of the panels
public void AddTiles(JPanel tile, int r, int c)
 {	
  basic.gridy = r;
  basic.gridx = 0;
  basic.gridwidth = 1;
  basic.gridheight = 1;
  basic.insets = new Insets(10,10,10,10);
  if (c == 0)
   {
	panel_L.add(tile, basic);
	Container container = tile.getParent();
	container.setComponentZOrder(tile, 0);
   }
   else
   {
    panel_R.add(tile, basic);
	Container container = tile.getParent();
	container.setComponentZOrder(tile, 0);		
   }		
 }

//Takes a tile and moves it to the correct panel position
public void moveTile(JPanel tile, int r, char panel)
 {
  basic.gridy = r;
  basic.gridx = 0;
  basic.gridwidth = 1;
  basic.gridheight = 1;
		
  tile.setFont(new Font("Arial", Font.PLAIN, 12));
  tile.setPreferredSize(new Dimension(62, 62));
  tile.setBackground(Color.magenta);
  tile.setForeground(Color.black);
		
  if (panel == 'l')
  {
	panel_L.add(tile, basic);
	Container container = tile.getParent();
	container.setComponentZOrder(tile, 0);

  }
  else if (panel == 'r')
  {
   panel_R.add(tile, basic);
   Container container = tile.getParent();
   container.setComponentZOrder(tile, 0);		
  }

}
	
@Override
public void mouseClicked(MouseEvent e) 
{
 JPanel clickedPanel = (JPanel)e.getSource(); 
 String panelName = clickedPanel.getName();
 gameWindow.panelClicked(clickedPanel);
}

public void setGameWindow(GameWindow gameWindow) 
{
 this.gameWindow = gameWindow;
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

}

