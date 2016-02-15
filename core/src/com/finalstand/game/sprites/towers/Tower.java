package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Keith on 09/02/2016.
 */
public class Tower {
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

    public void update(){}
    public Texture getCurrentTexture(){return currentTexture;}
    public Vector2 getPosition(){return position;}
    public void upgrade()
    {
        if(level < 3)
        {
            level++;
        }
        if(level == 2)
        {
            currentTexture = level2;
        }
        if(level == 3)
        {
            currentTexture = level3;
        }
    }
}