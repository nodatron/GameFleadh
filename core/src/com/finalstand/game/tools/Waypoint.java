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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Niall PC on 21/02/2016.
 */
public class Waypoint extends InteractiveTileObject{

    public Waypoint(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        setCategoryFilter(FinalStand.WAYPOINT_BIT);

        PlayScreen.waypoints.add(this);
    }

    @Override
    public void onRightHit() {
        Gdx.app.log("Collison", "Waypoint right hit");
    }

    @Override
    public void onLeftHit() {
        Gdx.app.log("Collison", "Waypoint left hit");
    }

    @Override
    public void onTopHit() {
        Gdx.app.log("Collison", "Waypoint top hit");
    }

    @Override
    public void onBottomHit() {
        Gdx.app.log("Collison", "Waypoint bottom hit");
    }

    @Override
    public void onRightRelease() {
        Gdx.app.log("Collison", "Waypoint right release");
    }

    @Override
    public void onLeftRelease() {
        Gdx.app.log("Collison", "Waypoint left release");
    }

    @Override
    public void onTopRelease() {
        Gdx.app.log("Collison", "Waypoint top release");
    }

    @Override
    public void onBottomRelease() {
        Gdx.app.log("Collison", "Waypoint bottom release");
    }

//    public static Array<Waypoint> getMapPath(String filename, World world, TiledMap map, Rectangle bounds) {
//        Array<Waypoint> waypoints = new Array<Waypoint>();
//
//        try {
//            BufferedReader br = new BufferedReader(new FileReader("filename"));
//            String line;
//
//            while((line = br.readLine()) != null) {
//                String[] data = line.split(",");
//                Waypoint point = new Waypoint(new Vector2(Float.parseFloat(data[0]), Float.parseFloat(data[1])));
//
//            }
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//
//        return waypoints;
//    }
}
