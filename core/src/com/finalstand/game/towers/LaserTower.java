package com.finalstand.game.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Keith on 09/02/2016.
 */
public class LaserTower extends Tower{

    public LaserTower(float x, float y)
    {
        super(x, y);

        level1 = new Texture("laserTower_level1.png");
        level2 = new Texture("laserTower_level2.png");
        level3 = new Texture("laserTower_level3_notFiring.png");
        //Texture level3Firing = new Texture("laserTower_level3_firing.png");
        currentTexture = level1;
    }

    @Override
    public void update()
    {
        /*if(level == 3 && firing)
        {
            currentTexture = level3Firing;
        }*/
    }
}
