import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.awt.event.*;
import java.io.*;

public class LoadAndSave 
{
 private GameWindow gWindow;
 private String fileName;
 
 public LoadAndSave(GameWindow gWindow) 
 {
  this.gWindow = gWindow;
 }
 
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
 public void showLoadDialog() 
 {
  LoadActionListener loadActionListener = new LoadActionListener();
  loadActionListener.actionPerformed(null); 
 }
 public void showSaveDialog() 
 {
  SaveActionListener saveActionListener = new SaveActionListener();
  saveActionListener.actionPerformed(null); 
 }
 
 private class LoadActionListener implements ActionListener 
 {
  @Override
  public void actionPerformed(ActionEvent e) 
  {
   JFileChooser fileChooser = new JFileChooser();
   fileChooser.setCurrentDirectory(new File("input/"));
   int returnValue = fileChooser.showOpenDialog(null);
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

 private class SaveActionListener implements ActionListener 
 {
  @Override
  public void actionPerformed(ActionEvent e) 
  {
   JFileChooser fileChooser = new JFileChooser();
   fileChooser.setCurrentDirectory(new File("input/"));
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
       saveMaze(fileName, gWindow.unplayed, gWindow.tilePositions, gWindow.tileRotations, gWindow.lineCoords, gWindow.tileNum);
      }
     } 
     else 
     {
      gWindow.getSaveData();
      saveMaze(fileName, gWindow.unplayed, gWindow.tilePositions, gWindow.tileRotations, gWindow.lineCoords, gWindow.tileNum);
     }
    }
   }
  }
  public void saveMaze(String fileName, boolean unplayed, int[] tilePositions, int[] tileRotations, float[][] lineCoords, int[] tileNum) 
  {
   File file = new File("input/", fileName);

   try (FileOutputStream outputStream = new FileOutputStream(file)) 
   {       
    // Write the header
    int header = unplayed ?  0xcafebeef : 0xcafedeed;
    outputStream.write(ByteBuffer.allocate(4).putInt(header).array());

    // Write the number of tiles
    int numTiles = tilePositions.length;
    outputStream.write(ByteBuffer.allocate(4).putInt(numTiles).array());

    
    
    // Sort the tiles based on their numbers
    Integer[] indices = new Integer[numTiles];
    for (int i = 0; i < numTiles; i++) {
        indices[i] = i;
    }

    Arrays.sort(indices, (a, b) -> Integer.compare(tileNum[a], tileNum[b]));

    
    System.out.println("-----SaveToFile-----");

    for (int i = 0; i < 16; i++) 
    {
     System.out.println("Tile: " + tileNum[i] + "\n\tPos: "+tilePositions[i]+ " Rot: " + tileRotations[i]);
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