/**
 * Randomizer class for the aMaze project.
 * This class handles all "randomness" 
 * in the program.
 * @author Buck Harris
 * Date: Mar 30, 2023
 * Updated: Apr 02, 2023
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
 
public class Randomizer
{

 private static  ArrayList<Integer> tileIndices;
 private static int rotations[];
 /**
  * Constructor for the Randomizer class.
  * Initializes random rotations and indices for the tiles.
  */
public Randomizer()
{
 randomizeRotation();
 randomizeIndices();
}
/**
 * Randomizes the rotation values for each tile in the game.
 */
private void randomizeRotation()
{
 rotations = new int[16];
 int zeroCount = 0;

 // Ensure at least one tile gets each of the three rotations
 rotations[0] = 1;
 rotations[1] = 2;
 rotations[2] = 3;

 for (int i = 3; i < 16; i++) 
 {
  int randomRotation = (int) (Math.random() * 4);

  if (randomRotation == 0 && zeroCount < 4) 
  {
   rotations[i] = randomRotation;
   zeroCount++;
  }
  else if (randomRotation != 0) 
  {
   rotations[i] = randomRotation;
  } 
  else i--;       
 }

 // Shuffle the rotations to distribute them randomly among the tiles
 Collections.shuffle(Arrays.asList(rotations));
}
/**
 * Randomizes the tile indices.
 */
private void randomizeIndices()
{
 // Create a list of indices and shuffle it
 tileIndices = new ArrayList<>();
 for (int i = 0; i < 16; i++) 
 {
  tileIndices.add(i);
 }
 Collections.shuffle(tileIndices);

}
/**
 * Returns the array of rotation values for the tiles.
 *
 * @return The array of rotation values.
 */
public int[] getRotation()
{
 return rotations;
}
/**
 * Returns the list of randomized tile indices.
 *
 * @return The ArrayList of randomized tile indices.
 */
public ArrayList<Integer> getIndice()
{
 return tileIndices;
}
	
}
