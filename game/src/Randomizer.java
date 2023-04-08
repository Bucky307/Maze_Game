import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Randomizer
{

 private static  ArrayList<Integer> tileIndices;
 private static int rotations[];
	
public Randomizer()
{
 randomizeRotation();
 randomizeIndices();
}

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

public int[] getRotation()
{
 return rotations;
}
	
public ArrayList<Integer> getIndice()
{
 return tileIndices;
}
	
}
