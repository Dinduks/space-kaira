package fr.upem.spacekaira.shape.character;

public class FixtureType {
    public static final int BULLET = 0x1;
    public static final int PLANET = 0x2;
    public static final int STD_ENEMY = 0x04;
    public static final int SHIP = 0x08;

    public static int typeToIndex(int fixtureType) {
        switch (fixtureType) {
            case BULLET:    return 0;
            case PLANET:    return 1;
            case STD_ENEMY: return 2;
            case SHIP:      return 3;
            default:        return -1;
        }
    }
}
