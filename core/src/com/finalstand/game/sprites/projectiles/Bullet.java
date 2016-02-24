package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Keith on 14/02/2016.
 */
public class Bullet extends Projectile {

    public Bullet(float x, float y, float angle, int level)
    {
        super(x, y, angle, level);
        sprite = new Sprite();
    }

    public void update()
    {

    }
}
