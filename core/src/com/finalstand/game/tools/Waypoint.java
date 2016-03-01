package com.finalstand.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.InteractiveTileObject;
import com.finalstand.game.sprites.creeps.Creep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by Niall PC on 21/02/2016.
 */
public class Waypoint extends InteractiveTileObject{

    private Vector2 pos;
    private Vector2 baseDimensions;

    public Waypoint(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(FinalStand.WAYPOINT_BIT);
        pos = new Vector2(bounds.getX(), bounds.getY());
        baseDimensions = new Vector2(bounds.getWidth() / FinalStand.PPM, bounds.getHeight() / FinalStand.PPM);

        PlayScreen.waypoints.add(this);
    }

    public Vector2 getPos() { return pos; }
    public Vector2 getBaseDimensions() { return baseDimensions; }

    @Override
    public void onRightHit(Creep creep) {
        PlayScreen.waypoints.add(this);
    }

    @Override
    public void onLeftHit(Creep creep) {
        creep.setWaypointHit();
    }

    @Override
    public void onTopHit(Creep creep) {
        creep.setWaypointHit();
    }

    @Override
    public void onBottomHit(Creep creep) {
        creep.setWaypointHit();
    }

    @Override
    public void onRightRelease(Creep creep) {
        Gdx.app.log("Collison", "Waypoint right release");
    }

    @Override
    public void onLeftRelease(Creep creep) {
        Gdx.app.log("Collison", "Waypoint left release");
    }

    @Override
    public void onTopRelease(Creep creep) {
        Gdx.app.log("Collison", "Waypoint top release");
    }

    @Override
    public void onBottomRelease(Creep creep) {
        Gdx.app.log("Collison", "Waypoint bottom release");
    }

    public static Array<String> readWaypoints(String filename) {
        Array<String> direction = new Array<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            String line;
            while((line = br.readLine()) != null) {
                direction.add(line);
            }
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return direction;
    }
}


