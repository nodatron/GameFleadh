package com.finalstand.game.towers;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Keith on 09/02/2016.
 */
public class SingleShotTower extends Tower{

    public SingleShotTower(float x, float y)
    {
        super(x, y);

        level1 = new Texture("singleshot_level1.png");
        level2 = new Texture("singleshot_level2.png");
        level3 = new Texture("singleshot_level3.png");
    }

    public void update()
    {

    }

    public void render()
    {

    }
}
