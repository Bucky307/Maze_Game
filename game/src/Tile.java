/*
 * @author: Chester Townsend
 * Date: 2/21/2023
 * Updated: 2/23/2023
 * 
 * Code used to generate and manipulate
 * game tiles for the aMaze group project
 * */


public class Tile {
	/*
	 * May need more numbers in the future
	 * for now we're only worried about the
	 * rotation and position
	 * */
	
	private int xPos;
	private int yPos;
	private int rotate;
	
	/*
	 * Constructor for Tile
	 * For now it just takes a number to display and
	 * the piece's coordinates
	 * */
	public Tile(int pieceNum, int x, int y){
		xPos = x;
		yPos = y;
		rotate = 0;
		
	}
	
	/*
	 * Used to change the x and y coordinates
	 * of a tile.
	 * */
	public void setPosition (int x, int y) {
		xPos = x;
		yPos = y;
		return;
	}
	
	/*
	 * Methods aren't useful yet, but will be used to 
	 * change the rotation of the object in the future
	 * 
	 * Will likely need to be changed as more is revealed about
	 * the tiles. These are mostly place holders
	 * */
	public void incRotation () {
		if(rotate < 3) {
			rotate++;
			return;
		}else{
			rotate = 0;
			return;
		}
	}
	
	public void decRotation () {
		if(rotate > 0) {
			rotate--;
			return;
		}else{
			
			rotate = 3;
			return;
		}
	}
	
	
	/*
	 * Used to get positions
	 * Will be used by the pieceMover class
	 * */
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
}
