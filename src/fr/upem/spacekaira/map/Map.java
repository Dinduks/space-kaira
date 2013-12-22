package fr.upem.spacekaira.map;

import fr.umlv.zen3.ApplicationContext;
import fr.upem.spacekaira.shape.Draw;
import fr.upem.spacekaira.shape.character.*;
import fr.upem.spacekaira.shape.character.factory.FactoryPool;
import fr.upem.spacekaira.util.Util;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class contains all figure present on the screen
 */
public class Map {
    private final int height;
    private final int width;

    private Ship ship;
    private List<Planet> planets;
    private List<Enemy> enemies;
    private PlanetGenerator planetGenerator;

    private World world;
    private Draw d;
    private FactoryPool factoryPool;

    public Map(World world,Draw d,final int height, final int width) {
        this.world = world;
        planets = new ArrayList<Planet>();
        enemies = new LinkedList<Enemy>();
        this.height = height;
        this.width = width;
        this.d = d;
        this.factoryPool = new FactoryPool(world);
    }

    public void initMap() {
        //TODO config class pour la l'init
        ship = factoryPool.getShipFactory().createShip(false);
        enemies.add(factoryPool.getEnemyFactory().createEnemy(10, 10));
        planetGenerator = PlanetGenerator.newPlanetGenerator(4,d,width,height,ship, factoryPool.getPlanetFactory());
    }

    public Ship getShip() {
        return ship;
    }

    public void computeDataGame() {
        checkBulletOutScreen();
        checkComputedCollision();
    }

    private void checkComputedCollision() {
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy e = it.next();
            e.computeTimeStepData();
            if (e.isDead()) {
                e.destroy();
                it.remove();
            }
        }
    }

    private void checkBulletOutScreen() {
        ship.checkForBulletOutScreen(d);
        enemies.forEach(e->e.checkForBulletOutScreen(d));
    }

    public void draw(ApplicationContext context,
                     Draw d,
                     long startTime,
                     int gameDuration) {
        context.render(graphics -> {
            //clear screen
            graphics.clearRect(0, 0, width, height);

            //draw Map
            ship.draw(graphics, d);
            planetGenerator.getPlanetSet().forEach(p-> p.draw(graphics,d));
            enemies.forEach(e -> e.draw(graphics, d));

            drawTimeCounter(graphics, startTime, gameDuration);
        });
    }

    /**
     * Draws the time countdown in the top right of the screen
     * The counter becomes red when less than 10 seconds is left
     * @param graphics
     * @param startTime
     * @param gameDuration
     */
    private void drawTimeCounter(Graphics2D graphics,
                                 long startTime,
                                 int gameDuration) {
        if (Util.getTimeLeftAsLong(startTime, gameDuration) >= 10) {
            graphics.setPaint(new Color(255, 255, 255));
        } else {
            graphics.setPaint(new Color(255, 0, 0));
        }
        Font font = new Font("arial", Font.BOLD, 30);
        graphics.setFont(font);
        String leftTime = Util.getTimeLeftAsString(startTime, gameDuration);
        graphics.drawString(leftTime, width - 80, 50);
    }
}
