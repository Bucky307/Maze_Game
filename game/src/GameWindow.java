/*
 * @author Buck Harris
 * Date: Feb 13, 2023
 * Updated: Feb 19, 2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame implements ActionListener
{
public static final long serialVersionUID=1;
public JButton lbutton, rbutton, mbutton;
 
private static JPanel grid, LPanel, RPanel;
private static Tile[] tile;
private static Tile lastTileClicked = null;

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
// Initializing the class variables
 GridBagConstraints basic = new GridBagConstraints();
 grid = new JPanel(new GridBagLayout());
 LPanel = new JPanel(new GridBagLayout());
 RPanel = new JPanel(new GridBagLayout());
 tile = new Tile[16];

 for (int i = 0; i < 16; i++)
 {
  tile[i] = new Tile(i);
 }
  
// Adding the playboxes to the grid panel
 basic.ipadx = 75;
 basic.ipady = 75;
 basic.insets = new Insets(0, 0, 0, 0);
 for (int i = 0; i < 4; i ++)
 {
  basic.gridx = i;
  for (int j = 0; j < 4; j++)
  {
   basic.gridy = j;
   grid.add(new playbox(i,j), basic);
  }
 }
  
// Adding the playboxes and tiles to L&R Panels
 basic.gridx = 0;
 basic.insets = new Insets(10, 10, 10 ,10);
 for (int i = 0; i < 8; i++)
 {
  basic.gridy = i;
  tile[i] = new Tile(i);
  LPanel.add(new playbox(0,i), basic);
  ((JPanel)LPanel.getComponent(i)).add(tile[i]); 
  ((JPanel)LPanel.getComponent(i)).setBorder(null);
   
  tile[i+8] = new Tile(i+8);
  RPanel.add(new playbox(0,i), basic);
  ((JPanel)RPanel.getComponent(i)).add(tile[i+8]);
  ((JPanel)RPanel.getComponent(i)).setBorder(null);
 }

// Adding grid to the board
 basic.ipadx = 0;
 basic.ipady = 0; 
 basic.gridx = 1;
 basic.gridy = 1;
 basic.insets = new Insets(75, 10, 10 ,10);
 add(grid, basic);
  
// Adding the L&R Panels
 basic.ipadx = 1;
 basic.ipady = 1;
 basic.insets = new Insets(1,50,1,50);
 basic.gridx = 0;
 basic.gridy = 0;
 basic.gridheight = 8;
 add(LPanel,basic);
 basic.gridx = 2;
 basic.gridy = 0;
 add(RPanel, basic);
  
// Adding the Buttons
 basic.gridx=1;
 basic.gridy=0;
 basic.ipadx = 0;
 basic.ipady = 0;
 basic.gridwidth = 1;
 basic.gridheight = 1;
 basic.insets = new Insets(10,10,10,10);
 add(this.addButtons(), basic);
  
 return;
 
}
// Function to Add the Button
public JPanel addButtons()
{
 JPanel ButtonPanel = new JPanel();
 JButton[] buttons = {lbutton, mbutton, rbutton};
 String[] action = {"New Game", "Reset", "Quit"};

 for (int i = 0; i < 3; i++)
 {
  buttons[i] = new JButton(action[i]);
  buttons[i].setBorder(BorderFactory.createLineBorder(Color.black));
  buttons[i].setPreferredSize(new Dimension(100, 75));
  buttons[i].setFont(new Font("Arial", Font.PLAIN, 18));
  ButtonPanel.add(buttons[i]);
  buttons[i].addActionListener(this);
  buttons[i].setActionCommand(action[i]);
 }
 return ButtonPanel;
}
// Sets lastTileClicked when a tile is clicked
public static void tileClick(Tile tile)
{	 
 if (lastTileClicked != null) 
  lastTileClicked.setBackground(new Color(175,175,175));

 lastTileClicked = tile;
 lastTileClicked.setBackground(new Color(100, 100, 100));
}
// Moves the tile to the playbox that was clicked
public static void playboxClick(playbox pbox) 
{
 if (lastTileClicked != null) 
 {
  Container parent = lastTileClicked.getParent();
  if (parent instanceof playbox) 
  {
   ((playbox) parent).setBorder(BorderFactory.createLineBorder(Color.BLACK)); // add black border to parent playbox
   ((playbox) parent).remove(lastTileClicked);
  }
  pbox.add(lastTileClicked);
  pbox.setBorder(null); // remove border from the new playbox
  lastTileClicked.setBackground(new Color(175,175,175));
  lastTileClicked = null;
  grid.repaint();
  LPanel.repaint();
  RPanel.repaint();
 }
}

};
