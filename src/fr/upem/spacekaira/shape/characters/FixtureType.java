package fr.upem.spacekaira.shape.characters;

/*
    This class holds constant who are used by the contact filter
 */
public class FixtureType {
    /* should be power of 2
    *  don't forget to fill the switch :)
    */
    public static final int BULLET = 0x1;
    public static final int PLANET = 0x2;
    public static final int STD_ENEMY = 0x04;
    public static final int SHIP = 0x08;
    public static final int BOMB = 0x10;
    public static final int ARMED_BOMB = 0x20;
    public static final int MBOMB = 0x40;
    public static final int ARMED_MBOMB = 0x80;
    public static final int BULLET_ENEMY = 0x100;
    public static final int SHIELD = 0x200;

    public static int typeToIndex(int fixtureType) {
        switch (fixtureType) {
            case BULLET:    return 0;
            case PLANET:    return 1;
            case STD_ENEMY: return 2;
            case SHIP:      return 3;
            case BOMB:      return 4;
            case ARMED_BOMB: return 5;
            case MBOMB:      return 6;
            case ARMED_MBOMB:return 7;
            case BULLET_ENEMY: return 8;
            case SHIELD: return 9;
            default:        return -1;
        }
    }
}
