package com.finalstand.game.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Keith on 09/02/2016.
 */
public class SingleShotTower extends Tower{

    private float towerAngle;
    private Vector2 bulletPos;

    public SingleShotTower(float x, float y)
    {
        super(x, y);
//        bounds = new Rectangle(x, y, level1.getWidth(), level1.getHeight());

        level1 = new Texture("singleshot_level1.png");
        level2 = new Texture("singleshot_level2.png");
        level3 = new Texture("singleshot_level3.png");
        currentTexture = level1;

        bulletPos = new Vector2(x, y);
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

    public Rectangle getBounds() {
        return bounds;
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
