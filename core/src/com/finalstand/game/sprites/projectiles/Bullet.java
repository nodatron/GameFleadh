package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.finalstand.game.FinalStand;

/**
 * Created by Keith on 14/02/2016.
 */
public class Bullet extends Projectile{

    public Bullet(float x, float y, float angle, int level, float w, float h)
    {
        super(x, y, angle, level, 6 / FinalStand.PPM);
        projectileSprite = new Sprite(new Texture("projectiles/singleshot_projectile.png"));
        projectileSprite.setSize(w, h);
        projectileSprite.setPosition(x, y);
    }
}