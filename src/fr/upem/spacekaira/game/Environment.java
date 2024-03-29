package fr.upem.spacekaira.game;

import fr.umlv.zen3.ApplicationContext;
import fr.upem.spacekaira.config.Configuration;
import fr.upem.spacekaira.brush.BrushFactory;
import fr.upem.spacekaira.shape.characters.*;
import fr.upem.spacekaira.shape.characters.bomb.nonarmed.AbstractBomb;
import fr.upem.spacekaira.shape.characters.bomb.nonarmed.MegaBomb;
import fr.upem.spacekaira.shape.characters.bomb.nonarmed.NormalBomb;
import fr.upem.spacekaira.shape.characters.enemies.Enemy;
import fr.upem.spacekaira.shape.characters.enemies.EnemyWavesGenerator;
import fr.upem.spacekaira.shape.characters.enemies.EnemyFactory;
import fr.upem.spacekaira.shape.characters.PlanetFactory;
import fr.upem.spacekaira.util.Util;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class contains all figure present on the screen
 */
public class Environment {
    private final int height;
    private final int width;
    private final int bombsFrequency;
    private final int megaBombsRate;
    private final PlanetFactory planetFactory;
    private final EnemyFactory enemyFactory;
    private Ship ship;
    private EnemyWavesGenerator wavesGenerator;
    private PlanetGenerator planetGenerator;
    private Radar radar;
    private World world;
    private Viewport viewport;
    private int hudXPosition;
    private int hudYPosition;
    private List<AbstractBomb> bombs = new ArrayList<>();
    private long lastTimeWasABombSpawned = 0;

    private Environment(World world, Viewport viewport, final int height,
                        final int width, Configuration config) {
        this.world = world;
        this.bombsFrequency = config.getBombsFrequency();
        this.megaBombsRate = config.getMegaBombsRate();
        this.height = height;
        this.width = width;
        this.viewport = viewport;
        this.planetFactory = new PlanetFactory(world);
        this.enemyFactory = new EnemyFactory(world);

        hudXPosition = width - 80;
        hudYPosition = 50;

    }

    public static Environment createEnvironment(World world, Viewport viewport,
                                                final int height,
                                                final int width,
                                                Configuration config) {
        Environment environment =
                new Environment(world, viewport, height, width, config);
        environment.initEnvironment(config);
        return environment;
    }

    private void initEnvironment(Configuration config) {
        /**
         * This collection is shared between the enemy waves generators and the
         * ship that passes it to exploding bombs.
         */
        List<Enemy> enemies = new LinkedList<>();

        ship = new Ship(world, enemies, BrushFactory.get(Color.BLUE),
                BrushFactory.get(Color.GREEN), !config.isHardcore(),
                config.getBulletsFrequency(), config.getShipSpeed());
        planetGenerator = PlanetGenerator.create(config.getPlanetsDensity(),
                viewport, width, height, ship, planetFactory);

        wavesGenerator = new EnemyWavesGenerator(enemyFactory, viewport,
                ship, config.getEnemyWaves(), enemies);

        radar = new Radar(viewport, ship, enemies);
    }


    /**
     * @param startTime    Start time in milliseconds
     * @param gameDuration Game duration in seconds
     * @return             Time left — Example: for 1m5s, return 1:05
     */
    public static String getTimeLeftAsString(long startTime, int gameDuration) {
        long currentTime = (long) Math.floor(System.currentTimeMillis() / 1000);
        Long startTimeInSecs = (long) Math.floor(startTime / 1000);

        int timeLeft = (int) (gameDuration - (currentTime - startTimeInSecs));
        int minutes = (timeLeft / 60);
        int seconds = timeLeft % 60;

        return String.format("%d:%02d", minutes, seconds);
    }

    /**
     * @param startTime Start time in milliseconds
     * @param gameDuration Game duration in seconds
     * @return Example: for 1m10s, returns 70
     */
    public static long getTimeLeftAsLong(long startTime, int gameDuration) {
        return (gameDuration * 1000 - System.currentTimeMillis() + startTime) / 1000;
    }

    public Ship getShip() {
        return ship;
    }

    public void computeDataGame() {
        checkBulletOutScreen();
        ship.computeTimeStepData();
        cleanDeadElements();
        moveEnemies();
        wavesGenerator.getEnemies().forEach(e -> e.shoot(ship));
        spawnABombIfNecessary();
    }

    public boolean noMoreEnemies() {
        return wavesGenerator.noMoreEnemies();
    }

    private void spawnABombIfNecessary() {
        long currentTime = System.currentTimeMillis();
        int durationBetweenEachSpawn = (60 / bombsFrequency) * 1000;
        if (currentTime - lastTimeWasABombSpawned <= durationBetweenEachSpawn)
            return;

        lastTimeWasABombSpawned = currentTime;
        Vec2 position = viewport.getRandomPositionForBomb(ship);
        Random random = new Random();
        if (random.nextInt(100) >= megaBombsRate) {
            bombs.add(NormalBomb.create(world, position));
        } else {
            bombs.add(MegaBomb.create(world, position));
        }
    }

    private void cleanDeadElements() {
        Iterator<Enemy> enemyIt = wavesGenerator.getEnemies().iterator();
        while (enemyIt.hasNext()) {
            Enemy e = enemyIt.next();
            e.computeTimeStepData();
            if (e.isDead()) {
                e.destroy();
                enemyIt.remove();
            }
        }

        Iterator<AbstractBomb> bombIt = bombs.iterator();
        while (bombIt.hasNext()) {
            AbstractBomb bomb = bombIt.next();
            if (bomb.isDead()) {
                bomb.destroy();
                bombIt.remove();
            }
        }
    }

    private void checkBulletOutScreen() {
        ship.checkForBulletOutScreen(viewport);
        wavesGenerator.getEnemies()
                .forEach(e -> e.checkForBulletOutScreen(viewport));
    }

    private void moveEnemies() {
        wavesGenerator.getEnemies().forEach(e -> e.move(ship));
    }

    public void draw(ApplicationContext context,
                     Viewport viewport,
                     long startTime,
                     int gameDuration) {
        context.render(graphics -> {
            //clear screen
            graphics.clearRect(0, 0, width, height);

            Set<Planet> planets = planetGenerator.getPlanetSet();

            bombs.forEach(b -> b.draw(graphics, viewport));
            ship.draw(graphics, viewport);
            planets.forEach(p -> p.draw(graphics, viewport));
            wavesGenerator.getEnemies()
                    .forEach(e -> e.draw(graphics, viewport));
            toggleShieldIfNearAPlanet(planets);
            if (ship.hasBomb()) updateBombInfo(graphics);
            drawEnemiesInfo(graphics);
            drawTimeCounter(graphics, startTime, gameDuration);
            radar.drawRadar(graphics);
        });
    }

    private void updateBombInfo(Graphics2D graphics) {
        Font font = new Font("arial", Font.BOLD, 19);
        graphics.setFont(font);
        if (ship.hasMegaBomb()) {
            graphics.setPaint(Color.YELLOW);
            graphics.drawString("MEGA", hudXPosition, hudYPosition + 30);
            graphics.drawString("BOMB", hudXPosition, hudYPosition + 47);
        } else {
            graphics.setPaint(Color.WHITE);
            graphics.drawString("BOMB", hudXPosition, hudYPosition + 30);
        }
    }

    private void toggleShieldIfNearAPlanet(Set<Planet> planets) {
        Vec2 shipCoords = ship.getPosition();
        planets.forEach(planet -> {
            float distance = Util.distanceBetweenVectors(shipCoords,
                    planet.getPosition());
            if (distance <= 8f) ship.enableShield();
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
        if (getTimeLeftAsLong(startTime, gameDuration) >= 10) {
            graphics.setPaint(new Color(255, 255, 255));
        } else {
            graphics.setPaint(new Color(255, 0, 0));
        }
        Font font = new Font("arial", Font.BOLD, 30);
        graphics.setFont(font);
        String leftTime = getTimeLeftAsString(startTime, gameDuration);
        graphics.drawString(leftTime, hudXPosition, hudYPosition);
    }

    private void drawEnemiesInfo(Graphics2D graphics) {
        Font font = new Font("arial", Font.BOLD, 18);
        graphics.setPaint(Color.WHITE);
        graphics.setFont(font);

        int wavesLeft = wavesGenerator.wavesLeft();
        int enemiesLeft = wavesGenerator.enemiesLeftInCurrentWave();

        String wavesLeftText = String.format("%d wave%c left", wavesLeft,
                (wavesLeft > 1) ? 's' : ' ');
        String enemiesLeftText = String.format("%d enem%s left", enemiesLeft,
                (enemiesLeft > 1) ? "ies" : "y");

        graphics.drawString(wavesLeftText, 50, hudYPosition);
        graphics.drawString(enemiesLeftText, 50, hudYPosition + 47);
    }
}
