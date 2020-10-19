public class Water_24129429 {

    private boolean[][] grid;
    private final int[] gridSize;

    //constructor: initializes empty boolean grid with size gridSize
    //takes in a reference to a universal gridSize array
    public Water_24129429(int[] gridSize){
        grid = new boolean[gridSize[0]][gridSize[1]]; //[x][y]
        this.gridSize = gridSize;
    }

    //waterLocations is a list of positions of water objects
    //this method populates the grid
    //a position in the grid has water if it is true
    public void populateGrid(Position_24129429[] waterLocations){
        if(waterLocations != null) {
            for (Position_24129429 position : waterLocations) {
                if(!grid[position.getX()][position.getY()]) {
                    grid[position.getX()][position.getY()] = true;
                }else{
                    System.out.println("Error: multiple water pieces were configured at the same position on the game grid.");
                    System.exit(0);
                }
            }
        }
    }

    //changes positions of water objects according to water rules
    public void iterate(){
        boolean[][] newGrid = new boolean[gridSize[0]][gridSize[1]];

        for(int y = 0; y < gridSize[1]; y++){
            for(int x = 0; x < gridSize[0]; x++){
                int nearbyWaterCount = 0;
                for(Position_24129429 neighbour : new Position_24129429(x,y).getNeighbors()){
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

    public boolean isWaterAtPosition(Position_24129429 position){
        return grid[position.getX()][position.getY()];
    }
}
