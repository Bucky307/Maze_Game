/**
 * TileFlasher class for the aMaze project.
 * This class handles the flashing of tiles
 * when there is an invalid click
 * @author Buck Harris
 * Date: Feb 13, 2023
 * Updated: Mar 29, 2023
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;

public class TileFlasher 
{
 private static final int FLASH_DELAY = 100; // milliseconds
 private int timerCount;
 private int flashCount = 3;
 private Tile tile;
 private Timer timer;

 /**
  * Constructor for TileFlasher
  * @param tile The tile to flash
  */
 public TileFlasher(Tile tile)
 {
  this.tile = tile;
  this.timerCount = 0;
        
  timer = new Timer(FLASH_DELAY, new FlashActionListener());
  timer.start();
 }

 /**
  * Private inner class that implements ActionListener
  * to handle the flashing of the tile
  */
 private class FlashActionListener implements ActionListener
 {
  /**
  * actionPerformed method handles the flashing of the tile
  * @param e ActionEvent
  */
  @Override
  public void actionPerformed(ActionEvent e)
  {
   timerCount++;

   if (timerCount % 2 == 0)
   {
	tile.setBackground(new Color(175, 175, 175));
   } 
   else 
   {
	tile.setBackground(new Color(100, 100, 100));
   }

   if (timerCount >= flashCount * 2) 
   {
    timer.stop();
   }

   tile.repaint();
  }
 }
}