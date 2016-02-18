package com.finalstand.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.finalstand.game.sprites.InteractiveTileObject;

/**
 * Created by Niall PC on 18/02/2016.
 */
public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        // checks if one of the fixtures is RightBound
        if(fixA.getUserData() == "RightBound" || fixB.getUserData() == "RightBound") {
            //Figures out which one of the fixtures is RightBound
            Fixture rightBound = fixA.getUserData() == "RightBound" ? fixA : fixB;
            Fixture object = rightBound == fixA ? fixB : fixA;

            // if the other fixture is an instance of InteractiveTileObject we want to do collision detection
            if(object.getUserData() instanceof InteractiveTileObject) {
                ((InteractiveTileObject) object.getUserData()).onRightHit();
            }

        }

        // checks if one of the fixtures is LeftBound
        if(fixA.getUserData() == "LeftBound" || fixB.getUserData() == "LeftBound") {
            //Figures out which one of the fixtures is LeftBound
            Fixture leftBound = fixA.getUserData() == "LeftBound" ? fixA : fixB;
            Fixture object = leftBound == fixA ? fixB : fixA;

            // if the other fixture is an instance of InteractiveTileObject we want to do collision detection
            if(object.getUserData() instanceof InteractiveTileObject) {
                ((InteractiveTileObject) object.getUserData()).onLeftHit();
            }

        }

        // checks if one of the fixtures is TopBound
        if(fixA.getUserData() == "TopBound" || fixB.getUserData() == "TopBound") {
            //Figures out which one of the fixtures is TopBound
            Fixture topBound = fixA.getUserData() == "TopBound" ? fixA : fixB;
            Fixture object = topBound == fixA ? fixB : fixA;

            // if the other fixture is an instance of InteractiveTileObject we want to do collision detection
            if(object.getUserData() instanceof InteractiveTileObject) {
                ((InteractiveTileObject) object.getUserData()).onTopHit();
            }

        }

        // checks if one of the fixtures is BottomBound
        if(fixA.getUserData() == "BottomBound" || fixB.getUserData() == "BottomBound") {
            //Figures out which one of the fixtures is BottomBound
            Fixture botBound = fixA.getUserData() == "BottomBound" ? fixA : fixB;
            Fixture object = botBound == fixA ? fixB : fixA;

            // if the other fixture is an instance of InteractiveTileObject we want to do collision detection
            if(object.getUserData() instanceof InteractiveTileObject) {
                ((InteractiveTileObject) object.getUserData()).onBottomHit();
            }

        }
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("Begin Contact", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
