public class Potion_24129429 {

    //3 types, trance causing, trance healing, invisibility
    private Position_24129429 pos;
    private potionType type;

    public enum potionType{
        TRANCE_CAUSING,
        TRANCE_HEALING,
        INVISIBILITY,
    }

    public Potion_24129429(Position_24129429 pos, int type) {
        this.pos = pos;

        Potion_24129429.potionType pot = Potion_24129429.potionType.values()[type];
        this.type = pot;
    }

    public Position_24129429 getPos() {
        return pos;
    }

    public potionType getType() {
        return type;
    }
}
