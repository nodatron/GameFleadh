package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Keith on 14/02/2016.
 */
public class Projectile {
    protected Vector2 position;
    protected Vector2 forward;
    protected float speed;
    protected int level;
    protected Texture projectileTexture;

    protected Rectangle bounds;
    protected Sprite sprite;

    public Projectile(float x, float y, float angle, int level)
    {
        position = new Vector2(x, y);
        this.level = level;
    }

    public void update(){}

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }
    public Texture getTexture() { return projectileTexture; }
}
