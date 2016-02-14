package com.finalstand.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Keith on 09/02/2016.
 */
public class AOETower extends Tower{

    public AOETower(float x, float y)
    {
        super(x, y);

        level1 = new Texture("aoe_level1.png");
        level2 = new Texture("aoe_level2.png");
        level3 = new Texture("aoe_level3.png");
        currentTexture = level1;
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

    @Override
    public void upgrade() {
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
