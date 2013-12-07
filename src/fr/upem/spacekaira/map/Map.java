package fr.upem.spacekaira.map;

import fr.umlv.zen3.ApplicationContext;
import fr.upem.spacekaira.shape.Draw;
import fr.upem.spacekaira.shape.character.AbstractEnemy;
import fr.upem.spacekaira.shape.character.Enemy;
import fr.upem.spacekaira.shape.character.Planet;
import fr.upem.spacekaira.shape.character.Ship;
import org.jbox2d.dynamics.World;

import java.awt.*;
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
    private  World world;


    public Map(World world,final int HEIGHT, final int WIDTH) {
        this.world = world;
        planets = new ArrayList<Planet>();
        enemies = new LinkedList<Enemy>();
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
    }

    public void initMap() {

        ship=new Ship(world);
        planets.addAll(Arrays.asList(new Planet(world, 2, 2),new Planet(world, 10, 30),new Planet(world, 10, 100)));
    }

    public Ship getShip() {
        return ship;
    }

    public void computeDataGame() {
        checkCollision();
    }

    private void checkCollision() {
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
