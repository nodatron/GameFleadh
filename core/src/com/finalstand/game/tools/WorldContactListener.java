package com.finalstand.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.finalstand.game.FinalStand;
import com.finalstand.game.sprites.RoadBounds;
import com.finalstand.game.sprites.creeps.Creep;
import com.finalstand.game.sprites.projectiles.Projectile;
import com.finalstand.game.sprites.towers.Tower;
import com.finalstand.game.sprites.traps.Barricade;
import com.finalstand.game.sprites.traps.Bomb;

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

            case FinalStand.TOWER_BIT | FinalStand.ROADBOUNDS_BIT:{
                Gdx.app.log("World Contact Listener", "tower, roadbounds hit right");
                if(fixA.getFilterData().categoryBits == FinalStand.TOWER_BIT) {
                    ((RoadBounds) fixB.getUserData()).onTowerHit((Tower) fixA.getUserData());
                } else {
                    ((RoadBounds) fixA.getUserData()).onTowerHit((Tower) fixB.getUserData());
                }
            } break;

            case FinalStand.CREEP_BIT | FinalStand.PROJECTILE_BIT: {
//                Gdx.app.log("World Contact Listener", "tower, roadbounds hit right");
                if(fixA.getFilterData().categoryBits == FinalStand.CREEP_BIT) {
                    System.out.println(fixA.getUserData());
                    System.out.println(fixB.getUserData());
                    ((Projectile) fixB.getUserData()).onCreepProjHit((Creep) fixA.getUserData(), (Projectile) fixB.getUserData());
                } else {
                    System.out.println(fixA.getUserData());
                    System.out.println(fixB.getUserData());
                    ((Projectile) fixA.getUserData()).onCreepProjHit((Creep) fixB.getUserData(), (Projectile) fixB.getUserData());
                }
            } break;

            case FinalStand.CREEP_BIT | FinalStand.BARRICADE_BIT: {
//                Gdx.app.log("World Contact Listener", "tower, roadbounds hit right");
                if(fixA.getFilterData().categoryBits == FinalStand.CREEP_BIT) {
                    System.out.println(fixA.getUserData());
                    System.out.println(fixB.getUserData());
                    ((Barricade) fixB.getUserData()).onCreepHit((Creep) fixA.getUserData());
                } else {
                    System.out.println(fixA.getUserData());
                    System.out.println(fixB.getUserData());
                    ((Barricade) fixA.getUserData()).onCreepHit((Creep) fixB.getUserData());
                }
            } break;

            case FinalStand.CREEP_BIT | FinalStand.BOMB_BIT: {
//                Gdx.app.log("World Contact Listener", "tower, roadbounds hit right");
                if(fixA.getFilterData().categoryBits == FinalStand.CREEP_BIT) {
                    System.out.println(fixA.getUserData());
                    System.out.println(fixB.getUserData());
                    ((Bomb) fixB.getUserData()).onCreepHit((Creep) fixA.getUserData());
                } else {
                    System.out.println(fixA.getUserData());
                    System.out.println(fixB.getUserData());
                    ((Bomb) fixA.getUserData()).onCreepHit((Creep) fixB.getUserData());
                }
            } break;
        }
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

            case FinalStand.CREEP_BIT | FinalStand.PROJECTILE_BIT: {
//                Gdx.app.log("World Contact Listener", "tower, roadbounds hit right");
                if(fixA.getFilterData().categoryBits == FinalStand.CREEP_BIT) {
                    ((Projectile) fixB.getUserData()).onCreepProjRelease((Creep) fixA.getUserData(), (Projectile) fixB.getUserData());
                } else {
                    ((Projectile) fixA.getUserData()).onCreepProjRelease((Creep) fixB.getUserData(), (Projectile) fixB.getUserData());
                }
            } break;

            case FinalStand.CREEP_BIT | FinalStand.BARRICADE_BIT: {
//                Gdx.app.log("World Contact Listener", "tower, roadbounds hit right");
                if(fixA.getFilterData().categoryBits == FinalStand.CREEP_BIT) {
                    System.out.println(fixA.getUserData());
                    System.out.println(fixB.getUserData());
                    ((Barricade) fixB.getUserData()).onCreepRelease((Creep) fixA.getUserData());
                } else {
                    System.out.println(fixA.getUserData());
                    System.out.println(fixB.getUserData());
                    ((Barricade) fixA.getUserData()).onCreepRelease((Creep) fixB.getUserData());
                }
            } break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
