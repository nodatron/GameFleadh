package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.finalstand.game.FinalStand;

/**
 * Created by Keith on 14/02/2016.
 */
public class Laser extends Projectile{

    public Laser(float x, float y, double angle, int level, float w, float h)
    {
        super(x, y, angle, level);
        projectileSprite = new Sprite(new Texture("projectiles/laser_projectile_level1.png"));
        projectileSprite.setSize(w / 4.0f, h);
        projectileSprite.setPosition(x, y);
    }
}
