package com.finalstand.game.traps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;

import java.awt.geom.Point2D;

/**
 * Created by Carl on 16/02/2016.
 */
public abstract class Trap
{
    protected Vector2 position;
    protected float radius;
    public static Vector2 trapSize = new Vector2(16 / FinalStand.PPM, 16 / FinalStand.PPM);

    protected Trap(float x, float y, World world)
    {
        position = new Vector2(x, y);
        radius = 32.0f; // diameter is 64px
    }
}