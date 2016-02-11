package com.finalstand.game.towers;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Keith on 09/02/2016.
 */
public class AOETower extends Tower{
    //fields

    public AOETower(float x, float y)
    {
        super(x, y);

        level1 = new Texture("aoe_level1.png");
        level2 = new Texture("aoe_level2.png");
        level3 = new Texture("aoe_level3.png");
    }

    public void update()
    {

    }

    public void render()
    {

    }
}
