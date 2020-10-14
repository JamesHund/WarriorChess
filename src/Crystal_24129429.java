public class Crystal_24129429 {
    private static final Position_24129429[] offsets = new Position_24129429[] //
            {new Position_24129429(-1,1), new Position_24129429(1,-1), new Position_24129429(1,1), new Position_24129429(-1,-1)};

    private static final String[] types = new String[]{"Air", "Flame", "Stone", "Water"};

    private Position_24129429 pos;
    private Position_24129429[] cornerPositions;

    public Crystal_24129429(Position_24129429 pos){
        this.pos = pos;
        this.cornerPositions = new Position_24129429[offsets.length];
        //populate cornerPositions by offsetting current position
        for(int i = 0; i < offsets.length; i++){
            cornerPositions[i] = offsets[i].add(pos);
        }
    }

    public Position_24129429 getPosition() {
        return pos;
    }

    public Position_24129429[] getCornerPositions(){
        return cornerPositions;
    }

    //pass in a positions array
    public boolean isAbilityActivated(WarriorPosition_24129429[] warriorPositions){
        if(warriorPositions.length < 4) return false;

        WarriorPosition_24129429[] cornerWarriorPosition = new WarriorPosition_24129429[cornerPositions.length]; //WarriorPosition at corner position
        boolean[] warriorTypesPresent = new boolean[4];

        for(WarriorPosition_24129429 warriorPos : warriorPositions){
            for(int i = 0; i < cornerPositions.length; i++){
                if(cornerPositions[i].equals(warriorPos.getPosition())){
                    cornerWarriorPosition[i] = warriorPos;
                    if(warriorPos.getNumWarriors() != 1){
                        return false;
                    }else{
                        for(int j = 0; j < types.length; j++){
                            if(warriorPos.getNumType(types[j]) == 1) warriorTypesPresent[j] = true;
                        }
                    }
                }

            }
        }
        for(boolean typePresent: warriorTypesPresent){
            if(!typePresent) return false;
        }
        return true;
    }

}
