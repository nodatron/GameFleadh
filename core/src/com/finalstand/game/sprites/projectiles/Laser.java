package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.finalstand.game.FinalStand;

/**
 * Created by Keith on 14/02/2016.
 */
public class Laser extends Projectile{

    public Laser(float x, float y, float angle, int level, float w, float h)
    {
        super(x, y, angle, level, 8 / FinalStand.PPM);
        if(level < 3) {
            projectileSprite = new Sprite(new Texture("projectiles/laser_projectile_level1.png"));
        }
        else {
            projectileSprite = new Sprite(new Texture("projectiles/laser_projectile_level3.png"));
        }
        projectileSprite.setSize(w, h);
        projectileSprite.setPosition(x, y);
    }
}