package com.finalstand.game.towers;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Keith on 09/02/2016.
 */
public class DOTTower extends Tower{
    //fields

    public DOTTower(float x, float y)
    {
        super(x, y);

        level1 = new Texture("dot_level1.png");
        level2 = new Texture("dot_level2.png");
        level3 = new Texture("dot_level3.png");
    }

    public void update()
    {

    }

    public void render()
    {

    }
}
