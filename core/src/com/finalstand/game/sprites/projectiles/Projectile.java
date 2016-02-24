package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Keith on 14/02/2016.
 */
public abstract class Projectile {
    protected Vector2 position;
    protected Vector2 forward;
    protected float speed;
    protected int level;

    protected Rectangle bounds;
    protected Texture texture;

    public Projectile(float x, float y, int level)
    {
        position = new Vector2(x, y);
        this.level = level;
    }

    public abstract void update();
}
