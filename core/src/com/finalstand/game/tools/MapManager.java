package com.finalstand.game.tools;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.finalstand.game.Screens.PlayScreen;

/**
 * Created by Niall PC on 01/03/2016.
 */
public class MapManager {

    private String mapFile;
    private B2WorldCreator worldCreator;
    private String waypointFile;
    private Vector2 creepStartLocation;
    private Vector2 baseLocation;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private Array<String> dir;

    public MapManager(String mapFile, String waypointFile, World world) {
        this.mapFile = mapFile;
        this.waypointFile = waypointFile;
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(this.mapFile);
        new B2WorldCreator(world, map);
        creepStartLocation = PlayScreen.waypoints.get(0).getPos();
        baseLocation = PlayScreen.waypoints.get(PlayScreen.waypoints.size - 1).getPos();
        dir = Waypoint.readWaypoints(this.waypointFile);
    }

    public Vector2 getCreepStartLocation() { return creepStartLocation; }
    public Vector2 getBaseLocation() { return baseLocation; }
    public TiledMap getMap() { return map; }
    public Array<String> getDir() { return dir; }

    public static String getMapFileName(Integer mapNumber) {
        String filename = "";
        switch(mapNumber) {
            case 1: { filename = "map1c.tmx"; } break;
            case 2: { filename = "map2.tmx"; } break;
            case 3: { filename = "map3.tmx"; } break;
        }
        return filename;
    }

    public static String getWaypointFileName(Integer mapNumber) {
        String filename = "";
        switch(mapNumber) {
            case 1: { filename = "map1.txt"; } break;
            case 2: { filename = "map2.txt"; } break;
            case 3: { filename = "map3.txt"; } break;
        }
        return filename;
    }

}
