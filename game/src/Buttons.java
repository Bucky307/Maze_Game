import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Buttons implements ActionListener
{
 private JButton lbutton, rbutton, mbutton; 
 private JPanel ButtonPanel;
 private String fileName = null;
 private GameWindow gWindow;
 
 public Buttons(GridBagConstraints basic, GameWindow gWindow)
 {
  this.gWindow = gWindow;
  ButtonPanel = new JPanel();
  JButton[] buttons = {lbutton, mbutton, rbutton};
  String[] action = {"File", "Reset", "Quit"};

  /* Goes through array of button names to name each,
   * 	 
   *  gives each a command, and gives it an actionListener
   */
  for (int i = 0; i < 3; i++)
  {
   buttons[i] = new JButton(action[i]);
   buttons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
   buttons[i].setFocusPainted(false);
   buttons[i].setContentAreaFilled(false);
   buttons[i].setPreferredSize(new Dimension(100, 100));
   buttons[i].setFont(new Font("Arial", Font.PLAIN, 18));
   buttons[i].setBackground(new Color(255, 255, 255));
   ButtonPanel.add(buttons[i]);
   ButtonPanel.setBackground(new Color(255, 255, 255));
   buttons[i].addActionListener(this);
   buttons[i].setActionCommand(action[i]);
   }
  //Creates space for buttons and adds the
  basic.gridx=1; basic.gridy=0;
  basic.ipadx = 0; basic.ipady = 0;
  basic.gridwidth = 1; basic.gridheight = 1;
  basic.insets = new Insets(10,10,10,10);
  //add(ButtonPanel, basic);
 }

 public JPanel getButtonPanel()
 {
  return ButtonPanel;
 }
 public String getFileName()
 {
  return fileName;
 }

 
/**
 * This method uses actionListener to call button functions.
 *
 * @param e the class which is used to return a result of a button action.
 */
 public void actionPerformed(ActionEvent e) 
 {
  if ("Quit".equals(e.getActionCommand()))
  {
   if (!GameWindow.loadTiles.isUnplayed())
   {
    int response = JOptionPane.showConfirmDialog(null,
                   "Do you want to save your game before quitting?",
                   "Save game",
    JOptionPane.YES_NO_CANCEL_OPTION,
    JOptionPane.QUESTION_MESSAGE);

    if (response == JOptionPane.YES_OPTION) 
    {
     LoadAndSave loadAndSave = new LoadAndSave(gWindow);
     loadAndSave.showSaveDialog(); 
    } 
    else if(response == JOptionPane.NO_OPTION) 
    {
     System.exit(0);
    }
   }
  }
  if ("Reset".equals(e.getActionCommand()) && GameWindow.loadTiles.getisValid())
   GameWindow.reset(); 
  if ("File".equals(e.getActionCommand()))
  {
   LoadAndSave loadAndSave = new LoadAndSave(gWindow);
   loadAndSave.showFileMenu(ButtonPanel); 
  }  
 }
}