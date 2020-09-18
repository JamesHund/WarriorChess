public class Water {

    private boolean[][] grid;

    //constructor: waterLocations is a list of positions of water objects
    //if the argument is null, the water object is initialized with an empty grid
    public Water(Position[] waterLocations){
        grid = new boolean[Game.gridSize[0]][Game.gridSize[1]]; //[x][y]
        if(waterLocations != null) {
            for (Position position : waterLocations) {
                grid[position.getX()][position.getY()] = true;
            }
        }
    }

    public void iterate(){
        boolean[][] newGrid = new boolean[Game.gridSize[0]][Game.gridSize[1]];
        //do stuff according to water rules
        for(int y = 0; y < Game.gridSize[1]; y++){
            for(int x = 0; x < Game.gridSize[0]; x++){
                int nearbyWaterCount = 0;
                for(Position neighbour : new Position(x,y).getNeighbors()){
                    if(isWaterAtPosition(neighbour)) nearbyWaterCount++;
                }
                if(grid[x][y]) {
                    if(nearbyWaterCount == 2 || nearbyWaterCount == 3){
                        newGrid[x][y] = true;
                    }
                }else{
                    if(nearbyWaterCount == 3){
                        newGrid[x][y] = true;
                    }
                }
            }
        }
        grid = newGrid;
    }

    public boolean isWaterAtPosition(Position position){
        if(grid[position.getX()][position.getY()]) return true;
        return false;
    }
}
