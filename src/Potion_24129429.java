public class Potion_24129429 {

    //3 types, trance causing, trance healing, invisibility
    private final Position_24129429 pos;
    private final potionType type;

    public enum potionType{
        TRANCE_CAUSING,
        TRANCE_HEALING,
        INVISIBILITY,
    }

    public Potion_24129429(Position_24129429 pos, int type) {
        this.pos = pos;

        this.type = potionType.values()[type];
    }

    public Position_24129429 getPosition() {
        return pos;
    }

    public potionType getType() {
        return type;
    }
}
