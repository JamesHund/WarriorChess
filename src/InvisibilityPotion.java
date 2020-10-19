public class InvisibilityPotion extends Potion{


    private int iterations;

    public InvisibilityPotion(Position_24129429 pos, int type, int iterations) {
        super(pos, type);
        this.iterations = iterations;
    }

    public int getIterations() {
        return iterations;
    }
}
