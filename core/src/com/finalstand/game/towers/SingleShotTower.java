package com.finalstand.game.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Keith on 09/02/2016.
 */
public class SingleShotTower extends Tower{

    public SingleShotTower(float x, float y)
    {
        super(x, y);
//        bounds = new Rectangle(x, y, level1.getWidth(), level1.getHeight());
        level = 1;
        level1 = new Texture("singleshot_level1.png");
        level2 = new Texture("singleshot_level2.png");
        level3 = new Texture("singleshot_level3.png");
        currentTexture = level1;
    }

    public void update()
    {
        if(level == 2 && currentTexture != level2)
        {
            currentTexture = level2;
        }

        if(Gdx.input.justTouched())
        {
            level = 2;
        }
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
}
