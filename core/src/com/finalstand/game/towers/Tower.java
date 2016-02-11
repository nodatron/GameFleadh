package com.finalstand.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Keith on 09/02/2016.
 */
public abstract class Tower {
    private Vector2 position;
    private Rectangle bounds;

    protected Texture level1;
    protected Texture level2;
    protected Texture level3;

    public Tower(float x, float y)
    {
        position = new Vector2(x, y);
    }

    public abstract void update();
    public abstract void render();
}
