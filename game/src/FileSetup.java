import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.swing.JOptionPane;

public class FileSetup 
{

 private String filename;
 private float[][] lineCoords;
	
 public FileSetup(String filename)
 {
  this.filename = filename;
  // Below take the data from the .mze file
  // and turns it into usable coordinates 
  // that we will use to paint on top of the 
  // tiles.
  File file = new File(filename);
  FileInputStream inputStream = null;
  try 
  {
   inputStream = new FileInputStream(file);

   // read number of tiles (an integer value)
   byte[] numTilesBytes = new byte[4];
   inputStream.read(numTilesBytes);
   int numTiles = ByteBuffer.wrap(numTilesBytes).getInt();
   
   //allocate memory for lineCoords array
   lineCoords = new float[numTiles][]; 

   // iterate over each tile
   for (int i = 0; i < numTiles; i++) 
   {
	// read tile number (an integer value)
	byte[] tileNumBytes = new byte[4];
	inputStream.read(tileNumBytes);
	int tileNum = ByteBuffer.wrap(tileNumBytes).getInt();

    // read number of lines on this tile (an integer value
	byte[] numLinesBytes = new byte[4];
	inputStream.read(numLinesBytes);
    int numLines = ByteBuffer.wrap(numLinesBytes).getInt();

    lineCoords[i] = new float[numLines * 4]; // allocate memory for lineCoords on this tile

    // read each line on this tile (two pairs of floats)
    for (int j = 0; j < numLines; j++) 
    {
     byte[] lineBytes = new byte[16]; // each line contains 2 pairs of 4-byte floats
     inputStream.read(lineBytes);
     ByteBuffer.wrap(lineBytes).asFloatBuffer().get(lineCoords[i], j * 4, 4); 	       
    }
   }
  } 
  ///
  /// This catch block stops the program if the maze file is missing
  ///	or inaccessible.
  /// @throws FileNotFoundException if the expected .mze file
  /// cannot be reached
  ///

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
	
 public float[][] getLineCoords()
 {
  return lineCoords;
 }
	
}
