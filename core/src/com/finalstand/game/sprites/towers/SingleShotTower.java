package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Keith on 09/02/2016.
 */
public class SingleShotTower extends Tower{

    private float towerAngle;
    private Vector2 bulletPos;

    public SingleShotTower(float x, float y, World world)
    {
        super(x, y, world);
//        bounds = new Rectangle(x, y, level1.getWidth(), level1.getHeight());

        level1 = new Texture("towers/singleshot_level1.png");
        level2 = new Texture("towers/singleshot_level2.png");
        level3 = new Texture("towers/singleshot_level3.png");
        currentTexture = level1;

        bulletPos = new Vector2(x, y);
    }

    @Override
    public void update()
    {

    }

    public Rectangle getBounds() {
        return bounds;
    }
}
