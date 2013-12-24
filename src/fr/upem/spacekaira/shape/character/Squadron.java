package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.Viewport;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;

import java.awt.*;

public class Squadron extends Enemy {
    private int nBodies = 8;
    private Body m_bodies[] = new Body[nBodies];
    private Joint m_joints[] = new Joint[nBodies*2];

    public Squadron(World world, float x, float y, Brush color, Brush bulletColor) {
        super(world, x, y, color, bulletColor);

        /*Le carre principale Body de l'ennemi*/
        {
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(0, 10);
            bd.angularDamping = 25;
            body = world.createBody(bd);
            body.setUserData(this);

            PolygonShape ps = new PolygonShape();
            ps.setAsBox(2,2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density = 5.0f;
            fixtureDef.userData = ENEMY_COLOR;
            fixtureDef.shape = ps;
            fixtureDef.filter.categoryBits = FixtureType.STD_ENEMY;
            fixtureDef.filter.maskBits = FixtureType.BULLET | FixtureType.SHIP | FixtureType.PLANET;
            body.createFixture(fixtureDef);
        }
        {
          /*Les triangles*/
            PolygonShape shape = new PolygonShape();
            shape.set(new Vec2[]{new Vec2(0.0f,0.5f),new Vec2(-0.5f,-0.5f),new Vec2(0.5f,-0.5f) }, 3);

            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.angularDamping = 20;
            bd.linearDamping = 4;

            FixtureDef fd = new FixtureDef();
            fd.userData = ENEMY_COLOR;
            fd.density = 5.0f;
            fd.shape = shape;
            fd.filter.categoryBits = FixtureType.STD_ENEMY;
            fd.filter.maskBits = FixtureType.BULLET | FixtureType.SHIP | FixtureType.PLANET;

            DistanceJointDef jd = new DistanceJointDef();
            Vec2 p1,p2,d;

            float cx = 0.0f;
            float cy = 10.0f;
            float rx = 5.0f;
            float ry = 5.0f;

            for (int i = 0; i < nBodies; ++i) {
                float angle = MathUtils.map(i, 0, nBodies, 0, 2 * 3.1415f);

                float xx = cx + rx * (float) Math.sin(angle);
                float yy = cy + ry * (float) Math.cos(angle);

                bd.position.set(xx,yy);
                m_bodies[i] = world.createBody(bd);
                m_bodies[i].createFixture(fd);

            /*joint core-triangle*/
                jd.bodyA = m_bodies[i];
                jd.bodyB = body;
                /*jd.localAnchorA.set(0.0f, 0.0f);
                jd.localAnchorB.set(0.0f,0.0f);*/
                p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
                p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
                d = p2.sub(p1);
                jd.length = d.length();
                m_joints[i] = world.createJoint(jd);
            }

            for (int i = 0; i < nBodies; ++i) {
            /*joint triangle-triangle suivant*/
                if(i != nBodies-1) {
                    jd.bodyA = m_bodies[i];
                    jd.bodyB = m_bodies[i+1];
                    /*jd.localAnchorA.set(0.0f, 0.0f);
                    jd.localAnchorB.set(0.0f, 0.0f);*/
                    p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
                    p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
                    d = p2.sub(p1);
                    jd.length = d.length();
                    m_joints[i] = world.createJoint(jd);
                }

                jd.bodyA = m_bodies[0];
                jd.bodyB = m_bodies[nBodies-1];
                /*jd.localAnchorA.set(0.0f, 0.0f);
                jd.localAnchorB.set(0.0f, 0.0f);*/
                p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
                p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
                d = p2.sub(p1);
                jd.length = d.length();
                m_joints[nBodies*2-1] = world.createJoint(jd);
             }
        }

    }

    @Override
    public void draw(Graphics2D graphics, Viewport viewport) {
        super.draw(graphics, viewport);
        for (Body b : m_bodies) {
            viewport.drawPolygon(b.m_fixtureList,graphics);
        }
    }

    @Override
    public void move(Ship ship) {
        Vec2 speed = followAlgo(ship.getPosition(),body.getPosition(),ship.getLinearVelocity(),body.getLinearVelocity(),0.1f);
        body.setLinearVelocity(speed);
    }

    @Override
    public boolean isDead() {
        return false;
    }

    @Override
    public void shoot() {

    }


}
