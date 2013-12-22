package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.Draw;
import fr.upem.spacekaira.shape.character.factory.PlanetFactory;
import org.jbox2d.common.Vec2;
import java.util.*;

/**
 * This class compute ship data to generate automatically planet when the ship moves, and holds all planets
 */
public class PlanetGenerator {
    private Ship ship;
    private final int HEIGHT;
    private final int WIDTH;
    private final Draw draw;
    private final int density;
    private final Random rand;
    private final PlanetFactory pF;

    private Map<Integer,List<Planet>> visitedZone;

    /* a factory method to create a planet generator */
    public static PlanetGenerator newPlanetGenerator(int density,Draw draw, int WIDTH, int HEIGHT, Ship ship, PlanetFactory planetFactory) {
        PlanetGenerator pg = new PlanetGenerator(density,draw,WIDTH,HEIGHT,ship,planetFactory);
        pg.execute();
        return pg;
    }
    /* Main private constructor */
    private PlanetGenerator(int density,Draw draw, int WIDTH, int HEIGHT, Ship ship, PlanetFactory planetFactory) {
        this.density = density;
        this.draw = draw;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.ship = ship;
        this.visitedZone = new HashMap<>();
        this.rand = new Random();
        this.pF = planetFactory;
    }

    /* generate an hash code with the ship position */
    private int genShipZoneCode() {
        Vec2 pos = ship.getPosition().mul(draw.getCameraScale());
        short x = (short)(pos.x/WIDTH);
        short y = (short)(pos.y/HEIGHT);
        return x<<16|y&0xFFFF;
    }

    /* convert a zone code to an couple ( x , y ) */
    private Vec2 zoneCodeToIndex(int zoneCode) {
        return new Vec2((short)(zoneCode >> 16),(short)(zoneCode & 0xFFFF));
    }

    /* convert a couple (x , y ) to a zoneCode */
    private int indexToZoneCode(Vec2 index) {
        short x = (short)index.x;
        short y = (short)index.y;
        return x<<16|y&0xFFFF;
    }

    /* represent all zones around the ship */
    private static final List<Vec2> zone;
    static {
        zone = new ArrayList<>(8);
        zone.addAll(Arrays.asList(new Vec2(-1,+1),new Vec2(-1,0),new Vec2(-1,-1),
                new Vec2(0,+1),new Vec2(0,-1),
                new Vec2(+1,+1),new Vec2(+1,0),new Vec2(+1,-1)));
    }

    /* create new planets if is necessary */
    private void execute() {
        Vec2 shipPos = zoneCodeToIndex(genShipZoneCode());

        for(Vec2 z : zones) {
            int zoneCode = indexToZoneCode(new Vec2(shipPos.x + z.x, shipPos.y + z.y));
            if (!visitedZone.containsKey(zoneCode)) {
                visitedZone.put(zoneCode,generateZone(zoneCode));
            }
        }
    }

    /* represent all zone around the ship and the ship zone */
    private static final List<Vec2> zones;
    static {
        zones = new ArrayList<>(9);
        zones.addAll(Arrays.asList(
                new Vec2(-1,1),new Vec2(-1,0),new Vec2(-1,-1),
                new Vec2(0,1),new Vec2(0,0),new Vec2(0,-1),
                new Vec2(1,1),new Vec2(1,0),new Vec2(1,-1)));
    }

    /* return a list of list of planet who should be draw */
    private List<List<Planet>> getPlanetsToDraw() {
        execute();
        Vec2 shipPos = zoneCodeToIndex(genShipZoneCode());
        List<List<Planet>> lists = new ArrayList<>(9);

        for(Vec2 z : zones) {
            lists.add(visitedZone.get(indexToZoneCode(new Vec2(shipPos.x + z.x, shipPos.y + z.y))));
        }
        return lists;
    }

    /**
     * Return a set of planet who should be draw
     * @warnig should be call at each time step
     * @return a set of planets
     */
    public Set<Planet> getPlanetSet() {
        //TODO mettre en cache le resultat ~[9]
        List<List<Planet>> lists = getPlanetsToDraw();
        return new AbstractSet<Planet>() {
        @Override
            public Iterator<Planet> iterator() {
                return Iterators.asIterator(lists);
            }

            @Override
            public int size() {
                return lists.stream().mapToInt(List::size).sum();
            }
        };
    }

    /*
     * Generate planet for a specific zone
     * @param zoneCode the ZoneCode
     * @return a list of generated planets
     */
    private List<Planet> generateZone(int zoneCode) {
        int cameraScale = (int)draw.getCameraScale();
        int i = (WIDTH/cameraScale)/6;
        int j = (HEIGHT/cameraScale)/6;

        List<Vec2> vec2s = generateRandVec2List(6,6);
        List<Planet> planets = new ArrayList<>(density);

        Vec2 zIndex = zoneCodeToIndex(zoneCode);

        for(Vec2 v : vec2s) {
            Vec2 ve = new Vec2((v.x * i + i/2) + zIndex.x*(WIDTH/cameraScale) - (WIDTH/cameraScale)/2,
                    (v.y * j + j/2 ) + zIndex.y*(HEIGHT/cameraScale) - (HEIGHT/cameraScale)/2);
            planets.add(pF.createPlanet(ve.x,ve.y));
        }
        return planets;
    }

    /* Generate a Vec2 with int value from 0 to bound (exclude) */
    private Vec2 generateRandVec2(int boundX, int boundY) {
        return  new Vec2(rand.nextInt(boundX),rand.nextInt(boundY));
    }

    /* Generate a list(without duplicates values) of Vec2 with int value from 0 to bound (exclude) */
    private List<Vec2> generateRandVec2List(int boundX,int boundY) {
        List<Vec2> vec2s = new ArrayList<>(density);
        while (vec2s.size() != density) {
            Vec2 v = generateRandVec2(boundX,boundY);
            if(!vec2s.contains(v))
                vec2s.add(v);
        }
        return vec2s;
    }
}