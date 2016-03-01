package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Keith on 09/02/2016.
 */
public class DOTTower extends Tower{

    public DOTTower(float x, float y, World world)
    {
        super(x, y, world);

        level1 = new Texture("towers/dot_level1.png");
        level2 = new Texture("towers/dot_level2.png");
        level3 = new Texture("towers/dot_level3.png");
        currentTexture = level1;


    }

    @Override
    public void update()
    {

    }
}
