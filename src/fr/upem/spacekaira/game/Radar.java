package fr.upem.spacekaira.game;

import fr.upem.spacekaira.brush.Brush;
import fr.upem.spacekaira.shape.characters.Ship;
import fr.upem.spacekaira.shape.characters.enemies.Enemy;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;

import java.awt.*;
import java.util.List;

/**
 *  This class represent a radar to detect the position of close enemies
 */
public class Radar {
    private Ship ship;
    private List<Enemy> enemies;
    private OBBViewportTransform viewportTransform;
    private Viewport viewport;
    private int x, y, width, height;

    public Radar(Viewport viewport, Ship ship, List<Enemy> enemies) {
        this.ship = ship;
        this.enemies = enemies;

        this.x = (int)(viewport.getScreenWidth()*0.90f);
        this.y = (int)(viewport.getScreenHeight()*0.90f);
        this.width = (int)(viewport.getScreenWidth()*0.10f);
        this.height = (int)(viewport.getScreenHeight()*0.10f);

        this.viewportTransform = new OBBViewportTransform();
        this.viewportTransform.setExtents(width/2+x,height/2+y);
        this.viewportTransform.setCamera(0,0,0.05f);

        this.viewport = viewport;
    }

    /**
     * Draw the radar at the bottom of the screen
     * @param  graphics a instance of the class {@link java.awt.Graphics2D}
     */
    public void drawRadar(Graphics2D graphics) {
        viewportTransform.setCenter(ship.getPosition());
        graphics.setPaint(Color.DARK_GRAY);
        graphics.fillOval(x, y, width, height);

        graphics.setPaint(Color.GRAY);
        graphics.drawOval(x,y,width,height);
        graphics.drawOval(x+7,y+7,width-14,height-14);
        graphics.drawOval(x+15,y+15,width-30,height-30);
        graphics.drawOval(x+25,y+25,width-50,height-50);
        graphics.drawLine(x+width/2,y,x+width/2,y+height);
        graphics.drawLine(x,y+height/2,x+width,y+height/2);

        Vec2 out = new Vec2();
        graphics.setPaint(Color.blue);
        for(Enemy e : enemies) {
            Vec2 pos = e.getPosition();
            viewportTransform.getWorldToScreen(pos,out);
            if(isInRadarScreen(out)) {
                graphics.setPaint(((Brush)(e.getBody().getFixtureList().getUserData())).getColor());
                graphics.fillOval((int)out.x,(int)out.y,4,4);
            }
        }
    }

    /**
     * Checks if the specified position is in the radar or not
     * @param pos
     * @return tue if the specified position is in the radar, false otherwise
     */
    private boolean isInRadarScreen(Vec2 pos) {
        if( pos.x <= viewport.getScreenWidth()  &&
            pos.x >= x                          &&
            pos.y <= viewport.getScreenHeight() &&
            pos.y >= y )
            return true;
        return false;
    }
}
