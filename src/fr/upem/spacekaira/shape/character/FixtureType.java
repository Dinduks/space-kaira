package fr.upem.spacekaira.shape.character;

public class FixtureType {
    public static final int BULLET = 0x1;
    public static final int PLANET = 0x2;
    public static final int STD_ENEMY = 0x04;
    public static final int SHIP = 0x08;
    public static final int BOMB = 0x16;
    public static final int ARMED_BOMB = 0x32;

    public static int typeToIndex(int fixtureType) {
        switch (fixtureType) {
            case BULLET:    return 0;
            case PLANET:    return 1;
            case STD_ENEMY: return 2;
            case SHIP:      return 3;
            case BOMB:      return 4;
            case ARMED_BOMB: return 5;
            default:        return -1;
        }
    }
}
