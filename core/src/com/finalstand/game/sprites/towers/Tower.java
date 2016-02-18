package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.FinalStand;
import com.finalstand.game.states.PlayState;

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
    public void checkPressed()
    {
        if(Gdx.input.justTouched())
        {
            Vector3 mouse = PlayState.getWorldMousePos();
            if(mouse.x > getPosition().x && mouse.x < getPosition().x + (getCurrentTexture().getWidth() / FinalStand.PPM) &&
                    mouse.y > getPosition().y && mouse.y < getPosition().y + (getCurrentTexture().getHeight() / FinalStand.PPM))
            {
                TowerOptions();
            }
        }
    }

    public void TowerOptions()
    {

    }
}
