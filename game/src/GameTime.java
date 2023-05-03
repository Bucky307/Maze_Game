/**
 * GameTime class for the aMaze project.
 * This class handles the timer for the
 * game
 * @author Buck Harris
 * Date: Mar 30, 2023
 * Updated: Apr 02, 2023
 */

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
 
/**
 * A class representing a game timer displayed in a JPanel.
 * This timer shows the elapsed time in hours, minutes, and seconds.
 */
public class GameTime extends JPanel implements ActionListener 
{
 private long seconds;
 private JLabel timeLabel;
 private Timer timer;
 /**
  * Constructs a new GameTime instance, initializing the timer, label, and seconds.
  */
 public GameTime()
 {
  seconds = 0;
  timeLabel = new JLabel("00:00:00");
  timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
  add(timeLabel);

  timer = new Timer(1000, this);
 }
 /**
  * Handles the timer update event, increments the elapsed time, and updates the time label.
  * @param e The ActionEvent object triggered by the timer.
  */
 public void actionPerformed(ActionEvent e)
 {
  seconds++;
  long hours = seconds / 3600;
  long minutes = (seconds % 3600) / 60;
  long secs = seconds % 60;
  timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, secs));
 }
 /**
  * Sets the elapsed time to the given value and updates the time label.
  * @param timeInSeconds The elapsed time in seconds.
  */
 public void setTime(long timeInSeconds)
 {
  seconds = timeInSeconds;
  long hours = seconds / 3600;
  long minutes = (seconds % 3600) / 60;
  long secs = seconds % 60;
  String timeStr = String.format("%02d:%02d:%02d", hours, minutes, secs);
  timeLabel.setText(timeStr);
 }
 /**
  * Returns the current elapsed time in seconds.
  * @return The elapsed time in seconds.
  */
 public long getTime()
 {
  return seconds;
 }
 /**
  * Stops the timer from counting.
  */
 public void stop()
 {
  timer.stop();
 }
 /**
  * Starts the timer to begin counting.
  */
 public void start()
 {
  timer.start();
 }
}
