package game;

public class Position {
	
	private static Position gridSize; //stores maximum board size
	private static boolean gridSizeSet = false;

	private final int x,y;
	
	private final int[][] neighbors = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//returns true if max is being set for the first time
	//max can only be set once
	//max needs to be set at the beginning of the program for the 
	public static boolean setGridSize(int x, int y) {
		if(!gridSizeSet) {
			gridSize = new Position(x,y);
			gridSizeSet = true;
			return true;
		}
		return false;
	}
	
	//returns a copy of the maximum board size to prevent changing the values of max
	public Position getGridSize() {
		return gridSize;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	//returns the combination of two positions accounting for board wraparound 
	public Position add(Position p) {
		//System.out.printf("Adding: %s and %s\n",this,p);
		int addX = p.x;
		int addY = p.y;
		if(p.x < 0) {
			addX = p.x % gridSize.x + gridSize.x;
		}
		//System.out.println(p.x % gridSize.x);
		if(p.y < 0) {
			addY = p.y % gridSize.y + gridSize.y;
		}
		//System.out.println(p.y % gridSize.y);
		int newX = (x + addX) % (gridSize.x);
		int newY = (y + addY) % (gridSize.y);
		return new Position(newX, newY);
	}
	
	public Position[] getNeighbors() {
		Position[] positions = new Position[8];
		for(int i = 0; i < neighbors.length ; i++) {
			int newX = neighbors[i][0];
			int newY = neighbors[i][1];
			Position neighbour = this.add(new Position(newX,newY));
			positions[i] = neighbour;
		}
		return positions;
		
	}
	
	public Position copy() {
		return new Position(x,y);
	}
	
	public String toString() {
		return "x = " + x + ", y = " + y;
		
	}
	
}
