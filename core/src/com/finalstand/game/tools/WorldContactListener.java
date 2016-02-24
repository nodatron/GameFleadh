package com.finalstand.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.finalstand.game.FinalStand;
import com.finalstand.game.sprites.InteractiveTileObject;
import com.finalstand.game.sprites.RoadBounds;
import com.finalstand.game.sprites.creeps.Creep;

/**
 * Created by Niall PC on 18/02/2016.
 */
public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int catDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (catDef) {
            case FinalStand.RIGHT_BOUND_BIT | FinalStand.ROADBOUNDS_BIT: {
                if(fixA.getFilterData().categoryBits == FinalStand.RIGHT_BOUND_BIT) {
                    ((RoadBounds) fixB.getUserData()).onRightHit((Creep) fixA.getUserData());
                } else {
                    ((RoadBounds) fixA.getUserData()).onRightHit((Creep) fixB.getUserData());
                }
            } break;

            case FinalStand.RIGHT_BOUND_BIT | FinalStand.WAYPOINT_BIT: {
                if(fixA.getFilterData().categoryBits == FinalStand.RIGHT_BOUND_BIT) {
                    ((Waypoint) fixB.getUserData()).onRightHit((Creep) fixA.getUserData());
                } else {
                    ((Waypoint) fixA.getUserData()).onRightHit((Creep) fixB.getUserData());
                }
            } break;

            case FinalStand.LEFT_BOUND_BIT | FinalStand.ROADBOUNDS_BIT: {
                if(fixA.getFilterData().categoryBits == FinalStand.LEFT_BOUND_BIT) {
                    ((RoadBounds) fixB.getUserData()).onLeftHit((Creep) fixA.getUserData());
                } else {
                    ((RoadBounds) fixA.getUserData()).onLeftHit((Creep) fixB.getUserData());
                }
            } break;

            case FinalStand.LEFT_BOUND_BIT | FinalStand.WAYPOINT_BIT: {
                if(fixA.getFilterData().categoryBits == FinalStand.LEFT_BOUND_BIT) {
                    ((Waypoint) fixB.getUserData()).onLeftHit((Creep) fixA.getUserData());
                } else {
                    ((Waypoint) fixA.getUserData()).onLeftHit((Creep) fixB.getUserData());
                }
            } break;

            case FinalStand.TOP_BOUND_BIT | FinalStand.ROADBOUNDS_BIT: {
                if(fixA.getFilterData().categoryBits == FinalStand.TOP_BOUND_BIT) {
                    ((RoadBounds) fixB.getUserData()).onTopHit((Creep) fixA.getUserData());
                } else {
                    ((RoadBounds) fixA.getUserData()).onTopHit((Creep) fixB.getUserData());
                }
            } break;

            case FinalStand.TOP_BOUND_BIT | FinalStand.WAYPOINT_BIT: {
                if(fixA.getFilterData().categoryBits == FinalStand.TOP_BOUND_BIT) {
                    ((Waypoint) fixB.getUserData()).onTopHit((Creep) fixA.getUserData());
                } else {
                    ((Waypoint) fixA.getUserData()).onTopHit((Creep) fixB.getUserData());
                }
            } break;

            case FinalStand.BOT_BOUND_BIT | FinalStand.ROADBOUNDS_BIT: {
                if(fixA.getFilterData().categoryBits == FinalStand.BOT_BOUND_BIT) {
                    ((RoadBounds) fixB.getUserData()).onBottomHit((Creep) fixA.getUserData());
                } else {
                    ((RoadBounds) fixA.getUserData()).onBottomHit((Creep) fixB.getUserData());
                }
            } break;

            case FinalStand.BOT_BOUND_BIT | FinalStand.WAYPOINT_BIT: {
                if(fixA.getFilterData().categoryBits == FinalStand.BOT_BOUND_BIT) {
                    ((Waypoint) fixB.getUserData()).onBottomHit((Creep) fixA.getUserData());
                } else {
                    ((Waypoint) fixA.getUserData()).onBottomHit((Creep) fixB.getUserData());
                }
            } break;
        }

//        // checks if one of the fixtures is RightBound
//        if(fixA.getUserData() == "RightBound" || fixB.getUserData() == "RightBound") {
//            //Figures out which one of the fixtures is RightBound
//            Fixture rightBound = fixA.getUserData() == "RightBound" ? fixA : fixB;
//            Fixture object = rightBound == fixA ? fixB : fixA;
//
//            // if the other fixture is an instance of InteractiveTileObject we want to do collision detection
//            if(object.getUserData() instanceof InteractiveTileObject) {
//                ((InteractiveTileObject) object.getUserData()).onRightHit((Creep) fixA.getUserData());
//            }
//
//        }
//
//        // checks if one of the fixtures is LeftBound
//        if(fixA.getUserData() == "LeftBound" || fixB.getUserData() == "LeftBound") {
//            //Figures out which one of the fixtures is LeftBound
//            Fixture leftBound = fixA.getUserData() == "LeftBound" ? fixA : fixB;
//            Fixture object = leftBound == fixA ? fixB : fixA;
//
//            // if the other fixture is an instance of InteractiveTileObject we want to do collision detection
//            if(object.getUserData() instanceof InteractiveTileObject) {
//                ((InteractiveTileObject) object.getUserData()).onLeftHit((Creep) fixA.getUserData());
//            }
//
//        }
//
//        // checks if one of the fixtures is TopBound
//        if(fixA.getUserData() == "TopBound" || fixB.getUserData() == "TopBound") {
//            //Figures out which one of the fixtures is TopBound
//            Fixture topBound = fixA.getUserData() == "TopBound" ? fixA : fixB;
//            Fixture object = topBound == fixA ? fixB : fixA;
//
//            // if the other fixture is an instance of InteractiveTileObject we want to do collision detection
//            if(object.getUserData() instanceof InteractiveTileObject) {
//                ((InteractiveTileObject) object.getUserData()).onTopHit((Creep) fixA.getUserData());
//            }
//
//        }
//
//        // checks if one of the fixtures is BottomBound
//        if(fixA.getUserData() == "BottomBound" || fixB.getUserData() == "BottomBound") {
//            //Figures out which one of the fixtures is BottomBound
//            Fixture botBound = fixA.getUserData() == "BottomBound" ? fixA : fixB;
//            Fixture object = botBound == fixA ? fixB : fixA;
//
//            // if the other fixture is an instance of InteractiveTileObject we want to do collision detection
//            if(object.getUserData() instanceof InteractiveTileObject) {
//                ((InteractiveTileObject) object.getUserData()).onBottomHit((Creep) fixA.getUserData());
//            }
//
//        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int catDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (catDef) {
            case FinalStand.RIGHT_BOUND_BIT | FinalStand.ROADBOUNDS_BIT: {
                if(fixA.getFilterData().categoryBits == FinalStand.RIGHT_BOUND_BIT) {
                    ((RoadBounds) fixB.getUserData()).onRightRelease((Creep) fixA.getUserData());
                } else {
                    ((RoadBounds) fixA.getUserData()).onRightRelease((Creep) fixB.getUserData());
                }
            } break;

            case FinalStand.LEFT_BOUND_BIT | FinalStand.ROADBOUNDS_BIT: {
                if(fixA.getFilterData().categoryBits == FinalStand.LEFT_BOUND_BIT) {
                    ((RoadBounds) fixB.getUserData()).onLeftRelease((Creep) fixA.getUserData());
                } else {
                    ((RoadBounds) fixA.getUserData()).onLeftRelease((Creep) fixB.getUserData());
                }
            } break;

            case FinalStand.TOP_BOUND_BIT | FinalStand.ROADBOUNDS_BIT: {
                if(fixA.getFilterData().categoryBits == FinalStand.TOP_BOUND_BIT) {
                    ((RoadBounds) fixB.getUserData()).onTopRelease((Creep) fixA.getUserData());
                } else {
                    ((RoadBounds) fixA.getUserData()).onTopRelease((Creep) fixB.getUserData());
                }
            } break;

            case FinalStand.BOT_BOUND_BIT | FinalStand.ROADBOUNDS_BIT: {
                if(fixA.getFilterData().categoryBits == FinalStand.BOT_BOUND_BIT) {
                    ((RoadBounds) fixB.getUserData()).onBottomRelease((Creep) fixA.getUserData());
                } else {
                    ((RoadBounds) fixA.getUserData()).onBottomRelease((Creep) fixB.getUserData());
                }
            } break;
        }
//        Gdx.app.log("End Contact", "");
//        Fixture fixA = contact.getFixtureA();
//        Fixture fixB = contact.getFixtureB();
//
//        if(fixA.getUserData() == "RightBound" || fixB.getUserData() == "RightBound") {
//            //Figures out which one of the fixtures is RightBound
//            Fixture rightBound = fixA.getUserData() == "RightBound" ? fixA : fixB;
//            Fixture object = rightBound == fixA ? fixB : fixA;
//
//            // if the other fixture is an instance of InteractiveTileObject we want to do release detection
//            if(object.getUserData() instanceof InteractiveTileObject) {
//                ((InteractiveTileObject) object.getUserData()).onRightRelease((Creep) fixA.getUserData());
//            }
//
//        }
//
//        // checks if one of the fixtures is LeftBound
//        if(fixA.getUserData() == "LeftBound" || fixB.getUserData() == "LeftBound") {
//            //Figures out which one of the fixtures is LeftBound
//            Fixture leftBound = fixA.getUserData() == "LeftBound" ? fixA : fixB;
//            Fixture object = leftBound == fixA ? fixB : fixA;
//
//            // if the other fixture is an instance of InteractiveTileObject we want to do release detection
//            if(object.getUserData() instanceof InteractiveTileObject) {
//                ((InteractiveTileObject) object.getUserData()).onLeftRelease((Creep) fixA.getUserData());
//            }
//
//        }
//
//        // checks if one of the fixtures is TopBound
//        if(fixA.getUserData() == "TopBound" || fixB.getUserData() == "TopBound") {
//            //Figures out which one of the fixtures is TopBound
//            Fixture topBound = fixA.getUserData() == "TopBound" ? fixA : fixB;
//            Fixture object = topBound == fixA ? fixB : fixA;
//
//            // if the other fixture is an instance of InteractiveTileObject we want to do release detection
//            if(object.getUserData() instanceof InteractiveTileObject) {
//                ((InteractiveTileObject) object.getUserData()).onTopRelease((Creep) fixA.getUserData());
//            }
//
//        }
//
//        // checks if one of the fixtures is BottomBound
//        if(fixA.getUserData() == "BottomBound" || fixB.getUserData() == "BottomBound") {
//            //Figures out which one of the fixtures is BottomBound
//            Fixture botBound = fixA.getUserData() == "BottomBound" ? fixA : fixB;
//            Fixture object = botBound == fixA ? fixB : fixA;
//
//            // if the other fixture is an instance of InteractiveTileObject we want to do release detection
//            if(object.getUserData() instanceof InteractiveTileObject) {
//                ((InteractiveTileObject) object.getUserData()).onBottomRelease((Creep) fixA.getUserData());
//            }
//
//        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
