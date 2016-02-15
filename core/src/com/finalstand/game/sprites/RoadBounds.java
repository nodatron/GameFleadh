package com.finalstand.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Niall on 2/15/2016.
 */
public class RoadBounds extends InteractiveTileObject{
    public RoadBounds(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
