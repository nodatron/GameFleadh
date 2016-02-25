package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;

/**
 * Created by Keith on 14/02/2016.
 */
public class AOERing extends Projectile{
    private float counter;
    private float maxSize;

    public AOERing(float x, float y, double angle, int level)
    {
        super(x, y, angle, level);
        if(level < 3) {
            projectileSprite = new Sprite(new Texture("projectiles/aoe_projectile_level1.png"));
        }
        else {
            projectileSprite = new Sprite(new Texture("projectiles/aoe_projectile_level3.png"));
        }
        projectileSprite.setSize(1, 1);
        projectileSprite.setCenter(x, y);
        counter = 0.0f;
        projectileSprite.setScale(counter);
        maxSize = 1.5f;
    }

    @Override
    public void update()
    {
        projectileSprite.setOriginCenter();
        counter += 0.03f;
        projectileSprite.setScale(counter);
        if(counter > maxSize)
        {
            PlayScreen.projectiles.remove(this);
        }
    }
}
