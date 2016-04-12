package com.finalstand.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.sprites.creeps.Creep;
import com.finalstand.game.sprites.towers.Tower;
import com.finalstand.game.sprites.traps.Barricade;
import com.finalstand.game.sprites.traps.Bomb;
import com.finalstand.game.sprites.traps.Glue;
import com.finalstand.game.sprites.traps.Spike;


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
    }

    @Override
    public void onLeftHit(Creep creep) {
    }

    public void onTowerHit(Tower tower) {
        tower.setRoadboundsHit(true);
    }

    @Override
    public void onTopHit(Creep creep) {
    }

    @Override
    public void onBottomHit(Creep creep) {
    }

    public void onBarricadeHit(Barricade barricade) {
        barricade.setRoadboundsHit(true);
    }

    public void onGlueHit(Glue glue) {
        glue.setRoadboundsHit(true);
    }

    public void onBombHit(Bomb bomb) {
        bomb.setRoadboundsHit(true);
    }

    public void onSpikeHit(Spike spike) {
        spike.setRoadboundsHit(true);
    }
}
