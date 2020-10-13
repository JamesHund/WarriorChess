public class Crystal_24129429 {
    private static final Position_24129429[] offsets = new Position_24129429[] //
            {new Position_24129429(-1,1), new Position_24129429(1,-1), new Position_24129429(1,1), new Position_24129429(-1,-1)};

    private Position_24129429 pos;

    public Crystal_24129429(Position_24129429 pos){
        this.pos = pos;
    }

    //pass in a positions array
    public boolean isAbilityActivated(Position_24129429[] positions){
        if(positions.length < 4) return false;

        Position_24129429[] cornerPositions = new Position_24129429[offsets.length];
        boolean[] cornerOccupied = new boolean[offsets.length];
        for(int i = 0; i < offsets.length; i++){
            cornerPositions[i] = offsets[i].add(pos);
        }
        for(Position_24129429 pos : positions){
            for(int i = 0; i < cornerPositions.length; i++){
                if(cornerPositions[i].equals(pos)) cornerOccupied[i] = true;
            }
        }
        for(boolean occupied : cornerOccupied){
            if(!occupied) return false;
        }
        return true;
    }

}
