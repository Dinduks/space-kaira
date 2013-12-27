package fr.upem.spacekaira.shape.character.collision;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.character.Enemy;
import fr.upem.spacekaira.shape.character.FixtureType;
import fr.upem.spacekaira.shape.character.Ship;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
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

    private static ContactAction bomb = (f1, f2) -> {
        if (f1.getBody().getUserData() instanceof Ship) {
            ((Ship) f1.getBody().getUserData()).addBomb();
            f2.setUserData(Brush.DESTROY_BRUSH);
        } else {
            ((Ship) f2.getBody().getUserData()).addBomb();
            f1.setUserData(Brush.DESTROY_BRUSH);
        }
    };

    private static ContactAction mbomb = (f1, f2) -> {
        f2.setUserData(Brush.DESTROY_BRUSH);
        if (f1.getBody().getUserData() instanceof Ship) {
            ((Ship) f1.getBody().getUserData()).addMegaBomb();
        } else {
            ((Ship) f2.getBody().getUserData()).addMegaBomb();
        }
    };

    private static ContactAction bombOnEnemy = (f1, f2) -> {
        if (f1.getBody().getUserData() instanceof Enemy) {
            f1.setUserData(Brush.DESTROY_BRUSH);
        } else {
            f2.setUserData(Brush.DESTROY_BRUSH);
        }
    };

    private static ContactAction shieldBulletEnemy = (f1,f2) -> {
        ((Ship)f1.getBody().getUserData()).enableShield();
        f2.setUserData(Brush.DESTROY_BRUSH);
    };

    private static ContactAction bulletEnemyShield = (f1,f2) ->
            shieldBulletEnemy.accept(f2,f1);

    private static ContactAction[][] action = {
                          /* BULLET PLANET STD_ENEMY    SHIP         BOMB, ARMED_BOMB,  MBOMB, ARMED_MBOMB   BULLET_ENEMY  SHIELD*/
        /* BULLET */       { nil,   rSD,   dSD,         nil,         nil,  nil,         nil,   nil,          rSD,          nil },
        /* PLANET */       { lSD,   nil,   nil,         nil,         nil,  nil ,        nil,   nil,          lSD,          nil },
        /* STD_ENEMY */    { dSD,   nil,   nil,         enemyVsShip, nil,  bombOnEnemy, nil,   nil,          nil,          nil },
        /* SHIP */         { nil,   nil,   shipVsEnemy, nil,         bomb, nil,         mbomb, nil,          shipVsEnemy,  nil },
        /* BOMB */         { nil,   nil,   nil,         bomb,        nil,  nil,         nil,   nil,          nil,          nil },
        /* ARMED_BOMB */   { nil,   nil,   bombOnEnemy, nil,         nil,  nil,         nil,   nil,          nil,          nil },
        /* MBOMB */        { nil,   nil,   nil,         mbomb,       nil,  nil,         nil,   nil,          nil,          nil },
        /* ARMED_MBOMB */  { nil,   nil,   nil,         nil,         nil,  nil,         nil,   nil,          nil,          nil },
        /* BULLET_ENEMY */ { nil,   rSD,   nil,         enemyVsShip, nil,  nil,         nil,   nil,          nil,          bulletEnemyShield },
        /* SHIELD */       { nil,   rSD,   nil,         nil,         nil,  nil,         nil,   nil, shieldBulletEnemy,     nil }
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
        //Not use
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //Not use
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //Not use
    }

    private void debug(Contact contact) {
        System.out.println(contact.getFixtureA().getBody().getUserData().getClass().getName() + " & " +
                contact.getFixtureB().getBody().getUserData().getClass().getName() + "\n");
    }
}
