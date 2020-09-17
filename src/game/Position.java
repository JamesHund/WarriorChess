package game;

public class Position {

	private final int x,y;
	
	private final int[][] neighbors = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
	
	public Position(int x, int y) {
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
	public Position add(Position p) {
		//System.out.printf("Adding: %s and %s\n",this,p);
		int addX = p.x;
		int addY = p.y;
		if(p.x < 0) {
			addX = p.x % Game.gridSize[0] + Game.gridSize[0];
		}
		//System.out.println(p.x % Game.gridSize[0];
		if(p.y < 0) {
			addY = p.y % Game.gridSize[1] + Game.gridSize[1];
		}
		//System.out.println(p.y % Game.gridSize[1]);
		int newX = (x + addX) % (Game.gridSize[0]);
		int newY = (y + addY) % (Game.gridSize[1]);
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

	public boolean isInNeighborhood(Position p){
		for(Position neighbour : getNeighbors()){
			if(p.equals(neighbour)) return true;
		}
		return false;
	}

	public boolean equals(Position p){
		if(x == p.x && y == p.y){
			return true;
		}
		return false;
	}

	@Override
	public Position clone() {
		return new Position(x,y);
	}
	
	public String toString() {
		return "{" + x + ", " + y + "}";
		
	}
	
}
