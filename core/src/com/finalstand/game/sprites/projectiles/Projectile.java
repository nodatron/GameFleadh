package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
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
    protected float angle;

    protected Sprite projectileSprite;

    public Projectile(float x, float y, float angle, int level, float speed)
    {
        position = new Vector2(x, y);
        this.level = level;
        this.speed = speed;
        this.angle = angle;
        forward = new Vector2(MathUtils.sinDeg(angle) * speed, -MathUtils.cosDeg(angle) * speed);
//        System.out.println(forward);
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

    public float getAngle() {
        return angle;
    }
}