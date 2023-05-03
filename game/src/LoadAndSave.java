/**
 * LoadAndSave class handles the 
 * load and save functionality of the
 * game.
 * actions.
 * @author Buck Harris
 * Date: Mar 30, 2023
 * Updated: Apr 02, 2023
 */
import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.awt.event.*;
import java.io.*;
/**
 * This class handles the load and save
 * popup menus. It will then call other classes
 * to do the actual loading and saving.
 */
public class LoadAndSave 
{
 private GameWindow gWindow;
 private String fileName;

 /**
  * Constructor for the LoadAndSave class.
  * Initializes the LoadAndSave object with the given parameters.
  * @param gWindow The GameWindow object.
  */
 public LoadAndSave(GameWindow gWindow) 
 {
  this.gWindow = gWindow;
 }
 /**
  * Displays the file menu with Load and Save options.
  * @param component The JComponent that the file menu will be attached to.
  */
 public void showFileMenu(JComponent component) 
 {
  JPopupMenu fileMenu = new JPopupMenu();

  JMenuItem loadItem = new JMenuItem("Load");
  loadItem.addActionListener(new LoadActionListener());

  JMenuItem saveItem = new JMenuItem("Save");
  saveItem.addActionListener(new SaveActionListener());

  fileMenu.add(loadItem);
  fileMenu.add(saveItem);
  fileMenu.show(component, 1, 1);
 }
 /**
  * Shows the Load dialog to allow the user to load a maze file.
  */
 public void showLoadDialog() 
 {
  LoadActionListener loadActionListener = new LoadActionListener();
  loadActionListener.actionPerformed(null); 
 }
 /**
  * Shows the Save dialog to allow the user to save the current maze.
  */
 public void showSaveDialog() 
 {
  SaveActionListener saveActionListener = new SaveActionListener();
  saveActionListener.actionPerformed(null); 
 }
 /**
  * LoadActionListener class to handle the file loading.
  */
 private class LoadActionListener implements ActionListener 
 {
  /**
  * Handles the action event for the Load button.
  * @param e The ActionEvent object triggered by the button actions.
  */
  @Override
  public void actionPerformed(ActionEvent e) 
  {
   if (GameWindow.getEdited()) 
   {
	int response = JOptionPane.showConfirmDialog(null,
	              "Do you want to save your game before loading?",
	              "Save game",
	              JOptionPane.YES_NO_CANCEL_OPTION,
	              JOptionPane.QUESTION_MESSAGE);

	if (response == JOptionPane.YES_OPTION) 
	{
	 showSaveDialog();
	} 
	else if (response == JOptionPane.CANCEL_OPTION || response == JOptionPane.CLOSED_OPTION) 
	{
	 return;
	}
   }

   JFileChooser fileChooser = new JFileChooser();
   fileChooser.setDialogTitle("Load");
   fileChooser.setCurrentDirectory(new File("input/"));  
   fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

   
   int returnValue = fileChooser.showOpenDialog(gWindow);

   if (returnValue == JFileChooser.APPROVE_OPTION)
   {
    File selectedFile = fileChooser.getSelectedFile();
    if (selectedFile != null) 
    {
     fileName = selectedFile.getName();
     gWindow.loadGame(fileName);
    }
   }
  }
 }
 /**
  * SaveActionListener class to handle the file saving.
  */
 private class SaveActionListener implements ActionListener 
 {
  /**
  * Handles the action event for the Save button.
  * @param e The ActionEvent object triggered by the button actions.
  */
  @Override
  public void actionPerformed(ActionEvent e) 
  {
   if(!gWindow.loadTiles.getisValid())
   {
    JOptionPane.showMessageDialog(null, "Cannot save, no .mze file loaded.", "Error", JOptionPane.ERROR_MESSAGE);
    return;
   }
    
   JFileChooser fileChooser = new JFileChooser();
   fileChooser.setDialogTitle("Save");
   fileChooser.setCurrentDirectory(new File("input/"));
   fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

   int returnValue = fileChooser.showSaveDialog(null);
   if (returnValue == JFileChooser.APPROVE_OPTION) 
   {
    File selectedFile = fileChooser.getSelectedFile();
    if (selectedFile != null) 
    {
     fileName = selectedFile.getName();
     File file = new File("input/", fileName);
     if (file.exists()) 
     {
      int result = JOptionPane.showConfirmDialog(null, "File already exists. Do you want to overwrite it?", "Warning", JOptionPane.YES_NO_OPTION);
      if (result == JOptionPane.YES_OPTION) 
      {
       gWindow.getSaveData();
       saveMaze(fileName, gWindow.unplayed, gWindow.tilePositions, gWindow.tileRotations, gWindow.lineCoords, gWindow.tileNum, gWindow.timeOg);
      }
      else if (result == JOptionPane.NO_OPTION)
      {
       showSaveDialog(); 
      }
     } 
     else 
     {
      gWindow.getSaveData();
      saveMaze(fileName, gWindow.unplayed, gWindow.tilePositions, gWindow.tileRotations, gWindow.lineCoords, gWindow.tileNum, gWindow.timeOg);
     }
    }
   }
  }
  /**
   * Saves the maze to a file with the specified parameters.
   * @param fileName The name of the file to save the maze data to.
   * @param unplayed Whether the maze is unplayed or not.
   * @param tilePositions The positions of the tiles.
   * @param tileRotations The rotations of the tiles.
   * @param lineCoords The coordinates of the lines in the maze.
   * @param tileNum The number of tiles in the maze.
   */
  public void saveMaze(String fileName, boolean unplayed, int[] tilePositions, int[] tileRotations, float[][] lineCoords, int[] tileNum, float timeOg) 
  {
   File file = new File("input/", fileName);
   
   gWindow.setUnedited();
   try (FileOutputStream outputStream = new FileOutputStream(file)) 
   {       
    // Write the header
    int header = 0xcafedeed;
    outputStream.write(ByteBuffer.allocate(4).putInt(header).array());

    // Write the number of tiles
    int numTiles = tilePositions.length;
    outputStream.write(ByteBuffer.allocate(4).putInt(numTiles).array());
    
    // Write the timeOg variable
    outputStream.write(ByteBuffer.allocate(8).putDouble((double) timeOg).array());
    
    // Sort the tiles based on their numbers
    Integer[] indices = new Integer[numTiles];
    for (int i = 0; i < numTiles; i++) 
    {
     indices[tileNum[i]] = i;
    }

    for (int i = 0; i < 16; i++) 
    {
     // Write the tile number, tile position and tile rotation
     //outputStream.write(ByteBuffer.allocate(4).putInt(tileNum[index]).array());
     outputStream.write(ByteBuffer.allocate(4).putInt(tilePositions[indices[i]]).array());
     outputStream.write(ByteBuffer.allocate(4).putInt(tileRotations[indices[i]]).array());

     // Write the number of lines
     int numLines = lineCoords[indices[i]].length / 4;
     outputStream.write(ByteBuffer.allocate(4).putInt(numLines).array());

     // Write the line coordinates
     for (int j = 0; j < numLines; j++) 
     {
      float[] lineSegment = Arrays.copyOfRange(lineCoords[indices[i]], j * 4, j * 4 + 4);
      byte[] lineSegmentBytes = new byte[lineSegment.length * 4];
      ByteBuffer.wrap(lineSegmentBytes).asFloatBuffer().put(lineSegment);
      outputStream.write(lineSegmentBytes);
     }
    }
   }
   catch (IOException e) 
   {
	  e.printStackTrace();
	  JOptionPane.showMessageDialog(null, "Error saving maze data.", "Error", JOptionPane.ERROR_MESSAGE);
   }
  }
 }
}