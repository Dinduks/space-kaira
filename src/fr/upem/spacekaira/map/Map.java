package fr.upem.spacekaira.map;

import fr.umlv.zen3.ApplicationContext;
import fr.upem.spacekaira.shape.Draw;
import fr.upem.spacekaira.shape.character.Enemy;
import fr.upem.spacekaira.shape.character.Planet;
import fr.upem.spacekaira.shape.character.Ship;
import fr.upem.spacekaira.shape.character.TIE;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class contains all figure present on the screen
 */
public class Map {
    private final int HEIGHT;
    private final int WIDTH;

    private Ship ship;
    private List<Planet> planets;
    private List<Enemy> enemies;
    private World world;
    private Draw d;


    public Map(World world,Draw d,final int HEIGHT, final int WIDTH) {
        this.world = world;
        planets = new ArrayList<Planet>();
        enemies = new LinkedList<Enemy>();
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.d = d;
    }

    public void initMap() {
        //TODO config class pour la l'init
        ship=new Ship(world);
        planets.addAll(Arrays.asList(new Planet(world, 2, 2),new Planet(world, 10, 30),new Planet(world, 10, 100)));
        enemies.add(new TIE(world,10,10));
    }

    public Ship getShip() {
        return ship;
    }

    public void computeDataGame() {
        checkBulletOutScreen();
        checkComputedCollision();
    }

    private void checkComputedCollision() {

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
            planets.forEach(p -> p.draw(graphics, d));
            enemies.forEach(e -> e.draw(graphics, d));
        });
    }
}
