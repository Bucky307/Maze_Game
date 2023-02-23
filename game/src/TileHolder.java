/*
 * @author: Chester Townsend
 * Date: 2/22/2023
 * Updated: 2/23/2023
 * 
 * Class used to hold the game tiles. 
 * Essentially a board tile for chess or slot
 * for solitaire. 
 * */

public class TileHolder {
	private Tile tile;
	private int xPos;
	private int yPos;
	
	/*
	 * Constructor for the tile holder
	 * for now we're worried about the 
	 * coordinates and the piece.
	 * 
	 * Empty pieces will be null for now.
	 * */
	
	public TileHolder(int x, int y, Tile tileData) {
		xPos = x;
		yPos = y;
		tile = tileData;
	}
	
	/*
	 * Both used in the movement
	 * of tiles
	 * */
	public void setTile(Tile tileData) {
		tile = tileData;
		return;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	/*
	 * Used to get the coordinates of a holder
	 * we do not care about setting them as 
	 * they should remain in the same spot
	 * */
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	
	
	
}
