package com.finalstand.game.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Keith on 09/02/2016.
 */
public class DOTTower extends Tower{

    public DOTTower(float x, float y)
    {
        super(x, y);

        level1 = new Texture("dot_level1.png");
        level2 = new Texture("dot_level2.png");
        level3 = new Texture("dot_level3.png");
        currentTexture = level1;
    }

    @Override
    public void update()
    {

    }
}
