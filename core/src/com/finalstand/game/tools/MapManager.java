package com.finalstand.game.tools;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Niall PC on 01/03/2016.
 */
public class MapManager {

    private String mapFile;
    private String waypointFile;
    private Vector2 creepStartLocation;
    private Vector2 baseLocation;

    public MapManager(String mapFile, String waypointFile) {

    }

    public Vector2 getCreepStartLocation() { return creepStartLocation; }
    public Vector2 getBaseLocation() { return baseLocation; }
}
