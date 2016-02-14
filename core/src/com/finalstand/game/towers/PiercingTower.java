package com.finalstand.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Keith on 09/02/2016.
 */
public class PiercingTower extends Tower{

    public PiercingTower(float x, float y)
    {
        super(x, y);
    }

    public void update()
    {

    }

    public Texture getCurrentTexture() {
        return currentTexture;
    }

    public Vector2 getPosition() {
        return position;
    }
}
