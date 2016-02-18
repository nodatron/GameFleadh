package com.finalstand.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Niall on 2/15/2016.
 */
public class RoadBounds extends InteractiveTileObject{
    public RoadBounds(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onRightHit() {
        Gdx.app.log("Collision", "Right Road Bounds");
    }

    @Override
    public void onLeftHit() {
        Gdx.app.log("Collision", "Left Road Bounds");
    }

    @Override
    public void onTopHit() {
        Gdx.app.log("Collision", "Top Road Bounds");
    }

    @Override
    public void onBottomHit() {
        Gdx.app.log("Collision", "Bottom Road Bounds");
    }

    @Override
    public void onRightRelease() {
        Gdx.app.log("Release", "Bottom Road Bounds");
    }

    @Override
    public void onLeftRelease() {
        Gdx.app.log("Release", "Bottom Road Bounds");
    }

    @Override
    public void onTopRelease() {
        Gdx.app.log("Release", "Bottom Road Bounds");
    }

    @Override
    public void onBottomRelease() {
        Gdx.app.log("Release", "Bottom Road Bounds");
    }
}
