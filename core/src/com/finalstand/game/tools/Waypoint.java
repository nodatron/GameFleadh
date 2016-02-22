package com.finalstand.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
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

    public Waypoint(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(FinalStand.WAYPOINT_BIT);

        PlayScreen.waypoints.add(this);
    }

    @Override
    public void onRightHit(Creep creep) {
        Gdx.app.log("Collison", "Waypoint right hit");
        creep.setWaypointHit();
    }

    @Override
    public void onLeftHit(Creep creep) {
        Gdx.app.log("Collison", "Waypoint left hit");
        creep.setWaypointHit();
    }

    @Override
    public void onTopHit(Creep creep) {
        Gdx.app.log("Collison", "Waypoint top hit");
        creep.setWaypointHit();
    }

    @Override
    public void onBottomHit(Creep creep) {
        Gdx.app.log("Collison", "Waypoint bottom hit");
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
