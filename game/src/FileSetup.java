/**
 * FileSetup class for the aMaze project.
 * This class handles reading from the 
 * .mze file for the tile lines
 * @author Buck Harris
 * Date: Mar 30, 2023
 * Updated: Apr 02, 2023
 */
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 * This class handles file setup and reads the maze configuration
 * from a given file. It also provides a method to get the line
 * coordinates for the maze.
 */
public class FileSetup 
{
 private String filename;
 private float[][] lineCoords;
 private boolean unplayed;
 private int[] tilePositionsOg;
 private int[] tileRotationsOg;
 private GameWindow gWindow;
 private boolean fileValid = false;

 public FileSetup(String filename, GameWindow gWindow)
 {
  this.gWindow = gWindow;
  this.filename = filename;
  File file = new File("input/", filename); 
  FileInputStream inputStream = null;
  
  try 
  {
   inputStream = new FileInputStream(file);
   
   byte[] headerBytes = new byte[4];
   inputStream.read(headerBytes);
   if (ByteBuffer.wrap(headerBytes).getInt() == 0xcafebeef)
	{unplayed = true; fileValid = true;}
   else if (ByteBuffer.wrap(headerBytes).getInt() == 0xcafedeed)
    {unplayed = false; fileValid = true;}
   else
   {   
	fileValid = false;
	JOptionPane.showMessageDialog(null, "Error opening .mze file", "Error", JOptionPane.ERROR_MESSAGE);
	//LoadAndSave loadAndSave = new LoadAndSave(gWindow);
    //loadAndSave.showLoadDialog();
   }
   
   System.out.println(fileValid);
   if(fileValid)
   {
    byte[] numTilesBytes = new byte[4];
    inputStream.read(numTilesBytes);
    int numTiles = ByteBuffer.wrap(numTilesBytes).getInt();
    
    tilePositionsOg = new int[numTiles];
    tileRotationsOg = new int[numTiles];
    lineCoords = new float[numTiles][];  

    for (int i = 0; i < numTiles; i++)
    {
     byte[] tileNumBytes = new byte[4];
	 inputStream.read(tileNumBytes);
	 int tileNum = ByteBuffer.wrap(tileNumBytes).getInt();

	 byte[] tileRotBytes = new byte[4];
	 inputStream.read(tileRotBytes);
 	 int tileRot = ByteBuffer.wrap(tileRotBytes).getInt();

	 tilePositionsOg[i] = tileNum;
	 tileRotationsOg[i] = tileRot;
	
	 byte[] numLinesBytes = new byte[4];
	 inputStream.read(numLinesBytes);
	 int numLines = ByteBuffer.wrap(numLinesBytes).getInt();

	 lineCoords[i] = new float[numLines * 4];

	 for (int j = 0; j < numLines; j++)
	 {
	  byte[] lineBytes = new byte[16];
	  inputStream.read(lineBytes);
	  ByteBuffer.wrap(lineBytes).asFloatBuffer().get(lineCoords[i], j * 4, 4);
	 
	 }
	
	 if (!unplayed)
	  System.out.printf("Num: %d, Pos: %d, Rot: %d%n",
             i, tilePositionsOg[i], tileRotationsOg[i]);
    }
   }
  }
  catch (FileNotFoundException e)
  {
   JOptionPane.showMessageDialog(null, "Error opening .mze file", "Error", JOptionPane.ERROR_MESSAGE);
   System.exit(0);
  }
  catch (IOException e)
  {
   e.printStackTrace();
  } 
  finally
  {
   if (inputStream != null)
   {   
	try
	{
	 inputStream.close();
	} 
	catch (IOException e)
	{
     e.printStackTrace();
    }
   }
  }
 }
 
 public boolean isValid()
 {
  return fileValid;
 }
 
 
 public float[][] getLineCoords() 
 {
  return lineCoords;
 }

 public boolean isUnplayed() 
 {
  return unplayed;
 }

 public int[] getTilePositionsOg()
 {
  return tilePositionsOg;
 }

 public int[] getTileRotationsOg()
 {
  return tileRotationsOg;
 }
	
}
