package fr.upem.spacekaira.shape.character.collision;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.character.FixtureType;
import fr.upem.spacekaira.shape.character.Ship;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import java.util.function.BiConsumer;

// TODO: Add Doc
public class MpContactListener implements ContactListener {
    @FunctionalInterface
    interface ContactAction extends BiConsumer<Fixture, Fixture> {}

    // TODO: Rename this
    private static ContactAction dSD =  (f1,f2) -> {
        f1.setUserData(Brush.DESTROY_BRUSH);
        f2.setUserData(Brush.DESTROY_BRUSH);
    };

    private static ContactAction lSD =  (f1,f2) -> {
        f1.setUserData(Brush.DESTROY_BRUSH);
    };

    private static ContactAction rSD =  (f1,f2) -> {
        f1.setUserData(Brush.DESTROY_BRUSH);
    };

    private static ContactAction nil =  (f1,f2) -> {};

    private static ContactAction enemyVsShip =  (f1,f2) -> {
        if (((Ship) f2.getBody().getUserData()).shield()) {
            f1.setUserData(Brush.DESTROY_BRUSH);
        }
    };

    private static ContactAction shipVsEnemy =  (f1,f2) -> {
        if (((Ship) f1.getBody().getUserData()).shield()) {
            f2.setUserData(Brush.DESTROY_BRUSH);
        }
    };

    private static ContactAction bomb = (f1, f2) -> {
        f2.setUserData(Brush.DESTROY_BRUSH);
        if (f1.getBody().getUserData() instanceof  Ship) {
            ((Ship) f1.getBody().getUserData()).addBomb();
        } else {
            ((Ship) f2.getBody().getUserData()).addBomb();
        }
    };

    private static ContactAction [][] action = {
                       /* BULLET PLANET STD_ENEMY    SHIP         BOMB, ARMED_BOMB */
        /* BULLET */    { nil,   rSD,   dSD,         dSD,         nil,  nil },
        /* PLANET */    { lSD,   nil,   nil,         nil,         nil,  nil },
        /* STD_ENEMY */ { dSD,   nil,   nil,         enemyVsShip, nil,  nil },
        /* SHIP */      { dSD,   nil,   shipVsEnemy, nil,         bomb, nil },
        /* BOMB */      { nil,   nil,   nil,         bomb,        nil,  nil },
        /* ARMED_BOMB */{ nil,   nil,   nil,         nil,         nil,  nil }
    };

    @Override
    public void beginContact(Contact contact) {
        //TODO suppress debug stuff
//        System.out.println(contact.getFixtureA().getBody().getUserData().getClass().getName() + " & " +
//                contact.getFixtureB().getBody().getUserData().getClass().getName() + "\n");

        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        // TODO: Make this readable
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
}
