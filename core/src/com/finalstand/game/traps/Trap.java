package com.finalstand.game.traps;

import com.badlogic.gdx.math.Vector2;

import java.awt.geom.Point2D;

/**
 * Created by Carl on 16/02/2016.
 */
public abstract class Trap
{
    protected Vector2 position;
    protected float radius;

    protected Trap(float x, float y)
    {
        position = new Vector2(x, y);
        radius = 32.0f; // diameter is 64px
    }
}