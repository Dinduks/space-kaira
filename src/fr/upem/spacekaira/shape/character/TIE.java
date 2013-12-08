package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.Brush;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.awt.*;

/**
 * The space ship Enemy
 */
public class TIE extends Enemy {
    static {ENEMY_COLOR = new Brush(Color.BLUE,false);}
    private static final Brush ENEMY_COLOR;

    public TIE (World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.angularDamping = 1.0f;
        bodyDef.linearDamping = 1.0f;
        bodyDef.position.set(10,10);

        body = world.createBody(bodyDef);

        FixtureDef edgeFix = new FixtureDef();
        edgeFix.density = 1.0f;
        edgeFix.userData = ENEMY_COLOR;

        //left-side-down
       {
            EdgeShape edgeShape = new EdgeShape();
            edgeShape.set(new Vec2(0,2),new Vec2(2,4));
            edgeFix.shape = edgeShape;
            body.createFixture(edgeFix);
        }

        //left-side-up
       {
            EdgeShape edgeShape = new EdgeShape();
            edgeShape.set(new Vec2(0,2),new Vec2(2,0));
            edgeFix.shape = edgeShape;
            body.createFixture(edgeFix);
        }

        //right-side-down
       {
            EdgeShape edgeShape = new EdgeShape();
            edgeShape.set(new Vec2(7,2),new Vec2(5,4));
            edgeFix.shape = edgeShape;
            body.createFixture(edgeFix);
        }

        //right-side-up
        {
            EdgeShape edgeShape = new EdgeShape();
            edgeShape.set(new Vec2(7,2),new Vec2(5,0));
            edgeFix.shape = edgeShape;
            body.createFixture(edgeFix);
        }

        //middle
        {
            EdgeShape edgeShape = new EdgeShape();
            edgeShape.set(new Vec2(2,2),new Vec2(5,2));
            edgeFix.shape = edgeShape;
            body.createFixture(edgeFix);
        }

        //inner polygon
        {
            PolygonShape polygonShape = new PolygonShape();
            Vec2[] tab = {new Vec2(0,2),new Vec2(1.5f,2.75f),new Vec2(5.5f,2.75f),new Vec2(7,2),new Vec2(5.5f,1.25f),new Vec2(1.5f,1.25f)};
            polygonShape.set(tab,6);
            edgeFix.shape = polygonShape;
            body.createFixture(edgeFix);
        }
    }

    private void shootLeft() {
        bullets.add(new Bullet(body.getWorld(),body.getWorldPoint(new Vec2(0,2)),body.getWorldVector(new Vec2(-1,0)),body.getAngle()+1.57f,BULLET_COLOR));
    }

    private void shootRight() {
        bullets.add(new Bullet(body.getWorld(),body.getWorldPoint(new Vec2(7,2)),body.getWorldVector(new Vec2(1,0)),body.getAngle()-1.57f,BULLET_COLOR));
    }
}
