package fr.upem.spacekaira.map;

import fr.umlv.zen3.ApplicationContext;
import fr.upem.spacekaira.shape.Draw;
import fr.upem.spacekaira.shape.character.*;
import fr.upem.spacekaira.shape.character.factory.FactoryPool;
import org.jbox2d.dynamics.World;

import java.util.*;

/**
 * This class contains all figure present on the screen
 */
public class Map {
    private final int HEIGHT;
    private final int WIDTH;

    private Ship ship;
    private List<Planet> planets;
    private List<Enemy> enemies;
    private PlanetGenerator planetGenerator;

    private World world;
    private Draw d;
    private FactoryPool fP;

    public Map(World world,Draw d,final int HEIGHT, final int WIDTH) {
        this.world = world;
        planets = new ArrayList<Planet>();
        enemies = new LinkedList<Enemy>();
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.d = d;
        this.fP = new FactoryPool(world);
    }

    public void initMap() {
        //TODO config class pour la l'init
        ship=fP.getShipFactory().createShip(false); /* <- hard ship core*/
        enemies.add(fP.getEnemyFactory().createEnemy(10,10));
        planetGenerator = PlanetGenerator.newPlanetGenerator(4,d,WIDTH,HEIGHT,ship, fP.getPlanetFactory());
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
            if(e.isDie()) {
                e.destroy();
                it.remove();
            }
        }
    }

    private void checkBulletOutScreen() {
        ship.checkForBulletOutScreen(d);
        enemies.forEach(e->e.checkForBulletOutScreen(d));
    }

    public void draw(ApplicationContext context, Draw d) {
        context.render(graphics -> {
            //clear screen
            graphics.clearRect(0, 0, WIDTH, HEIGHT);

            //draw Map
            ship.draw(graphics, d);
            planetGenerator.getPlanetSet().forEach(p-> p.draw(graphics,d));
            enemies.forEach(e -> e.draw(graphics, d));
        });
    }
}
