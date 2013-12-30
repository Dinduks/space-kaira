# Space Kaïra

## The project
Make sure you're using Java 8 to build and run the project.

Ant tasks:
* `compile`: builds the project to the *classes/* directory.
* `jar`:     creates an all-in-one JAR of the game to *master-pilot.jar*.
* `javadoc`: generates the Java doc of the project to the *docs/doc* directory.
* `clean`:   wipes the compiled classes and the generated java doc.

## The game
### How to launch the game
After creating the game's JAR:

```
java -jar master-pilot.jar level.xml [hardcore]
```

Where *level.xml* is a level description file.  

### What is it about
The goal of the game is to kill all the ennemies, that come in waves, within
the time limit.

The ship's shield is toggled automatically before you hit a planet.  
In normal mode, it is also toggled before you get hit by an enemy's bullet. For
more challenge, you can disabled this by launching the game with the `hardcore`
argument.

Depending on the level's configuration, bomb may spawn around you. There's two
kinds of bombs:
* Normal ones that explode. They're gray.
* Mega bombs that implode, sucking the ennemies to their center, then destroying
them. They're gold.

### How to play
* `↑` , `←` and `→` to move the ship.
* `B` to drop bombs (if you have any).
* `S` to toggle the shield.
* `SPACE` to shoot.

### How to create levels
Level description files are formally described in the *level.xsd* file that can
be found in the *config/resources* package.

Level description files allow to set the:
* Game duration (in seconds).
* Planets density: how many planets per screen?
* Bombs spawning frequency: how many bombs per minute?
* Mega bombs rate: in 100 bombs, how many will be mega bombs?
* Bullets frequency: makes the ship shoot a bullet each N milliseconds.
* The ship's speed. The default value is 500.

In the `<enemyWaves>` tag should be declared as many `<enemyWave>`s as wanted.  
Each on of these should contains `<enemy>` tags with the name of the enemy, f.e.
`<enemy>BRUTE</enemy>`.

Example of a level description file:

```xml
<configuration>
  <gameDuration>120</gameDuration>
  <planetsDensity>1</planetsDensity>
  <bombsFrequency>30</bombsFrequency>
  <megaBombsRate>40</megaBombsRate>
  <bulletsFrequency>20</bulletsFrequency>
  <shipSpeed>500</shipSpeed>

  <enemyWaves>
    <enemyWave>
      <enemy>SQUADRON</enemy>
      <enemy>TIE</enemy>
      <enemy>ROTATINGTRIANGLE</enemy>
      <enemy>BRUTE</enemy>
      <enemy>CRUISER</enemy>
    </enemyWave>
    <enemyWave>
      <enemy>BRUTE</enemy>
      <enemy>SQUADRON</enemy>
      <enemy>ROTATINGTRIANGLE</enemy>
    </enemyWave>
  </enemyWaves>
</configuration>
```
