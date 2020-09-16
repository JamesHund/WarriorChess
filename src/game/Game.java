package game;
public class Game {

    public static void main(String[] args) {
    	
        String path = args[0];
        int outputVersion = Integer.parseInt(args[1]);
        if (outputVersion == 0) {
            // warrior statistics mode
        } else if (outputVersion == 1) {
            // warrior statistics with visualization mode
        }
        Position.setGridSize(5,5);
        Position p = new Position(0,0);
        for(Position neighbour : p.getNeighbors()) {
        	System.out.println(neighbour);
        }
    }

}