package com.finalstand.game.traps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.sprites.InteractiveTileObject;
import com.finalstand.game.sprites.creeps.Creep;

/**
 * Created by Carl on 16/02/2016.
 */
public abstract class Trap extends Sprite
{
    int hits;
    protected Vector2 position;
    protected Texture texture;
    protected Sprite image;
    protected float radius;
    protected int cost;

    protected World world;
    protected Body b2Body;

    public static Vector2 trapSize = new Vector2(16 / FinalStand.PPM, 16 / FinalStand.PPM);

    protected Trap(float x, float y)
    {
        position = new Vector2(x, y);
        radius = 32.0f; // diameter is 64px
        cost = 0;
        hits = 0;
    }

    public abstract void defineTrap();

    public abstract void update();
    public abstract void render();
    protected abstract void checkCollisions();

    public abstract void onCreepHit(Creep creep);
    public abstract void onCreepRelease(Creep creep);

    public Sprite getImage()
    {
        return image;
    }
}