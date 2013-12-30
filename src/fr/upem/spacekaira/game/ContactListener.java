package fr.upem.spacekaira.game;

import fr.upem.spacekaira.brush.Brush;
import fr.upem.spacekaira.shape.characters.FixtureType;
import fr.upem.spacekaira.shape.characters.Ship;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import java.util.function.BiConsumer;

public class ContactListener implements org.jbox2d.callbacks.ContactListener {
    @FunctionalInterface
    interface ContactAction extends BiConsumer<Fixture, Fixture> {}

    // TODO: Rename this
    private static ContactAction dSD =  (f1,f2) -> {
        f1.setUserData(Brush.DESTROY_BRUSH);
        f2.setUserData(Brush.DESTROY_BRUSH);
    };

    private static ContactAction lSD = (f1,f2) -> {
        f1.setUserData(Brush.DESTROY_BRUSH);
    };

    private static ContactAction rSD = (f1,f2) -> {
        f1.setUserData(Brush.DESTROY_BRUSH);
    };

    private static ContactAction nil = (f1,f2) -> {};

    private static ContactAction shipVsEnemy = (f1, f2) -> {
        if(((Ship)(f1.getBody().getUserData())).shield()) {
            f2.setUserData(Brush.DESTROY_BRUSH);
        } else {
            f1.setUserData(Brush.DESTROY_BRUSH);
        }
    };

    private static ContactAction enemyVsShip = (f1,f2) -> {
        shipVsEnemy.accept(f2,f1);
    };

    private static ContactAction shipBomb = (f1,f2) -> {
        ((Ship) f1.getBody().getUserData()).addNormalBomb();
        f2.setUserData(Brush.DESTROY_BRUSH);
    };

    private static ContactAction bombShip = (f1,f2) -> {
        shipBomb.accept(f2,f2);
    };

    private static ContactAction shipMbomb = (f1,f2) -> {
        ((Ship) f1.getBody().getUserData()).addMegaBomb();
        f2.setUserData(Brush.DESTROY_BRUSH);
    };

    private static ContactAction mbombShip = (f1,f2) -> {
        shipMbomb.accept(f2,f1);
    };

    private static ContactAction bombEnemy = (f1,f2) -> {
        f1.setUserData(Brush.DESTROY_BRUSH);
        f2.setUserData(Brush.DESTROY_BRUSH);
    };

    private static ContactAction enemyBomb = (f1, f2) -> {
        bombEnemy.accept(f2, f1);
    };

    private static ContactAction shieldBulletEnemy = (f1,f2) -> {
        ((Ship)f1.getBody().getUserData()).enableShield();
        f2.setUserData(Brush.DESTROY_BRUSH);
    };

    private static ContactAction bulletEnemyShield = (f1,f2) ->
            shieldBulletEnemy.accept(f2,f1);

    private static ContactAction planetShield = (f1, f2) -> {
        CircleShape planet = (CircleShape)f1.getShape();
        CircleShape shield = (CircleShape)f2.getShape();
        Vec2 planetShip = f1.getBody().getWorldPoint(planet.m_p).sub(f2.getBody().getWorldPoint(shield.m_p));
        Vec2 shieldSpeed = f2.getBody().getLinearVelocity();
        planetShip.normalize();
        shieldSpeed.normalize();
        float angle = (float)Math.acos(Vec2.cross(planetShip,shieldSpeed));
        shieldSpeed.negateLocal();
        Rot.mulToOut(new Rot(angle*2),shieldSpeed,shieldSpeed);
        f2.getBody().setLinearVelocity(shieldSpeed.mul(40));
    };

    private static ContactAction shieldPlanet = (f1, f2) ->
            planetShield.accept(f2, f1);

    private static ContactAction[][] action = {
                          /* BULLET PLANET        ENEMY        SHIP         BOMB,     ARMED_BOMB, MBOMB,     ARMED_MBOMB BULLET_ENEMY       SHIELD*/
        /* BULLET */       { nil,   rSD,          dSD,         nil,         nil,      nil,        nil,       nil,        rSD,               nil },
        /* PLANET */       { lSD,   nil,          nil,         nil,         nil,      nil ,       nil,       nil,        lSD,               planetShield },
        /* ENEMY */        { dSD,   nil,          nil,         enemyVsShip, nil,      enemyBomb,  nil,       nil,        nil,               nil },
        /* SHIP */         { nil,   nil,          shipVsEnemy, nil,         shipBomb, nil,        shipMbomb, nil,        shipVsEnemy,       nil },
        /* BOMB */         { nil,   nil,          nil,         bombShip,    nil,      nil,        nil,       nil,        nil,               nil },
        /* ARMED_BOMB */   { nil,   nil,          bombEnemy,   nil,         nil,      nil,        nil,       nil,        nil,               nil },
        /* MBOMB */        { nil,   nil,          nil,         mbombShip,   nil,      nil,        nil,       nil,        nil,               nil },
        /* ARMED_MBOMB */  { nil,   nil,          nil,         nil,         nil,      nil,        nil,       nil,        nil,               nil },
        /* BULLET_ENEMY */ { nil,   rSD,          nil,         enemyVsShip, nil,      nil,        nil,       nil,        nil,               bulletEnemyShield },
        /* SHIELD */       { nil,   shieldPlanet, nil,         nil,         nil,      nil,        nil,       nil,        shieldBulletEnemy, nil }
    };

    @Override
    public void beginContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        action[FixtureType.typeToIndex(f1.getFilterData().categoryBits)]
              [FixtureType.typeToIndex(f2.getFilterData().categoryBits)].accept(f1, f2);
    }

    @Override
    public void endContact(Contact contact) {
        //Not used
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //Not used
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //Not used
    }
}
