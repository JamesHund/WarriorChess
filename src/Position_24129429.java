public class Position_24129429 {
	/* Position is an immutable data type
		which describes the position of an object on a grid */


	private final int x,y;
	
	private final int[][] neighbors = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};

	private static int[] gridSize = new int[]{0,0};
	private static boolean gridSizeSet = false; //whether gridSize has been set, grid size can only be set once

	//Takes in a reference to a universal gridSize array.
	//gridSize should be set before instantiating a Position object.
	//In that case the gridSize is the default {0, 0} and all operations on
	//the Position use this default gridSize.
	public static void setGridSize(int[] gridSize){
		if(!gridSizeSet) {
			Position_24129429.gridSize = gridSize;
			gridSizeSet = true;
		}
	}

	public Position_24129429(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	//returns the combination of two positions accounting for board wraparound 
	public Position_24129429 add(Position_24129429 p) {
		int addX = p.x;
		int addY = p.y;
		if(p.x < 0) {
			addX = p.x % gridSize[0] + gridSize[0];
		}
		if(p.y < 0) {
			addY = p.y % gridSize[1] + gridSize[1];
		}
		int newX = (x + addX) % (gridSize[0]);
		int newY = (y + addY) % (gridSize[1]);
		return new Position_24129429(newX, newY);
	}

	//returns a list of neighbouring positions
	public Position_24129429[] getNeighbors() {
		Position_24129429[] positions = new Position_24129429[8];
		for(int i = 0; i < neighbors.length ; i++) {
			int newX = neighbors[i][0];
			int newY = neighbors[i][1];
			Position_24129429 neighbour = this.add(new Position_24129429(newX,newY));
			positions[i] = neighbour;
		}
		return positions;
		
	}

	//returns true if Position p is in the neighbourhood of this position
	//this also implies the reverse is true and can therefore be used in both interpretations
	public boolean isInNeighborhood(Position_24129429 p){
		for(Position_24129429 neighbour : getNeighbors()){
			if(p.equals(neighbour)) return true;
		}
		return false;
	}

	public boolean equals(Position_24129429 p){
		return x == p.x && y == p.y;
	}

	@Override
	public String toString() {
		return "{" + x + ", " + y + "}";
		
	}
	
}
