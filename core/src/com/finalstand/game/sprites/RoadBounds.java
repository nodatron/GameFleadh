package com.finalstand.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.sprites.creeps.Creep;
import com.finalstand.game.sprites.towers.Tower;


/**
 * Created by Niall on 2/15/2016.
 */
public class RoadBounds extends InteractiveTileObject{
    public RoadBounds(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(FinalStand.ROADBOUNDS_BIT);
    }

    @Override
    public void onRightHit(Creep creep) {
//        Gdx.app.log("Collision", "Right Road Bounds");
//        creep.unsetMovement(0);
    }

    @Override
    public void onLeftHit(Creep creep) {
//        Gdx.app.log("Collision", "Left Road Bounds");
//        creep.unsetMovement(1);
    }

    public void onTowerHit(Tower tower)
    {
        Gdx.app.log("Collision", "Tower Right Road Bounds");
    }

    @Override
    public void onTopHit(Creep creep) {
//        Gdx.app.log("Collision", "Top Road Bounds");
//        creep.unsetMovement(2);
    }

    @Override
    public void onBottomHit(Creep creep) {
//        Gdx.app.log("Collision", "Bottom Road Bounds");
//        creep.unsetMovement(3);
    }

    @Override
    public void onRightRelease(Creep creep) {
//        Gdx.app.log("Release", "Bottom Road Bounds");

    }

    @Override
    public void onLeftRelease(Creep creep) {
//        Gdx.app.log("Release", "Bottom Road Bounds");

    }

    @Override
    public void onTopRelease(Creep creep) {
//        Gdx.app.log("Release", "Bottom Road Bounds");

    }

    @Override
    public void onBottomRelease(Creep creep) {
//        Gdx.app.log("Release", "Bottom Road Bounds");

    }
}
