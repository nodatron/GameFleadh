package com.finalstand.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Keith on 09/02/2016.
 */
public abstract class Tower {
    protected Vector2 position;
    protected Rectangle bounds;

    protected int level;
    protected Texture level1;
    protected Texture level2;
    protected Texture level3;
    protected Texture currentTexture;

    public Tower(float x, float y)
    {
        position = new Vector2(x, y);
        level = 1;
    }

    public abstract void update();
    public abstract Texture getCurrentTexture();
    public abstract Vector2 getPosition();
    public abstract void upgrade();
}
