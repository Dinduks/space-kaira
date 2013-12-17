package fr.upem.spacekaira.shape.character;


import fr.upem.spacekaira.shape.AbstractShape;
import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.Collidable;
import fr.upem.spacekaira.shape.Draw;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.contacts.ContactEdge;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Represent a bullet who are a little segment
 */
public class Bullet extends AbstractShape implements Collidable{
    /**
     * Build a new bullet with a fixed velocity
     * @param world the current world
     * @param position start position of the bullet
     * @param velocity velocity vector (will be normalize then mul by a cnst)
     * @param angle the angle a the bullet
     */
    public Bullet(World world,Vec2 position,Vec2 velocity,float angle,Brush color) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(position);
        bodyDef.bullet = true;

        body = world.createBody(bodyDef);
        velocity.normalize();
        velocity = velocity.mul(60);
        body.setLinearVelocity(velocity);
        body.setTransform(position,angle);

        PolygonShape polygonShape = new PolygonShape();
        Vec2[] tab = {new Vec2(0,0),new Vec2(0.1f,0),new Vec2(0.1f,1),new Vec2(0,1)};
        polygonShape.set(tab,4);

        FixtureDef bullet = new FixtureDef();
        bullet.shape = polygonShape;
        bullet.density = 1.0f;
        bullet.userData = color;

        body.createFixture(bullet);
        body.setUserData(this);
    }

    boolean isInScreen(Draw d) {
        return  d.isInScreen(body.getPosition());
    }

    public static void checkForBulletsOutScreen(Draw d, List<Bullet> bullets) {
        Iterator<Bullet> it = bullets.iterator();

        while(it.hasNext()) {
            if(!it.next().isInScreen(d))
                it.remove();
        }
    }

    @Override
    public boolean isCollide(AbstractShape as) {
        for(ContactEdge edge = body.getContactList(); edge != null; edge = edge.next) {
            if(edge.contact.isEnabled()) {
                if(as.equalsBody(edge.other) && edge.contact.isTouching())
                    return true;
                edge.contact.setEnabled(false);
            }
        }
        return false;
    }

    /*
    public boolean isCollide(FixtureDef fix) {
        for(ContactEdge edge = body.getContactList(); edge != null; edge = edge.next) {
            if(edge.contact.isEnabled()) {
                for(Contact c = edge.contact; c != null; c=c.getNext()) {
                    c.getFixtureA().getBody()
                }
            }
        }
        return false;
    }
    */

}
