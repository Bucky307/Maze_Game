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


/**
 * TileFlasher class handles the event of when the user
 * inputs an invalid click. It will flash the tile
 * that they performed the click with.
 */
public class TileFlasher implements ActionListener {
    private static final int FLASH_DELAY = 80; // milliseconds
    private int timerCount;
    private int flashCount = 3;
    private Tile tile;
    private Timer timer;

    public TileFlasher(Tile tile) {
        this.tile = tile;
        this.timerCount = 0;

        timer = new Timer(FLASH_DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timerCount++;

        if (timerCount % 2 == 0) {
            tile.setBackground(new Color(175, 175, 175));
        } else {
            tile.setBackground(new Color(100, 100, 100));
        }

        if (timerCount >= flashCount * 2) {
            timer.stop();
        }

        tile.repaint();
    }
}