package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;

/**
 * Created by Keith on 14/02/2016.
 */
public class Projectile extends Sprite{
    protected Vector2 position;
    protected Vector2 forward;
    protected float speed;
    protected int level;

    protected Rectangle bounds;
    protected Sprite projectileSprite;

    public Projectile(float x, float y, float angle, int level)
    {
        position = new Vector2(x, y);
        this.level = level;
        speed = 1 / FinalStand.PPM;
        forward = new Vector2((float)Math.sin(angle) * speed, -(float)Math.cos(angle) * speed);
    }

    public void update()
    {
        position.add(forward);
        projectileSprite.setPosition(position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Sprite getSprite() {
        return projectileSprite;
    }
}
