package fr.upem.spacekaira.shape.character.collision;

import fr.upem.spacekaira.shape.Brush;
import fr.upem.spacekaira.shape.character.FixtureType;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

import java.util.function.BiConsumer;

public class MpContactListener implements ContactListener {
    interface ContactAction extends BiConsumer<Fixture,Fixture> {}

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

   private static ContactAction [][] action = {
                        /*BULLET PLANET STD_ENEMY SHIP*/
        /*BULLET*/      {nil,   rSD,    dSD,    dSD},
        /*PLANET*/      {lSD,   nil,    nil,    nil},
        /*STD_ENEMY*/   {dSD,   nil,    nil,    nil},
        /*SHIP*/        {dSD,   nil,    nil,    nil}};


    @Override
    public void beginContact(Contact contact) {
        //TODO suppress debug stuff
        /*System.out.println(contact.getFixtureA().getBody().getUserData().getClass().getName() + " & " +
                contact.getFixtureB().getBody().getUserData().getClass().getName() + "\n");*/

        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        action[FixtureType.typeToIndex(f1.getFilterData().categoryBits)][FixtureType.typeToIndex(f2.getFilterData().categoryBits)].accept(f1, f2);
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
