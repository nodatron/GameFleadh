package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.finalstand.game.FinalStand;

/**
 * Created by Keith on 14/02/2016.
 */
public class Bullet extends Projectile{

    public Bullet(float x, float y, double angle, int level)
    {
        super(x, y, angle, level);
        projectileSprite = new Sprite(new Texture("projectiles/singleshot_projectile.png"));
        projectileSprite.setSize(projectileSprite.getTexture().getWidth() / (FinalStand.PPM * 6),
                                 projectileSprite.getTexture().getHeight() / (FinalStand.PPM * 6));
        projectileSprite.setPosition(x, y);
    }
}
