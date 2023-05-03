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
import javax.swing.SwingUtilities;

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
 private long timeOg = 0;

 /**
  * Constructor for the FileSetup class.
  * Initializes the FileSetup object with the given parameters.
  * @param filename The name of the file to be loaded.
  * @param gWindow The GameWindow object.
  */
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
	JOptionPane.showMessageDialog(null, "Error opening " + filename +" file", "Error", JOptionPane.ERROR_MESSAGE);
	return;
   }
   
   if(fileValid)
   {
    byte[] numTilesBytes = new byte[4];
    inputStream.read(numTilesBytes);
    int numTiles = ByteBuffer.wrap(numTilesBytes).getInt();
    
    byte[] timeOgBytes = new byte[8];
    inputStream.read(timeOgBytes);
    timeOg = (long) ByteBuffer.wrap(timeOgBytes).getDouble();

    if (unplayed)
     timeOg = 0;
     
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
    }
   }
  }
  catch (FileNotFoundException e)
  {
   JOptionPane.showMessageDialog(null, "Error opening " + filename +" file", "Error", JOptionPane.ERROR_MESSAGE);
   LoadAndSave loadAndSave = new LoadAndSave(gWindow);
   SwingUtilities.invokeLater(new LoadDialogRunnable(loadAndSave));
   return;   
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
 
 /**
  * A class that implements the Runnable interface to show the load dialog
  * of a LoadAndSave object in a separate thread.
  */
 class LoadDialogRunnable implements Runnable 
 {
  private LoadAndSave loadAndSave;
  /**
   * Constructs a LoadDialogRunnable with the given LoadAndSave object.
   *
   * @param loadAndSave the LoadAndSave object that contains the load dialog to be shown
   */
  public LoadDialogRunnable(LoadAndSave loadAndSave) 
  {
   this.loadAndSave = loadAndSave;
  }
  /**
   * Runs the showLoadDialog method of the LoadAndSave object in a separate thread.
   */
  @Override
  public void run() 
  {
   loadAndSave.showLoadDialog();
  }
 }
 
 /**
  * Returns whether the file is valid or not.
  * @return true if the file is valid, false otherwise.
  */
 public boolean isValid()
 {
  return fileValid;
 }
 /**
  * Returns a 2D array containing the line coordinates for the maze.
  * @return A 2D float array containing the line coordinates.
  */
 public float[][] getLineCoords() 
 {
  return lineCoords;
 }
 /**
  * Returns whether the FileSetup object is unplayed or not.
  * @return true if the object is unplayed, false otherwise.
  */
 public boolean isUnplayed() 
 {
  return unplayed;
 }
 /**
  * Returns an array containing the original tile positions.
  * @return An integer array containing the original tile positions.
  */
 public int[] getTilePositionsOg()
 {
  return tilePositionsOg;
 }
 /**
  * Returns an array containing the original tile rotations.
  * @return An integer array containing the original tile rotations.
  */
 public int[] getTileRotationsOg()
 {
  return tileRotationsOg;
 }
 
 public long getTimeOg()
 {
  return timeOg;
 }
 
}