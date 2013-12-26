package fr.upem.spacekaira.shape.character;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.Viewport;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;
import org.jbox2d.dynamics.joints.Joint;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Squadron extends Enemy {
    private int nBodies = 8;
    private List<Body> m_bodies = new ArrayList<>(nBodies);
    private List<Joint> m_joints = new ArrayList<>(nBodies*2);

    public Squadron(World world, int x, int y, Brush color, Brush bulletColor) {
        super(color, bulletColor);

        /*Main square*/
        {
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(x, y);
            bd.angularDamping = 25;
            body = world.createBody(bd);
            body.setUserData(this);

            PolygonShape ps = new PolygonShape();
            ps.setAsBox(2,2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density = 5.0f;
            fixtureDef.userData = enemyColor;
            fixtureDef.shape = ps;
            fixtureDef.filter.categoryBits = FixtureType.STD_ENEMY;
            fixtureDef.filter.maskBits = FixtureType.BULLET |
                    FixtureType.SHIP |
                    FixtureType.PLANET;
            body.createFixture(fixtureDef);
        }
        {
          /*Triangles*/
            PolygonShape shape = new PolygonShape();
            shape.set(new Vec2[]{new Vec2(0.0f,0.5f),new Vec2(-0.5f,-0.5f),new Vec2(0.5f,-0.5f) }, 3);

            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.angularDamping = 20;
            bd.linearDamping = 4;

            FixtureDef fd = new FixtureDef();
            fd.userData = enemyColor;
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

            Body b;

            for (int i = 0; i < nBodies; ++i) {
                float angle = MathUtils.map(i, 0, nBodies, 0, 2 * 3.1415f);

                float xx = cx + rx * (float) Math.sin(angle);
                float yy = cy + ry * (float) Math.cos(angle);

                bd.position.set(xx,yy);
                m_bodies.add(b = world.createBody(bd));
                b.createFixture(fd);

            /*joint core-triangle*/
                jd.bodyA = b;
                jd.bodyB = body;
                /*jd.localAnchorA.set(0.0f, 0.0f);
                jd.localAnchorB.set(0.0f,0.0f);*/
                p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
                p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
                d = p2.sub(p1);
                jd.length = d.length();
                world.createJoint(jd);
            }

            for (int i = 0; i < nBodies; ++i) {
            /*joint triangle-triangle_next*/
                if(i != nBodies-1) {
                    jd.bodyA = m_bodies.get(i);
                    jd.bodyB = m_bodies.get(i+1);
                    /*jd.localAnchorA.set(0.0f, 0.0f);
                    jd.localAnchorB.set(0.0f, 0.0f);*/
                    p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
                    p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
                    d = p2.sub(p1);
                    jd.length = d.length();
                    m_joints.add(world.createJoint(jd));
                }

                jd.bodyA = m_bodies.get(0);
                jd.bodyB = m_bodies.get(nBodies-1);
                /*jd.localAnchorA.set(0.0f, 0.0f);
                jd.localAnchorB.set(0.0f, 0.0f);*/
                p1 = jd.bodyA.getWorldPoint(jd.localAnchorA);
                p2 = jd.bodyB.getWorldPoint(jd.localAnchorB);
                d = p2.sub(p1);
                jd.length = d.length();
                m_joints.add(world.createJoint(jd));
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
        Vec2 speed = follow(ship.getPosition(), body.getPosition(), ship.getLinearVelocity(), body.getLinearVelocity(), 0.1f);
        body.setLinearVelocity(speed);
    }

    @Override
    public boolean isDead() {
        return body.getUserData() == Brush.DESTROY_BRUSH;
    }

    @Override
    public void computeTimeStepData() {
        super.computeTimeStepData();
        /* check for all triangles */
        Iterator<Body> it = m_bodies.iterator();
        while (it.hasNext()) {
            Body b = it.next();
            if( b.m_fixtureList.getUserData() == Brush.DESTROY_BRUSH ) {
                destroyTriangle(b);
                it.remove();
            }
        }
    }

    /* destroy the triangle and put every triangle at each equal distance between each */
    private void destroyTriangle(Body b) {
        int index = m_bodies.indexOf(b);
        DistanceJointDef jd = new DistanceJointDef();
        float length = (((DistanceJoint)(m_joints.get(0))).getLength() * m_bodies.size()) / (m_bodies.size()-1);

        int indexA, indexB;

        if(m_bodies.size() > 2) {
            if(index == 0) {
                indexA = 1;
                indexB = m_bodies.size()-1;
            }
            else if(index == m_bodies.size()-1) {
                indexA = index-1;
                indexB = 0;
            }
            else {
                indexA = index-1;
                indexB = index+1;
            }

            jd.bodyA = m_bodies.get(indexA);
            jd.bodyB = m_bodies.get(indexB);
            jd.length = length;
            body.getWorld().createJoint(jd);
        }
        body.getWorld().destroyBody(b);

        m_joints.forEach(j->(((DistanceJoint)(j)).setLength(length)));
    }

    private static long lastShootTime = 0;
    @Override
    public void shoot(Ship ship) {

        if(m_bodies.size() >= 3) {
            Map<Body,Float> lengthMap = new HashMap<>(nBodies);

            Vec2 shipSquad = ship.getPosition().sub(body.getPosition());
            shipSquad.normalize();

            for (Body b : m_bodies) {
                lengthMap.put(b, ship.getPosition().sub(b.getPosition()).length());
            }
            if (System.currentTimeMillis() - lastShootTime < 1000) return;

            int limit = (m_bodies.size() == 3)?1:3;

            lengthMap.entrySet().stream().sorted((o1, o2) -> Float.compare(o1.getValue(), o2.getValue()))
                    .limit(3).forEach( e -> {

                    bullets.add(new Bullet(
                            body.getWorld(),
                            e.getKey().getPosition().add(shipSquad.mul(2)),
                            ship.getPosition().sub(e.getKey().getPosition()),
                            e.getKey().getAngle(),
                            bulletColor));
                });
            lastShootTime = System.currentTimeMillis();
        }
    }
}